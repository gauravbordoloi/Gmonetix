package com.gmonetix.gmonetix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.gmonetix.gmonetix.constant.Const;
import com.gmonetix.gmonetix.helper.Font;
import com.gmonetix.gmonetix.helper.SingletonVolley;
import com.gmonetix.gmonetix.helper.WebViewLoader;

import org.json.JSONException;
import org.json.JSONObject;

public class Post extends AppCompatActivity implements View.OnClickListener {
    WebView content;
    ProgressDialog progressDialog;
    public static final String POST_ID = "id";
    public static final String POST_TITLE = "post_title";
    public static final String POST_URL = "url";
    public static final String POST_DATE = "date";
    private FloatingActionMenu fabMore;
    private com.github.clans.fab.FloatingActionButton fab_share ,fab_open_in_browser , fab_home;
    private String postTitle;
    private Toolbar toolbar;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        int id = getIntent().getExtras().getInt(POST_ID);
        postTitle = getIntent().getExtras().getString(POST_TITLE);

        date = (TextView) findViewById(R.id.tv_date);
        content = (WebView)findViewById(R.id.content);
        final WebViewLoader webViewLoader = new WebViewLoader(content);
        webViewLoader.setWebSettings();
        fabMore = (FloatingActionMenu) findViewById(R.id.fab_post_more);
        fab_share = (FloatingActionButton) findViewById(R.id.fab_share);
        fab_open_in_browser = (FloatingActionButton) findViewById(R.id.fab_open_in_browser);
        fab_home = (FloatingActionButton) findViewById(R.id.fab_home);
        TextView toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        Font font = new Font();
        font.setFont(getApplicationContext(),toolBarTextView);

        progressDialog = new ProgressDialog(Post.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        fabMore.setClosedOnTouchOutside(true);
        fab_share.setOnClickListener(this);
        fab_open_in_browser.setOnClickListener(this);
        fab_home.setOnClickListener(this);

        String url = Const.get_content_by_id.replace("POST_ID",String.valueOf(id));

        toolBarTextView.setText(postTitle);
        date.setText(getIntent().getExtras().getString(POST_DATE));
        font.setFont(getApplicationContext(),date);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject ParentObject = new JSONObject(s);
                    webViewLoader.setLoadDataWithBaseUrl(ParentObject.getJSONObject("content").getString("rendered"));
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(Post.this,"Unable to Load", Toast.LENGTH_LONG).show();
            }
        });
        SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.fab_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString(POST_URL));
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Choose any"));
                break;

            case R.id.fab_open_in_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getExtras().getString(POST_URL))));
                break;

            case R.id.fab_home:
                startActivity(new Intent(Post.this, Home.class));
                finish();
                break;
        }
    }
}