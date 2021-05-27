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

public class WorkoutRecycler extends AppCompatActivity {

    //widgets
    RecyclerView recyclerView;

    private DatabaseReference reference;

    //variables
    private ArrayList<MyWorkoutsRetrieve> myWorkoutsRetrieveArrayList;
    private WorkoutRecylerAdapter recyclerAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_recycler);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //firebase
        reference = FirebaseDatabase.getInstance().getReference();

        //ArrayList
        myWorkoutsRetrieveArrayList = new ArrayList<>();

        //clear Arraylist
        ClearAll();

        //get Data
        GetDataFromFB();
    }

    private void GetDataFromFB() {
        Query query = reference.child("WorkoutData");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MyWorkoutsRetrieve myWorkoutsRetrieve = new MyWorkoutsRetrieve();

                    myWorkoutsRetrieve.setImageUrl(snapshot.child("imageUrl").getValue().toString());
                    myWorkoutsRetrieve.setTitle(snapshot.child("title").getValue().toString());
                    myWorkoutsRetrieve.setDescription(snapshot.child("description").getValue().toString());
                    myWorkoutsRetrieve.setUrl(snapshot.child("url").getValue().toString());

                    myWorkoutsRetrieveArrayList.add(myWorkoutsRetrieve);
                }
                recyclerAdapter = new WorkoutRecylerAdapter(getApplicationContext(), myWorkoutsRetrieveArrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ClearAll() {
        if(myWorkoutsRetrieveArrayList != null) {
            myWorkoutsRetrieveArrayList.clear();

            if(recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        myWorkoutsRetrieveArrayList = new ArrayList<>();
    }
}