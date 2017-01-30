package com.gmonetix.gmonetix;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gmonetix.gmonetix.constant.Const;
import com.gmonetix.gmonetix.helper.Font;
import com.gmonetix.gmonetix.helper.SingletonVolley;
import com.gmonetix.gmonetix.model.JsonDataModel;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private String url = Const.url;
    public static List<JsonDataModel> JsonDatas ;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_splash_screen);

        TextView gmonetix = (TextView) findViewById(R.id.tv_gmonetix);
        TextView developers_environment = (TextView) findViewById(R.id.tv_developers_environment);
        TextView copyright = (TextView) findViewById(R.id.tv_copyright);
        Font font = new Font();
        font.setFont(getApplicationContext(),gmonetix);
        font.setFont(getApplicationContext(),developers_environment);
        font.setFont(getApplicationContext(),copyright);

        JsonDatas = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    Gson gson = new Gson();
                    JSONArray ParentArray = new JSONArray(s);
                    for (int i= 0;i<ParentArray.length();i++){
                        JSONObject ParentObject = ParentArray.getJSONObject(i);
                        JsonDataModel jsonDataModel = gson.fromJson(ParentObject.toString(), JsonDataModel.class);
                        jsonDataModel.setId(ParentObject.getInt("id"));
                        jsonDataModel.setDate(ParentObject.getString("date").substring(0,10));
                        jsonDataModel.setLink(ParentObject.getString("link"));
                        jsonDataModel.setTitleRendered(ParentObject.getJSONObject("title").getString("rendered"));
                        jsonDataModel.setFeaturedMediaUrl(ParentObject.getJSONObject("better_featured_image").getString("source_url"));
                        JsonDatas.add(jsonDataModel);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    if (JsonDatas!=null) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), PermissionTransferToHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                writeToFile(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.toast_network_error),Toast.LENGTH_LONG).show();
                try {
                    Gson gson = new Gson();

                    JSONArray ParentArray = new JSONArray(readFromFile());
                    for (int i= 0;i<ParentArray.length();i++){
                        JSONObject ParentObject = ParentArray.getJSONObject(i);
                        JsonDataModel jsonDataModel = gson.fromJson(ParentObject.toString(), JsonDataModel.class);
                        jsonDataModel.setId(ParentObject.getInt("id"));
                        jsonDataModel.setDate(ParentObject.getString("date").substring(0,10));
                        jsonDataModel.setLink(ParentObject.getString("link"));
                        jsonDataModel.setTitleRendered(ParentObject.getJSONObject("title").getString("rendered"));
                        jsonDataModel.setFeaturedMediaUrl(ParentObject.getJSONObject("better_featured_image").getString("source_url"));
                        JsonDatas.add(jsonDataModel);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    if (JsonDatas!=null) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), PermissionTransferToHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void writeToFile(String data){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("dont_delete.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromFile(){
        String result = "";
        try {
            InputStream inputStream = openFileInput("dont_delete.txt");
            if (inputStream!=null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String tempString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((tempString = bufferedReader.readLine())!=null){
                    stringBuilder.append(tempString);
                }
                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
