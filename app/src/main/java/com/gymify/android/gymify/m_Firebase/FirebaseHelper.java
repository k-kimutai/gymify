package com.gymify.android.gymify.m_Firebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.gymify.android.gymify.m_model.model;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;
    ArrayList<model> workouts=new ArrayList<>();

    public FirebaseHelper(DatabaseReference db){
        this.db=db;
    }
    public Boolean save(model workout)
    {
        if(workout==null){
            saved=false;
        }else{
            try{
                db.child("Workout").push().setValue(workout);
                saved=true;
            }catch (DatabaseException e){e.printStackTrace();
            saved=false;
            }
        }
        return saved;
    }
    private void fetchData(DataSnapshot dataSnapshot){
        workouts.clear();
        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            model workout=ds.getValue(model.class);
            workouts.add(workout);
        }
    }

    public ArrayList<model> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return workouts;
    }

}
