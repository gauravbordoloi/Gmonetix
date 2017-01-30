package com.gmonetix.gmonetix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmonetix.gmonetix.constant.Const;
import com.gmonetix.gmonetix.helper.CommonAdapter;
import com.gmonetix.gmonetix.helper.DateConverter;
import com.gmonetix.gmonetix.helper.Font;
import com.gmonetix.gmonetix.helper.SingletonVolley;
import com.gmonetix.gmonetix.model.JsonDataModel;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class PostsOfParticularCategory extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static List<JsonDataModel> JsonCategoriesDatas;
    private String categoriesName;
    private int categoriesId;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ListView listView;
    public static final String CATEGORIES_ID = "categories_id";
    public static final String CATEGORIES_NAME = "categories_name";
    public static final String POST_ID = "id";
    public static final String POST_TITLE = "post_title";
    public static final String POST_URL = "url";
    public static final String POST_DATE = "date";
    private DateConverter dateConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_of_particular_category);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = (ListView) findViewById(R.id.post_categories_list_view);
        TextView toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_post_of_categories);
        dateConverter = new DateConverter();

        Font font = new Font();
        font.setFont(getApplicationContext(), toolBarTextView);

        categoriesId = getIntent().getExtras().getInt(CATEGORIES_ID);
        categoriesName = getIntent().getExtras().getString(CATEGORIES_NAME);

        toolBarTextView.setText(categoriesName);
        JsonCategoriesDatas = new ArrayList<>();
        listView.setOnItemClickListener(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.get_all_posts_of_category_url.replace("CATEGORY_ID", String.valueOf(categoriesId)),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            Gson gson = new Gson();
                            JSONArray ParentArray = new JSONArray(s);
                            for (int i = 0; i < ParentArray.length(); i++) {
                                JSONObject ParentObject = ParentArray.getJSONObject(i);
                                JsonDataModel jsonDataModel = gson.fromJson(ParentObject.toString(), JsonDataModel.class);
                                jsonDataModel.setId(ParentObject.getInt("id"));
                                jsonDataModel.setDate(ParentObject.getString("date").substring(0, 10));
                                jsonDataModel.setLink(ParentObject.getString("link"));
                                jsonDataModel.setTitleRendered(ParentObject.getJSONObject("title").getString("rendered"));
                                jsonDataModel.setFeaturedMediaUrl(ParentObject.getJSONObject("better_featured_image").getString("source_url"));
                                JsonCategoriesDatas.add(jsonDataModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            CommonAdapter commonAdapter = new CommonAdapter(getApplicationContext(), JsonCategoriesDatas);
                            listView.setAdapter(commonAdapter);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), Post.class);
        intent.putExtra(POST_ID, JsonCategoriesDatas.get(position).getId());
        intent.putExtra(POST_TITLE, JsonCategoriesDatas.get(position).getTitleRendered());
        intent.putExtra(POST_URL, JsonCategoriesDatas.get(position).getLink());
        intent.putExtra(POST_DATE,dateConverter.getDate(JsonCategoriesDatas.get(position).getDate())+" "+ dateConverter.getMonth(JsonCategoriesDatas.get(position).getDate()));
        startActivity(intent);
    }
}