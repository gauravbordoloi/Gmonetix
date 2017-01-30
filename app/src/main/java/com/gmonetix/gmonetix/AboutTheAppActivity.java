package com.gmonetix.gmonetix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.gmonetix.gmonetix.helper.Font;

public class AboutTheAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Font font = new Font();
        TextView toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        toolBarTextView.setText("About the app");
        font.setFont(getApplicationContext(), toolBarTextView);

        TextView version = (TextView) findViewById(R.id.version);
        TextView version_number = (TextView) findViewById(R.id.version_number);
        TextView open_source_libraries = (TextView) findViewById(R.id.open_source_libraries);
        TextView volley = (TextView) findViewById(R.id.volley);
        TextView gson = (TextView) findViewById(R.id.gson);
        TextView clans_fab = (TextView) findViewById(R.id.clans_fab);
        TextView app_copyrights = (TextView) findViewById(R.id.app_copyrights);
        TextView copyright = (TextView) findViewById(R.id.copyright);
        TextView desc = (TextView) findViewById(R.id.desc);

        font.setFont(getApplicationContext(), version);
        font.setFont(getApplicationContext(), version_number);
        font.setFont(getApplicationContext(), open_source_libraries);
        font.setFont(getApplicationContext(), volley);
        font.setFont(getApplicationContext(), gson);
        font.setFont(getApplicationContext(), clans_fab);
        font.setFont(getApplicationContext(), app_copyrights);
        font.setFont(getApplicationContext(), copyright);
        font.setFont(getApplicationContext(), desc);
    }
}
