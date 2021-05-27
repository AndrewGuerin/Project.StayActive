package com.vogella.StayActiveApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkoutsStatsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference reference;

    private ArrayList<UserWorkoutStats> workoutStatsList;
    private WorkoutStatsAdapter workoutStatsAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts_stats);

        recyclerView = findViewById(R.id.recyclerView2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //firebase
        reference = FirebaseDatabase.getInstance().getReference();
        
        workoutStatsList = new ArrayList<>();

        ClearAll();

        GetDataFromFB();

    }

    private void GetDataFromFB() {
        Query query = reference.child("WorkoutData+Score");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserWorkoutStats userWorkoutStats = new UserWorkoutStats();

                    userWorkoutStats.setWorkoutName(snapshot.child("title").getValue().toString());
                    userWorkoutStats.setWorkoutScore(snapshot.child("score").getValue().toString());
                    userWorkoutStats.setWorkoutStep(snapshot.child("stepdata").getValue().toString());

                    workoutStatsList.add(userWorkoutStats);
                }
                workoutStatsAdapter = new WorkoutStatsAdapter(getApplication(), workoutStatsList);
                recyclerView.setAdapter(workoutStatsAdapter);
                workoutStatsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ClearAll() {
        if(workoutStatsList != null) {
            workoutStatsList.clear();

            if(workoutStatsAdapter != null) {
                workoutStatsAdapter.notifyDataSetChanged();
            }
        }
        workoutStatsList = new ArrayList<>();
    }
}