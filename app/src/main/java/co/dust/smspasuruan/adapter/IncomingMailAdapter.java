package co.dust.smspasuruan.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.github.marlonlom.utilities.timeago.TimeAgo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dust.smspasuruan.R;
import co.dust.smspasuruan.model.IncomingMail;
import co.dust.smspasuruan.tools.StaticHelper;

/**
 * Created by irsal on 6/8/17.
 */

public class IncomingMailAdapter extends RecyclerView.Adapter<IncomingMailAdapter.ItemViewHolder> {

    private static final String TAG = IncomingMailAdapter.class.getSimpleName();

    private Activity activity;

    private LinkedHashMap<Integer, List<IncomingMail>> map;
    private List<IncomingMail> incomingMails;

    public IncomingMailAdapter(Activity activity) {
//        if (BuildConfig.DEBUG) {
//            Log.e(TAG, "constructor");
//        }

        this.activity = activity;
        map = new LinkedHashMap<>();
        incomingMails = new ArrayList<>();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_incoming_mail, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvTitle.setText(getItem(position).getNomorSurat());
        holder.tvContent.setText(getItem(position).getPerihal());

        if (getItem(position).getTerbaca() != null) {
            if (Integer.parseInt(getItem(position).getTerbaca()) == 0) {
                holder.tvTitle.setTypeface(null, Typeface.BOLD);
                holder.tvContent.setTypeface(null, Typeface.BOLD);
            } else {
                holder.tvTitle.setTypeface(null, Typeface.NORMAL);
                holder.tvContent.setTypeface(null, Typeface.NORMAL);
            }
        } else {
            holder.tvTitle.setTypeface(null, Typeface.NORMAL);
            holder.tvContent.setTypeface(null, Typeface.NORMAL);
        }

        Random rnd = new Random();
        if (getItem(position).getColor() == -1) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            getItem(position).setColor(color);
        }

        TextDrawable drawable;
        if (getItem(position).getPerihal() != null) {

            drawable = TextDrawable.builder()
                    .buildRound(getItem(position).getPerihal().substring(0, 1).toUpperCase(), getItem(position).getColor());
        } else {
            drawable = TextDrawable.builder()
                    .buildRound(getItem(position).getNomorSurat().substring(0, 1).toUpperCase(), getItem(position).getColor());
        }

        Date now = new Date();
        //Date tglSurat = StaticHelper.formatDate(getItem(position).getTglSurat());
        Date tglSurat = StaticHelper.formatDate(getItem(position).getTglDiteruskan());
        if (tglSurat != null) {
            if (tglSurat.before(now)) {
                String timestamp = TimeAgo.using(tglSurat.getTime());
                holder.tvTimestamp.setText(timestamp);
            } else {
                holder.tvTimestamp.setText(getItem(position).getTglSurat());
            }
        } else {
            holder.tvTimestamp.setText(getItem(position).getTglSurat());
        }

        holder.imageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        int count = 0;

        for (List<IncomingMail> list : map.values()) {
            count = count + list.size();
        }

        return count;
    }

    public IncomingMail getItem(int position) {

        int listSize = 0;

        if (incomingMails.size() > (position)) {
            return incomingMails.get(position);
        }

        incomingMails = new ArrayList<>();

        for (List<IncomingMail> list : map.values()) {
            incomingMails.addAll(list);
            listSize = listSize + list.size();
            if (listSize > (position)) {
                break;
            }
        }

        if (incomingMails.size() > 0) {
            return incomingMails.get(position);
        }

        return null;
    }

    public void onNext(List<IncomingMail> incomingMails1, int page) {

        if (incomingMails1 == null) {
            return;
        }

        if (page == 1) {
            map = new LinkedHashMap<>();
            incomingMails = new ArrayList<>();
        }

        map.put(page, incomingMails1);
        notifyDataSetChanged();

//        if (BuildConfig.DEBUG) {
//            Log.e(TAG, "tadlkfjds");
//        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_content)
        TextView tvContent;

        @BindView(R.id.iv_main_icon)
        ImageView imageView;


        @BindView(R.id.tv_timestamp)
        TextView tvTimestamp;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
