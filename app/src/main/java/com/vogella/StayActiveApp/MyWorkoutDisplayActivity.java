package com.vogella.StayActiveApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class MyWorkoutDisplayActivity extends AppCompatActivity implements SensorEventListener {
    public static final String TAG = "TAG";
    ProgressBar spiiner;
    ImageView fullScreenOp;
    FrameLayout frameLayout;
    EditText videoTitle, videoUrlLink, urlImage, tvscore;
    TextView videoDesc;
    VideoView videoPlayer;

    Button beginWorkout;
    WorkoutData fbWorkoutData;
    DatabaseReference databaseReference;

    //step counter
    TextView stepsCount;
    SensorManager sensorManager;
    boolean counting = false;

    //timer
    private static final long START_TIME_IN_MILLIS = 120000;
    private TextView tvCountdown;
    private Button buttonStartStop, buttonReset;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private String score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workout_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spiiner = findViewById(R.id.progressBar);
        fullScreenOp = findViewById(R.id.fullScreenOp);
        frameLayout = findViewById(R.id.frameLayout);

        videoTitle = findViewById(R.id.WorkoutTitle);
        videoDesc = findViewById(R.id.videoDesc);
        videoUrlLink = findViewById(R.id.videoUrlfb);
        videoUrlLink.setVisibility(View.GONE);
        urlImage = findViewById(R.id.imageUrl);
        urlImage.setVisibility(View.GONE);
        tvscore = findViewById(R.id.tvScore);
        tvscore.setVisibility(View.INVISIBLE);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("WorkoutData+Score");

        //steps counter
        stepsCount = (TextView) findViewById(R.id.stepCount);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        beginWorkout = findViewById(R.id.beginworkout);
        fbWorkoutData = new WorkoutData();

        Intent intent = getIntent();

        final Bundle data = intent.getExtras();
        MyWorkoutsRetrieve work = (MyWorkoutsRetrieve) data.getSerializable("WorkoutData");

        getSupportActionBar().setTitle(work.getTitle());

        videoPlayer = findViewById(R.id.videoView);
        videoTitle.setText(work.getTitle());
        videoDesc.setText(work.getDescription());
        videoUrlLink.setText(work.getVideoUrl());
        urlImage.setText(work.getImageUrl());

        //timer
        tvCountdown = findViewById(R.id.tvCountdown);
        buttonStartStop = findViewById(R.id.buttonStart);
        buttonReset = findViewById(R.id.buttonReset);

        Uri newVideoUrl = Uri.parse(work.getUrl());

        videoPlayer.setVideoURI(newVideoUrl);

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

        buttonStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimerRunning) {
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
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

    @Override
    protected void onResume() {
        super.onResume();
        counting = true;
        Sensor sensorCount = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(sensorCount != null) {
            sensorManager.registerListener(this, sensorCount, SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this, "No sensor was found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        counting = false;
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(counting) {
            stepsCount.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                buttonStartStop.setText("Start");
                buttonStartStop.setVisibility(View.INVISIBLE);
                buttonReset.setVisibility(View.VISIBLE);
                tvscore.setText("500");

                fbWorkoutData.setTitle(videoTitle.getText().toString().trim());
                fbWorkoutData.setDescription(videoDesc.getText().toString().trim());
                fbWorkoutData.setUrl(videoUrlLink.getText().toString().trim());
                fbWorkoutData.setImageUrl(urlImage.getText().toString().trim());
                fbWorkoutData.setScore(tvscore.getText().toString().trim());
                fbWorkoutData.setStepdata(stepsCount.getText().toString().trim());
                databaseReference.push().setValue(fbWorkoutData);
                Toast.makeText(MyWorkoutDisplayActivity.this, "Data has been inserted to the Database", Toast.LENGTH_LONG).show();
            }
        }.start();
        isTimerRunning = true;
        buttonStartStop.setText("Pause");
        buttonReset.setVisibility(View.INVISIBLE);
    }
    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
        buttonStartStop.setText("Start");
        buttonReset.setVisibility(View.VISIBLE);
    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        buttonReset.setVisibility(View.INVISIBLE);
        buttonStartStop.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText() {
        int minutes = (int) mTimeLeftInMillis / 1000 / 60;
        int seconds = (int) mTimeLeftInMillis / 1000 % 60;
        String timeLeftover = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        tvCountdown.setText(timeLeftover);
    }
}