package com.gmonetix.gmonetix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gmonetix.gmonetix.helper.Font;
import com.gmonetix.gmonetix.helper.SingletonVolley;
import com.gmonetix.gmonetix.helper.WebViewLoader;
import org.json.JSONException;
import org.json.JSONObject;

public class SitePageLoader extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolBarTextView;
    private ProgressBar progressBar;
    WebView webView;
    WebViewLoader webViewLoader;
    private static String PAGE_TITLE = "page_title";
    private static String PAGE_URL = "page_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_page_loader);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        webView = (WebView) findViewById(R.id.webViewAboutUs);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_about_us);
        progressBar.setVisibility(View.VISIBLE);
        webViewLoader = new WebViewLoader(webView);

        Font font = new Font();
        toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        toolBarTextView.setText(getIntent().getExtras().getString(PAGE_TITLE));
        font.setFont(getApplicationContext(),toolBarTextView);

        webViewLoader.setWebSettings();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getIntent().getExtras().getString(PAGE_URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject ParentObject = new JSONObject(s);
                            webViewLoader.setLoadDataWithBaseUrl(ParentObject.getJSONObject("content").getString("rendered"));
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });
        SingletonVolley.getInstance(SitePageLoader.this).addToRequestQueue(stringRequest);

    }
}
