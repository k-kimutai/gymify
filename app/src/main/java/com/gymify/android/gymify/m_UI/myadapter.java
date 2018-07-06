package com.gymify.android.gymify.m_UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymify.android.gymify.R;
import com.gymify.android.gymify.m_model.model;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myviewholder> {
     Context c;
     ArrayList<model> workouts;
    SwipeRefreshLayout swiper;

    public myadapter(Context c, ArrayList<model> workouts) {
        this.c = c;
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.nameworkout.setText(workouts.get(position).getDesc());
        holder.namesets.setText(workouts.get(position).getSets());
        holder.namereps.setText(workouts.get(position).getReps());
        holder.namedate.setText(workouts.get(position).getDate());

    }

    @Override
    public int getItemCount()
    {
        return workouts.size();
    }
}
