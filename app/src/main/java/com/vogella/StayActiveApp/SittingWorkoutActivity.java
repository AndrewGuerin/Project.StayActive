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

public class SittingWorkoutActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    RecyclerView sittingExcercise;
    VideoAdapter adapter;
    List<Video> all_videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting_workout);
        all_videos = new ArrayList<>();

        sittingExcercise = findViewById(R.id.sittingWRecycler);
        sittingExcercise.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this,all_videos);
        sittingExcercise.setAdapter(adapter);
        getJsonData();

    }

    private void getJsonData() {
        String URL = "https://raw.githubusercontent.com/Fitness-excercise/excer/main/sitting.json";
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

                    for (int i = 0; i< videos.length();i++){
                        JSONObject video = videos.getJSONObject(i);

                        Video v = new Video();

                        v.setTitle(video.getString("title"));
                        v.setDescription(video.getString("description"));
                        v.setAuthor(video.getString("subtitle"));
                        v.setImageUrl(video.getString("thumb"));
                        JSONArray videoUrl = video.getJSONArray("sources");
                        v.setVideoUrl(videoUrl.getString(0));

                        all_videos.add(v);
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