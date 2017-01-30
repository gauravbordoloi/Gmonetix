package com.gmonetix.gmonetix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gmonetix.gmonetix.helper.CommonAdapter;
import com.gmonetix.gmonetix.helper.DateConverter;
import com.gmonetix.gmonetix.model.JsonDataModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import java.util.ArrayList;
import java.util.List;

public class Recent extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    public List<JsonDataModel> jsonDataList = SplashScreen.JsonDatas;
    public List<JsonDataModel> dataItems ;
    public static final String POST_ID = "id";
    public static final String POST_TITLE = "post_title";
    public static final String POST_URL = "url";
    public static final String POST_DATE = "date";
    DateConverter dateConverter;
    CommonAdapter commonAdapter;
    public Recent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        listView = (ListView) view.findViewById(R.id.recent_list_view);
        dateConverter = new DateConverter();
        listView.setOnItemClickListener(this);
        commonAdapter = new CommonAdapter(getActivity(),jsonDataList);
        listView.setAdapter(commonAdapter);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Intent intent = new Intent(getActivity(),Post.class);
        intent.putExtra(POST_ID,jsonDataList.get(position).getId());
        intent.putExtra(POST_TITLE,jsonDataList.get(position).getTitleRendered());
        intent.putExtra(POST_URL,jsonDataList.get(position).getLink());
        intent.putExtra(POST_DATE,dateConverter.getDate(jsonDataList.get(position).getDate())+" "+ dateConverter.getMonth(jsonDataList.get(position).getDate()));
        startActivity(intent);
    }
}
