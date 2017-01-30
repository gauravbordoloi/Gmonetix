package com.gmonetix.gmonetix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmonetix.gmonetix.constant.Const;
import com.gmonetix.gmonetix.helper.AdapterCategories;
import com.gmonetix.gmonetix.helper.SingletonVolley;
import com.gmonetix.gmonetix.model.JsonCategoriesModel;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Categories extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    public List<JsonCategoriesModel> JsonCategoriesData ;
    ProgressBar progressBar;
    public static final String CATEGORIES_ID = "categories_id";
    public static final String CATEGORIES_NAME = "categories_name";

    public Categories() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_categories, container, false);

        listView = (ListView) view.findViewById(R.id.categories_list_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_categories);
        JsonCategoriesData = new ArrayList<>();
        listView.setOnItemClickListener(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.get_categories_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Gson gson = new Gson();
                            JSONArray ParentArray = new JSONArray(s);
                            for (int i= 0;i<ParentArray.length();i++){
                                JSONObject ParentObject = ParentArray.getJSONObject(i);
                                JsonCategoriesModel jsonCategoriesModel = gson.fromJson(ParentObject.toString(), JsonCategoriesModel.class);
                                jsonCategoriesModel.setCategoriesId(ParentObject.getInt("id"));
                                jsonCategoriesModel.setCategoriesDescription(ParentObject.getString("description"));
                                jsonCategoriesModel.setCategoriesName(ParentObject.getString("name"));
                                JsonCategoriesData.add(jsonCategoriesModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            AdapterCategories adapterCategories = new AdapterCategories(getActivity(),JsonCategoriesData);
                            listView.setAdapter(adapterCategories);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        SingletonVolley.getInstance(getActivity()).addToRequestQueue(stringRequest);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),PostsOfParticularCategory.class);
        intent.putExtra(CATEGORIES_ID,JsonCategoriesData.get(position).getCategoriesId());
        intent.putExtra(CATEGORIES_NAME,JsonCategoriesData.get(position).getCategoriesName());
        startActivity(intent);
    }
}
