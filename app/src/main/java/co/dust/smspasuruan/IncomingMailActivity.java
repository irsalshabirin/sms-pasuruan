package co.dust.smspasuruan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dust.smspasuruan.adapter.IncomingMailAdapter;
import co.dust.smspasuruan.tools.APIService;
import co.dust.smspasuruan.tools.CommonConstants;
import co.dust.smspasuruan.tools.CredentialStorage;
import co.dust.smspasuruan.tools.ItemClickSupport;
import co.dust.smspasuruan.tools.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by irsal on 6/6/17.
 */

public class IncomingMailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = IncomingMailActivity.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private IncomingMailAdapter incomingMailAdapter;

    int currentPage = 1;
    boolean isLoading = false;

    private CredentialStorage credentialStorage;

    private String from = "";
    private String now = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_mail);
        ButterKnife.bind(this);

        credentialStorage = new CredentialStorage(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // date
        DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
        from = dfDate.format(new DateTime().minusMonths(3).toDate());
        now = dfDate.format(new DateTime().toDate());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        swipeRefreshLayout.setOnRefreshListener(this);

        incomingMailAdapter = new IncomingMailAdapter(this);
        recyclerView.setAdapter(incomingMailAdapter);

        BaseAttacher mBaseAttacher = Mugen.with(recyclerView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {

//                if (BuildConfig.DEBUG) {
//                    Log.e(TAG, "onLoadMore");
//                }

                if (currentPage <= 5) {
                    loadMail(currentPage + 1, CommonConstants.DEFAULT_PER_PAGE);
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        }).start();

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

//                incomingMailAdapter.getItem(position).getSuratMasukID();
                Intent intent = new Intent(IncomingMailActivity.this, DetailIncomingMailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id", incomingMailAdapter.getItem(position).getSuratMasukID());
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

        onRefresh();

    }

    private void loadMail(final int page, int perPage) {
        APIService apiService = ServiceFactory.createRetrofitService(APIService.class, true);

//        if (BuildConfig.DEBUG) {
//            Log.e(TAG, "loadMail");
//        }

        progressBar.setVisibility(View.VISIBLE);

        apiService
                .loadMail(
                        page,
                        perPage,
                        CommonConstants.ALL_MAIL,

                        credentialStorage.getSatkerId(),
                        credentialStorage.getYear(),

                        from,
                        now,

                        null
                )
                .enqueue(new Callback<APIService.IncomingMailsResponse>() {
                    @Override
                    public void onResponse(Call<APIService.IncomingMailsResponse> call, Response<APIService.IncomingMailsResponse> response) {
                        isLoading = false;

                        if (response.isSuccessful()) {

//                            if (BuildConfig.DEBUG) {
//                                Log.e(TAG, "response is Successful");
//                            }

                            if (response.body().incomingMails != null) {

//                                if (BuildConfig.DEBUG) {
//                                    Log.e(TAG, "not null incomingMails");
//                                    Log.e(TAG, "length : " + response.body().incomingMails.size());
//                                    Log.e(TAG, "content : " + response.body().incomingMails.toString());
//                                }
                                if (incomingMailAdapter == null) {
                                    incomingMailAdapter = new IncomingMailAdapter(IncomingMailActivity.this);
                                    recyclerView.setAdapter(incomingMailAdapter);
                                }

                                incomingMailAdapter.onNext(response.body().incomingMails, page);

                            } else {

                                if (BuildConfig.DEBUG) {
                                    Log.e(TAG, "response null, incomingMails");
                                }

                            }
                        }

                        currentPage = page;
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<APIService.IncomingMailsResponse> call, Throwable t) {
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        loadMail(1, CommonConstants.DEFAULT_PER_PAGE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
