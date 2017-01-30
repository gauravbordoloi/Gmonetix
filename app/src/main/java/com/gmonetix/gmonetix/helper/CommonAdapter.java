package com.gmonetix.gmonetix.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.gmonetix.gmonetix.R;
import com.gmonetix.gmonetix.model.JsonDataModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.util.List;


public class CommonAdapter extends BaseAdapter{

    private Context context;
    private List<JsonDataModel> jsonData;
    private Font font;

    public CommonAdapter(Context context, List<JsonDataModel> jsonData) {
        this.context = context;
        this.jsonData = jsonData;
    }

    @Override
    public int getCount() {
        return jsonData.size();
    }

    @Override
    public Object getItem(int position) {
        return jsonData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.row, null);

            holder.imageView = (ImageView)convertView.findViewById(R.id.imageView);
            holder.date = (TextView)convertView.findViewById(R.id.tv_date);
            holder.title = (TextView)convertView.findViewById(R.id.tv_title_rendered);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar_recent);

            font  = new Font();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DateConverter dateConverter = new DateConverter();


        holder.title.setText(jsonData.get(position).getTitleRendered());
        holder.date.setText(dateConverter.getDate(jsonData.get(position).getDate())+" "+ dateConverter.getMonth(jsonData.get(position).getDate()));

        font.setFont(context,holder.title);
        font.setFont(context,holder.date);

        final ViewHolder finalHolder = holder;
        ImageLoader.getInstance().displayImage(jsonData.get(position).getFeaturedMediaUrl(), holder.imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                finalHolder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                finalHolder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        return convertView;
    }
    private class ViewHolder{
        private ImageView imageView;
        private TextView date;
        private TextView title;
        private ProgressBar progressBar;
    }
}
