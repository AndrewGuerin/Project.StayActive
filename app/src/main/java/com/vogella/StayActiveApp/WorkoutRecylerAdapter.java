package com.vogella.StayActiveApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WorkoutRecylerAdapter extends RecyclerView.Adapter<WorkoutRecylerAdapter.ViewHolder> {

    private static final String Tag = "RecycleView";
    private Context context;
    private ArrayList<MyWorkoutsRetrieve> fbWorkoutList;

    public WorkoutRecylerAdapter(Context context, ArrayList<MyWorkoutsRetrieve> fbWorkoutList) {
        this.context = context;
        this.fbWorkoutList = fbWorkoutList;
    }

    @NonNull
    @Override
    public WorkoutRecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myworkout_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.fbTitleView.setText(fbWorkoutList.get(position).getTitle());
        holder.fbDescView.setText(fbWorkoutList.get(position).getDescription());
        holder.fbVidUrlView.setText(fbWorkoutList.get(position).getUrl());
        Picasso.get().load(fbWorkoutList.get(position).getImageUrl()).into(holder.imageView);

        holder.wv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("WorkoutData",fbWorkoutList.get(position));
                Intent intent = new Intent(context,MyWorkoutDisplayActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fbWorkoutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //widgets
        ImageView imageView;
        TextView fbTitleView, fbDescView, fbVidUrlView;
        View wv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            fbTitleView = itemView.findViewById(R.id.fbTitleTextView);
            fbDescView = itemView.findViewById(R.id.descriptionTextView);
            fbDescView.setVisibility(View.GONE);
            fbVidUrlView = itemView.findViewById(R.id.fbvideoUrl);
            fbVidUrlView.setVisibility(View.GONE);
            wv = itemView;
        }
    }
}
