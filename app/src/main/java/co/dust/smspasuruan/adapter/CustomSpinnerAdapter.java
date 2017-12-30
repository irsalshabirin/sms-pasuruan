package co.dust.smspasuruan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.dust.smspasuruan.R;

/**
 * Created by irsal on 6/10/17.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    // View lookup cache
    private static class ViewHolder {
        TextView textView;
    }

    public CustomSpinnerAdapter(Context context, List<String> strings) {
        super(context, R.layout.item_text_view, strings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_text_view, parent, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.textView.setText(item);

        // Return the completed view to render on screen
        return convertView;
    }
}
