package co.dust.smspasuruan;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dust.smspasuruan.adapter.DisposisiAdapter;
import co.dust.smspasuruan.adapter.IncomingMailAttachmentAdapter;
import co.dust.smspasuruan.model.Disposisi;
import co.dust.smspasuruan.model.IncomingMail;
import co.dust.smspasuruan.tools.APIService;
import co.dust.smspasuruan.tools.CommonConstants;
import co.dust.smspasuruan.tools.CredentialStorage;
import co.dust.smspasuruan.tools.ItemClickSupport;
import co.dust.smspasuruan.tools.ServiceFactory;
import co.dust.smspasuruan.tools.StaticHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by irsal on 6/8/17.
 */

public class DetailIncomingMailActivity extends AppCompatActivity {

    private static final String TAG = DetailIncomingMailActivity.class.getSimpleName();

    @BindView(R.id.tv_no_surat)
    TextView tvNoSurat;

    @BindView(R.id.tv_tanggal_surat)
    TextView tvTglSurat;

    @BindView(R.id.tv_tanggal_diteruskan)
    TextView tvTglDiteruskan;

    @BindView(R.id.tv_tanggal_remiten)
    TextView tvTglRemiten;

    @BindView(R.id.tv_unit_kerja_asal)
    TextView tvUnitKerjaAsal;

    @BindView(R.id.tv_asal_surat)
    TextView tvAsalSurat;

    @BindView(R.id.tv_alamat_surat)
    TextView tvAlamatSurat;

    @BindView(R.id.tv_kepada)
    TextView tvKepada;

    @BindView(R.id.tv_perihal)
    TextView tvPerihal;

    @BindView(R.id.tv_klasifikasi)
    TextView tvKlasifikasi;

    @BindView(R.id.tv_sifat_surat)
    TextView tvSifatSurat;

    @BindView(R.id.tv_tujuan_surat)
    TextView tvTujuanSurat;

    @BindView(R.id.tv_satker_tujuan)
    TextView tvSatkerTujuan;

    @BindView(R.id.ll_ringkasan)
    LinearLayout llRingkasan;

    @BindView(R.id.tv_ringkasan)
    TextView tvRingkasan;

    @BindView(R.id.ll_attachment)
    LinearLayout llAttachment;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.rl_history_disposisi)
    RecyclerView rlHistoryDisposisi;

    @BindView(R.id.rl_attachment)
    RecyclerView rlAttachment;

    DisposisiAdapter disposisiAdapter;

    IncomingMailAttachmentAdapter incomingMailAttachmentAdapter;

    APIService apiService = ServiceFactory.createRetrofitService(APIService.class, true);

    private String suratMasukID = "";
    private IncomingMail suratMasuk = null;

    private CredentialStorage credentialStorage;
    private MaterialDialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_incoming_mail);
        ButterKnife.bind(this);

        credentialStorage = new CredentialStorage(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loading = new MaterialDialog.Builder(DetailIncomingMailActivity.this)
//                                        .title(R.string.progress_dialog)
                .content(R.string.loading)
                .progress(true, 0).build();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            suratMasukID = bundle.getString(CommonConstants.TAG_SURAT_MASUK_ID);

            try {
                suratMasuk = (IncomingMail) bundle.getSerializable(CommonConstants.TAG_SURAT_MASUK);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
//            Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_LONG).show();
//            return;
        }

        updateRead();

        fetchMail();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rlAttachment.setLayoutManager(layoutManager);
        rlAttachment.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rlHistoryDisposisi.setLayoutManager(manager);
        rlHistoryDisposisi.setHasFixedSize(true);
        rlHistoryDisposisi.setNestedScrollingEnabled(false);

        getAttachment();
    }

    private void getAttachment() {
//        suratMasukID = "30641";

        progressBar.setVisibility(View.VISIBLE);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 500); // see this max value coming back here, we animale towards that value
        animation.setDuration(5000); //in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        apiService.getAttachment(suratMasukID).enqueue(new Callback<APIService.IncomingMailAttachmentsResponse>() {
            @Override
            public void onResponse(Call<APIService.IncomingMailAttachmentsResponse> call, Response<APIService.IncomingMailAttachmentsResponse> response) {
                progressBar.setVisibility(View.GONE);
                progressBar.clearAnimation();

                if (response.isSuccessful()) {
                    if (!response.body().error) {

                        incomingMailAttachmentAdapter = new IncomingMailAttachmentAdapter(DetailIncomingMailActivity.this, response.body().incomingMailAttachments);
                        rlAttachment.setAdapter(incomingMailAttachmentAdapter);
                        rlAttachment.setVisibility(View.VISIBLE);
                        llAttachment.setVisibility(View.VISIBLE);

                        if (response.body().incomingMailAttachments == null || response.body().incomingMailAttachments.isEmpty()) {
                            llAttachment.setVisibility(View.GONE);
                        }

                    } else {
                        llAttachment.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<APIService.IncomingMailAttachmentsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                progressBar.clearAnimation();

                llAttachment.setVisibility(View.GONE);
            }
        });
    }

    private void fetchMail() {
        if (suratMasuk != null) {
            tvNoSurat.setText(suratMasuk.getNomorSurat());

            tvTglSurat.setText(StaticHelper.prettyDate(suratMasuk.getTglSurat()));

            if (suratMasuk.getTglDiteruskan() != null) {
                tvTglDiteruskan.setText(StaticHelper.prettyDate(suratMasuk.getTglDiteruskan()));
            } else {
                tvTglDiteruskan.setText("");
            }

            if (suratMasuk.getTglBatas() != null) {
                tvTglRemiten.setText(StaticHelper.prettyDate(suratMasuk.getTglBatas()));
            } else {
                tvTglRemiten.setText("");
            }

            // asal surat
            tvUnitKerjaAsal.setText(credentialStorage.getSatkerName());

            if (suratMasuk.getInstansiAsal() != null) {
                tvAsalSurat.setText(suratMasuk.getInstansiAsal());
            }

            if (suratMasuk.getAlamatAsal() != null) {
                String alamat = suratMasuk.getAlamatAsal();
                String kota = "";
                if (suratMasuk.getKotaAsal() != null) {
                    kota = " , " + suratMasuk.getKotaAsal();
                }
                tvAlamatSurat.setText(alamat + kota);
            }

            if (suratMasuk.getKepada() != null) {
                tvKepada.setText(suratMasuk.getKepada());
            } else {
                tvKepada.setText("");
            }

            if (suratMasuk.getPerihal() != null) {
                tvPerihal.setText(suratMasuk.getPerihal());
            } else {
                tvPerihal.setText("");
            }


            if (suratMasuk.getKlasifikasi() != null) {
                tvKlasifikasi.setText(suratMasuk.getKlasifikasi());
            } else {
                tvKlasifikasi.setText("");
            }


            if (suratMasuk.getJenis() != null) {
                tvSifatSurat.setText(suratMasuk.getJenis());
            } else {
                tvSifatSurat.setText("");
            }

            if (suratMasuk.getJenisTujuan() != null) {
                tvTujuanSurat.setText(suratMasuk.getJenisTujuan());
            } else {
                tvTujuanSurat.setText("");
            }


            if (suratMasuk.getSatkerTujuan() != null) {
                tvSatkerTujuan.setText(suratMasuk.getSatkerTujuan());
            } else {
                tvSatkerTujuan.setText("");
            }

            if (suratMasuk.getIsiRingkas() != null) {
                tvRingkasan.setText(suratMasuk.getIsiRingkas());
            } else {
                llRingkasan.setVisibility(View.GONE);
            }
        } else {
            // get from another url
            StaticHelper.showProgressDialog(this, null, getString(R.string.loading), false);

            if (BuildConfig.DEBUG) {
                Log.e(TAG, "surat Masuk Id : " + suratMasukID);
            }
            apiService.getDetailMail(
                    suratMasukID,
                    credentialStorage.getSatkerId(),
                    credentialStorage.getYear()
            ).enqueue(new Callback<APIService.IncomingMailResponse>() {
                @Override
                public void onResponse(Call<APIService.IncomingMailResponse> call, Response<APIService.IncomingMailResponse> response) {
                    StaticHelper.hideProgressDialog();
                    if (response.isSuccessful()) {
                        if (!response.body().error) {
                            suratMasuk = response.body().incomingMail;
                            fetchMail();
                        }
                    }
                }

                @Override
                public void onFailure(Call<APIService.IncomingMailResponse> call, Throwable t) {
                    StaticHelper.hideProgressDialog();
                    t.printStackTrace();
                    Toast.makeText(DetailIncomingMailActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
//                    finish();
                }
            });
        }

        loadHistoryDisposisi();
    }

    private void loadHistoryDisposisi() {
//        APIService apiService = ServiceFactory.createRetrofitService(APIService.class, false);
        apiService.getDisposisi(suratMasukID).enqueue(new Callback<List<Disposisi>>() {
            @Override
            public void onResponse(Call<List<Disposisi>> call, final Response<List<Disposisi>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        disposisiAdapter = new DisposisiAdapter(DetailIncomingMailActivity.this, response.body());
                        rlHistoryDisposisi.setAdapter(disposisiAdapter);

                        ItemClickSupport.addTo(rlHistoryDisposisi).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                String noUrut = response.body().get(position).getNourut();

                                if (loading != null) {
                                    loading.show();
                                }
                                loadContentDisposisi(noUrut);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Disposisi>> call, Throwable t) {

            }
        });
    }

    private void loadContentDisposisi(String noUrut) {

        apiService.getIsiDisposisi(suratMasukID, noUrut).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "onResponse");
                }


                if (response.isSuccessful()) {

                    if (loading != null) {
                        loading.dismiss();
                    }

                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "string : " + response.body());
                    }

                    String content = "";
                    try {
                        content = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    new MaterialDialog.Builder(DetailIncomingMailActivity.this)
                            .title("Isi Disposisi")
                            .content(content)
                            .autoDismiss(false)
                            .cancelable(false)
                            .canceledOnTouchOutside(false)
                            .positiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void updateRead() {
        apiService.updateRead(suratMasukID).enqueue(new Callback<APIService.Message>() {
            @Override
            public void onResponse(Call<APIService.Message> call, Response<APIService.Message> response) {

            }

            @Override
            public void onFailure(Call<APIService.Message> call, Throwable t) {

            }
        });
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
