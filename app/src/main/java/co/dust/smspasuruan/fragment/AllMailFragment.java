package co.dust.smspasuruan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import co.dust.smspasuruan.BuildConfig;
import co.dust.smspasuruan.DetailIncomingMailActivity;
import co.dust.smspasuruan.R;
import co.dust.smspasuruan.adapter.IncomingMailAdapter;
import co.dust.smspasuruan.tools.APIService;
import co.dust.smspasuruan.tools.CommonConstants;
import co.dust.smspasuruan.tools.CredentialStorage;
import co.dust.smspasuruan.tools.ItemClickSupport;
import co.dust.smspasuruan.tools.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import org.joda.time.LocalDateTime;

/**
 * Created by irsal on 6/9/17.
 */

public class AllMailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = AllMailFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.rl_no_content)
    RelativeLayout rlNoContent;

    private IncomingMailAdapter incomingMailAdapter;

    int currentPage = 1;
    boolean isLoading = false;

    private CredentialStorage credentialStorage;
    private String from = "";
    private String now = "";

    public static Fragment newInstance() {
        return new AllMailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv_srl, container, false);
        ButterKnife.bind(this, view);

        credentialStorage = new CredentialStorage(getActivity());

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        LocalDateTime.from(referenceDate.toInstant()).minusMonths(3);
//        LocalDateTime localDateTime = LocalDateTime.fromDateFields(referenceDate).minusMonths(3);

        DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
        from = dfDate.format(new DateTime().minusMonths(3).toDate());
        now = dfDate.format(new DateTime().toDate());
//        if (BuildConfig.DEBUG) {
//            Log.e(TAG, "from :" + from);
//            Log.e(TAG, "to :" + now);
//        }


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        swipeRefreshLayout.setOnRefreshListener(this);

        incomingMailAdapter = new IncomingMailAdapter(getActivity());
        recyclerView.setAdapter(incomingMailAdapter);

        BaseAttacher mBaseAttacher = Mugen.with(recyclerView, new MugenCallbacks() {

            @Override
            public void onLoadMore() {

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

                Intent intent = new Intent(getActivity(), DetailIncomingMailActivity.class);

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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
                            if (response.body().incomingMails != null) {

                                if (incomingMailAdapter == null) {
                                    incomingMailAdapter = new IncomingMailAdapter(getActivity());
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

        currentPage = 1;
        loadMail(1, CommonConstants.DEFAULT_PER_PAGE);
    }
}
