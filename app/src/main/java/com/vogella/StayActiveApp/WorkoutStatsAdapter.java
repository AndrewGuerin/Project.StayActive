package com.vogella.StayActiveApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutStatsAdapter extends RecyclerView.Adapter<WorkoutStatsAdapter.ViewHolder> {
    private static final String Tag = "Recent Workouts";
    private Context context;
    private ArrayList<UserWorkoutStats> statsArrayList;

    public WorkoutStatsAdapter(Context context, ArrayList<UserWorkoutStats> statsArrayList) {
        this.context = context;
        this.statsArrayList = statsArrayList;
    }


    @NonNull
    @Override
    public WorkoutStatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workoutstats_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutStatsAdapter.ViewHolder holder, int position) {
        holder.workoutStatsTitle.setText(statsArrayList.get(position).getWorkoutName());
        holder.workoutStatsScore.setText(statsArrayList.get(position).getWorkoutScore());
        holder.stepData.setText(statsArrayList.get(position).getWorkoutStep());
    }

    @Override
    public int getItemCount() {
        return statsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView workoutStatsTitle, workoutStatsScore, stepData;
        View sv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutStatsTitle = itemView.findViewById(R.id.statsWorkoutTitle);
            workoutStatsScore = itemView.findViewById(R.id.statsWorkoutScore);
            stepData = itemView.findViewById(R.id.statsSteps);
            sv = itemView;
        }


    }
}
