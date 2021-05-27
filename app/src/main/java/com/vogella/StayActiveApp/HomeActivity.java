package com.vogella.StayActiveApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {
    Button btn_workoutList;
    Button btn_myWorkout;
    Button btn_workoutStats;
    Button btn_yogaList;
    Button btn_sittingList;

    private TextView qtextView;
    Button randomeQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_workoutList = findViewById(R.id.workoutList);
        btn_myWorkout = findViewById(R.id.myWorkout);
        btn_workoutStats = findViewById(R.id.workoutStats);
        btn_yogaList = findViewById(R.id.yoga);
        btn_sittingList = findViewById(R.id.sittingWbutton);

        qtextView = findViewById(R.id.QuoteView);
        randomeQuote = findViewById(R.id.randomeQuote);

        btn_workoutList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_myWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WorkoutRecycler.class);
                startActivity(intent);
            }
        });

        btn_workoutStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WorkoutsStatsActivity.class);
                startActivity(intent);
            }
        });

        btn_yogaList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SecondCategoryActivity.class);
                startActivity(intent);
            }
        });

        btn_sittingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SittingWorkoutActivity.class);
                startActivity(intent);
            }
        });

        randomeQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuote();
            }
        });

    }
    public void showQuote() {
        shuffleQuotes();
        qtextView.setText(quoteArray[0].getQuote());
    }

    Quotes q1 = new Quotes("Eat well. Move Daily. Hydrate Often. Sleep Lots. Love Your Body. Repeat for Life.");
    Quotes q2 = new Quotes("The greatest weapon against stress is our ability to choose one thought over another.");
    Quotes q3 = new Quotes("Two things you can control.  Attitude and Effort.");
    Quotes q4 = new Quotes("Work hard in silence. Let success be your noise.");
    Quotes q5 = new Quotes("If you start now you'll begin seeing results one day earlier than if you started tomorrow.");
    Quotes q6 = new Quotes("Sometimes you wont feel like working out but then you do it and it makes your day.");
    Quotes q7 = new Quotes("Push yourself. No one else is going to do it for you.");
    Quotes q8 = new Quotes("Your strongest muscle and worst enemy is your mind. Train it well.");
    Quotes q9 = new Quotes("Exercise not only changes your body, it changes your attitude and your mood.");
    Quotes q10 = new Quotes("Even if you can't physically see the results in front of you, Never get discouraged.");

    Quotes [] quoteArray = new Quotes[]{
        q1,q2,q3,q4,q5,q6,q7,q8,q9,q10
    };

    public void shuffleQuotes() {
        Collections.shuffle(Arrays.asList(quoteArray));
    }
}