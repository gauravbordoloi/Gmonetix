package com.gmonetix.gmonetix.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gmonetix.gmonetix.R;
import com.gmonetix.gmonetix.model.JsonCategoriesModel;
import java.util.List;

public class AdapterCategories extends BaseAdapter {

    private Context context;
    private List<JsonCategoriesModel> jsonCategoriesDataList;
    private Font font;

    public AdapterCategories(Context context, List<JsonCategoriesModel> jsonCategoriesDataList) {
        this.context = context;
        this.jsonCategoriesDataList = jsonCategoriesDataList;
    }

    @Override
    public int getCount() {
        return jsonCategoriesDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return jsonCategoriesDataList.get(position);
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
            convertView = mInflater.inflate(R.layout.categories_row_sample_layout, null);

            holder.categoriesDescription = (TextView) convertView.findViewById(R.id.categories_description);
            holder.categoriesName = (TextView) convertView.findViewById(R.id.categories_name);

            font  = new Font();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.categoriesDescription.setText(jsonCategoriesDataList.get(position).getCategoriesDescription());
        holder.categoriesName.setText(jsonCategoriesDataList.get(position).getCategoriesName());

        font.setFont(context,holder.categoriesDescription);
        font.setFont(context,holder.categoriesName);
            return convertView;
        }
    private class ViewHolder{
        private TextView categoriesName;
        private TextView categoriesDescription;
    }
}