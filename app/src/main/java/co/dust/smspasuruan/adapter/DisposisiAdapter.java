package co.dust.smspasuruan.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dust.smspasuruan.BuildConfig;
import co.dust.smspasuruan.R;
import co.dust.smspasuruan.model.Disposisi;

/**
 * Created by irsal on 7/18/17.
 */

public class DisposisiAdapter extends RecyclerView.Adapter<DisposisiAdapter.ItemViewHolder> {

    private static final String TAG = DisposisiAdapter.class.getSimpleName();

    private List<Disposisi> disposisiList;
    private Activity activity;

    public DisposisiAdapter(Activity activity, List<Disposisi> disposisiList) {
        this.activity = activity;
        this.disposisiList = disposisiList;
    }

    @Override
    public DisposisiAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_history_disposisi, parent, false);
        return new DisposisiAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DisposisiAdapter.ItemViewHolder holder, final int position) {

        Disposisi disposisi = disposisiList.get(position);
        if (disposisi.getTglDisposisi() != null) {
            holder.tvTglDisposisi.setText(disposisi.getTglDisposisi());
        } else {
            holder.tvTglDisposisi.setText("");
        }

        if (disposisi.getSatkerAsal() != null) {
            holder.tvAsalDisposisi.setText(disposisi.getSatkerAsal());
        } else {
            holder.tvAsalDisposisi.setText("");
        }

        if (disposisi.getSatkerTujuan() != null) {
            holder.tvTujuanDisposisi.setText(disposisi.getSatkerTujuan());
        } else {
            holder.tvTujuanDisposisi.setText("");
        }

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "disposisi terbaca : " + disposisi.isTerbaca());
        }
        if (disposisi.isTerbaca()) {
            holder.itvBaca.setVisibility(View.VISIBLE);
        } else {
            holder.itvBaca.setVisibility(View.INVISIBLE);
        }

        if (disposisi.isTerbalas()) {
            holder.itvBalas.setVisibility(View.VISIBLE);
        } else {
            holder.itvBalas.setVisibility(View.INVISIBLE);
        }

        if (disposisi.isTerdisposisi()) {
            holder.itvDisposisi.setVisibility(View.VISIBLE);
        } else {
            holder.itvDisposisi.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return disposisiList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        // tanggal disposisi
        // asal disposisi
        // tujuan disposisi
        // terbalas
        // terbaca
        // terdisposisi

//        @BindView(R.id.cv_attachment)
//        CardView cardView;

        @BindView(R.id.tv_tanggal_disposisi)
        TextView tvTglDisposisi;

        @BindView(R.id.tv_asal_disposisi)
        TextView tvAsalDisposisi;

        @BindView(R.id.tv_tujuan_disposisi)
        TextView tvTujuanDisposisi;

        @BindView(R.id.itv_baca)
        TextView itvBaca;

        @BindView(R.id.itv_balas)
        TextView itvBalas;

        @BindView(R.id.itv_disposisi)
        TextView itvDisposisi;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

