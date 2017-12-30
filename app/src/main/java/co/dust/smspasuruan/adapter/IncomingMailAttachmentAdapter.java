package co.dust.smspasuruan.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dust.smspasuruan.R;
import co.dust.smspasuruan.model.IncomingMailAttachment;

/**
 * Created by irsal on 6/10/17.
 */

public class IncomingMailAttachmentAdapter extends RecyclerView.Adapter<IncomingMailAttachmentAdapter.ItemViewHolder> {

    private List<IncomingMailAttachment> incomingMailAttachmentList;
    private Activity activity;

    public IncomingMailAttachmentAdapter(Activity activity, List<IncomingMailAttachment> incomingMailAttachmentList) {
        this.activity = activity;
        this.incomingMailAttachmentList = incomingMailAttachmentList;
    }

    @Override
    public IncomingMailAttachmentAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_incoming_mail_attachment, parent, false);
        return new IncomingMailAttachmentAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IncomingMailAttachmentAdapter.ItemViewHolder holder, final int position) {

        holder.tvName.setText(incomingMailAttachmentList.get(position).getCatatan());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingMailAttachmentList.get(position).getUrl() != null) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(incomingMailAttachmentList.get(position).getUrl()));
                    activity.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomingMailAttachmentList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_attachment)
        CardView cardView;

        @BindView(R.id.tv_attachment_name)
        TextView tvName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
