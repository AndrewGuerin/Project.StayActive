package com.vogella.StayActiveApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondCategoryActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    RecyclerView secondCat;
    VideoAdapter adapter;
    List<Video> all_videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_category);
        all_videos = new ArrayList<>();

        secondCat = findViewById(R.id.secondCategory);
        secondCat.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this,all_videos);
        secondCat.setAdapter(adapter);
        getJsonData();

    }

    private void getJsonData() {
        String URL = "https://raw.githubusercontent.com/Fitness-excercise/excer/main/data.json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.d(TAG, "onResponse: "+ response);
                try {
                    JSONArray categories = response.getJSONArray("categories");
                    JSONObject categoriesData = categories.getJSONObject(0);
                    JSONArray videos = categoriesData.getJSONArray("videos");

                    //Log.d(TAG, "onResponse: "+ videos);

                    for (int index = 0; index< videos.length();index++){
                        JSONObject video = videos.getJSONObject(index);

                        Video vid = new Video();

                        vid.setTitle(video.getString("title"));
                        vid.setDescription(video.getString("description"));
                        vid.setAuthor(video.getString("subtitle"));
                        vid.setImageUrl(video.getString("thumb"));
                        JSONArray videoUrl = video.getJSONArray("sources");
                        vid.setVideoUrl(videoUrl.getString(0));

                        all_videos.add(vid);
                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });

        requestQueue.add(objectRequest);
    }
}