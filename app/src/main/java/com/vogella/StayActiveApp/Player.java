package com.vogella.StayActiveApp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Player extends AppCompatActivity {

    public static final String TAG = "TAG";
    ProgressBar spiiner;
    ImageView fullScreenOp;
    FrameLayout frameLayout;
    VideoView videoPlayer;

    //new
    EditText videoTitle, videoDesc, videoUrlLink, urlImage;

    //new
    Button addToRegime;
    Button goToWorkout;
    DatabaseReference databaseReference;
    WorkoutData workoutData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //new
        
        spiiner = findViewById(R.id.fbprogressBar);
        fullScreenOp = findViewById(R.id.fbfullScreenOp);
        frameLayout = findViewById(R.id.fbframeLayout);

        videoTitle = findViewById(R.id.fbWorkoutTitle);
        videoDesc = findViewById(R.id.fbvideoDesc);
        videoUrlLink = findViewById(R.id.fbvideoUrlfb);
        urlImage = findViewById(R.id.fbimageUrl);


        addToRegime = findViewById(R.id.beginworkout);
        goToWorkout = findViewById(R.id.buttonToWorkout);
        workoutData = new WorkoutData();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("WorkoutData");

        Intent i = getIntent();
        final Bundle data = i.getExtras();
        Video v = (Video) data.getSerializable("videoData");

        getSupportActionBar().setTitle(v.getTitle());

        //final TextView title = findViewById(R.id.videoTitle);
        //final TextView desc = findViewById(R.id.videoDesc);

        videoPlayer = findViewById(R.id.fbvideoView);

        videoTitle.setText(v.getTitle());
        videoDesc.setText(v.getDescription());
        videoUrlLink.setText(v.getVideoUrl());
        videoUrlLink.setVisibility(View.GONE);
        urlImage.setText(v.getImageUrl());
        urlImage.setVisibility(View.GONE);
        Uri videoUrl = Uri.parse(v.getVideoUrl());
        videoPlayer.setVideoURI(videoUrl);
        MediaController mc = new MediaController(this);
        videoPlayer.setMediaController(mc);


        addToRegime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutData.setTitle(videoTitle.getText().toString().trim());
                workoutData.setDescription(videoDesc.getText().toString().trim());
                workoutData.setUrl(videoUrlLink.getText().toString().trim());
                workoutData.setImageUrl(urlImage.getText().toString().trim());

                databaseReference.push().setValue(workoutData);
                Toast.makeText(Player.this, "Data inserted", Toast.LENGTH_LONG).show();
            }
        });

        goToWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Player.this, WorkoutRecycler.class);
                startActivity(intent);
            }
        });

        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoPlayer.start();
                spiiner.setVisibility(View.GONE);
            }
        });


        fullScreenOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();
                fullScreenOp.setVisibility(View.GONE);
                frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
                videoPlayer.setLayoutParams(new FrameLayout.LayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        fullScreenOp.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int heightValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,220,getResources().getDisplayMetrics());
        frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heightValue)));
        videoPlayer.setLayoutParams(new FrameLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heightValue)));
        int orientation = getResources().getConfiguration().orientation;
        
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            super.onBackPressed();
        }
    }

}