package com.vogella.StayActiveApp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    RecyclerView videoList;
    VideoAdapter adapter;
    List<Video> all_videos;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        all_videos = new ArrayList<>();

        videoList = findViewById(R.id.videoList);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this,all_videos);
        videoList.setAdapter(adapter);
        getJsonData();

    }

    private void getJsonData() {
        String URL = "https://raw.githubusercontent.com/Fitness-excercise/excer/main/fitness.json";
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

                        Video videoview = new Video();
                        videoview.setTitle(video.getString("title"));
                        videoview.setDescription(video.getString("description"));
                       videoview.setAuthor(video.getString("subtitle"));
                       videoview.setImageUrl(video.getString("thumb"));
                       JSONArray videoUrl = video.getJSONArray("sources");
                       videoview.setVideoUrl(videoUrl.getString(0));

                        all_videos.add(videoview);
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