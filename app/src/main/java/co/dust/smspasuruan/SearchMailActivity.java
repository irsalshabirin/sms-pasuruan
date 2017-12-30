package co.dust.smspasuruan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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
 * Created by irsal on 7/16/17.
 */

public class SearchMailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = SearchMailActivity.class.getSimpleName();
    int currentPage = 1;
    boolean isLoading = false;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.rl_no_content)
    RelativeLayout rlNoContent;

    private IncomingMailAdapter incomingMailAdapter;

    private CredentialStorage credentialStorage;

    private String query = null;
    private String from = "";
    private String now = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mail);
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

                if (currentPage <= 5) {
                    if (query != null) {
                        loadMail(currentPage + 1, CommonConstants.DEFAULT_PER_PAGE, query);
                    }
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

                Intent intent = new Intent(SearchMailActivity.this, DetailIncomingMailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(CommonConstants.TAG_SURAT_MASUK_ID, incomingMailAdapter.getItem(position).getSuratMasukID());
                bundle.putSerializable(CommonConstants.TAG_SURAT_MASUK, incomingMailAdapter.getItem(position));

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

        rlNoContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlNoContent.setVisibility(View.GONE);
                onRefresh();
            }
        });

        onRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem menuItemSearch = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItemSearch);

        SearchView.SearchAutoComplete theTextArea = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        theTextArea.setTextColor(Color.WHITE);//or any color that you want
        theTextArea.setHintTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchMailActivity.this.query = "%" + query + "%";
                loadMail(1, CommonConstants.DEFAULT_PER_PAGE, SearchMailActivity.this.query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                SearchMailActivity.this.query = "%" + newText + "%";
//                loadMail(1, CommonConstants.DEFAULT_PER_PAGE, SearchMailActivity.this.query);
                return false;
            }
        });

        searchView.setSubmitButtonEnabled(true);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                query = "";
                finish();
                return false;
            }
        });
        // expand searchnya
        searchView.setIconified(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_search:
                Intent intent = new Intent(this, SearchMailActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMail(final int page, int perPage, String query) {
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

                        query
                )
                .enqueue(new Callback<APIService.IncomingMailsResponse>() {
                    @Override
                    public void onResponse(Call<APIService.IncomingMailsResponse> call, Response<APIService.IncomingMailsResponse> response) {
                        isLoading = false;

                        if (response.isSuccessful()) {
                            if (response.body().incomingMails != null) {

                                if (incomingMailAdapter == null) {
                                    incomingMailAdapter = new IncomingMailAdapter(SearchMailActivity.this);
                                    recyclerView.setAdapter(incomingMailAdapter);
                                }

                                incomingMailAdapter.onNext(response.body().incomingMails, page);

                                if (incomingMailAdapter.getItemCount() == 0) {
                                    rlNoContent.setVisibility(View.VISIBLE);
                                    swipeRefreshLayout.setVisibility(View.GONE);
                                } else {
                                    rlNoContent.setVisibility(View.GONE);
                                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                                }

                            } else {
                                if (BuildConfig.DEBUG) {
                                    Log.e(TAG, "response null, incomingMails");
                                }
                                rlNoContent.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setVisibility(View.GONE);

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
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "onRefresh()");
        }

        if (query != null) {
            currentPage = 1;
            loadMail(1, CommonConstants.DEFAULT_PER_PAGE, query);
        }
    }
}
