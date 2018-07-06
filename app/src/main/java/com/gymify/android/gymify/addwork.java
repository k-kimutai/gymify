package com.gymify.android.gymify;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gymify.android.gymify.m_Firebase.FirebaseHelper;
import com.gymify.android.gymify.m_UI.btm_nav_helper;
import com.gymify.android.gymify.m_UI.myadapter;
import com.gymify.android.gymify.m_model.model;

import java.util.Calendar;

public class  addwork extends AppCompatActivity {
    DatabaseReference db;
    DatePickerDialog picker;
    FirebaseHelper helper;
    myadapter adapter;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefresh;


    EditText nameworkout,date,set,reps;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwork);
        rv=(RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseDatabase.getInstance().getInstance().getReference();
        helper=new FirebaseHelper(db);

        adapter=new myadapter(this,helper.retrieve());
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swiper);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rv.setAdapter(adapter);
                swipeRefresh.setRefreshing(false);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });


        BottomNavigationView bottomNavigationView=(BottomNavigationView) findViewById(R.id.navigation);
        btm_nav_helper.disableShiftMode(bottomNavigationView);
        Menu menu=bottomNavigationView .getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.locations:
                        Intent intent=new Intent(addwork.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityForResult(intent, 0);
                        overridePendingTransition(0,0); //0 for no animation
                        break;
                    case R.id.workouts:
                        break;
                    case R.id.instructors:
                        Intent intent3=new Intent(addwork.this,instructors.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityForResult(intent3, 0);
                        overridePendingTransition(0,0); //0 for no animation
                        break;
                    case R.id.profile:
                        break;

                }
            }
        });
    }
    public void displayInputDialog()
    {
        //CREATE DIALOG
        Dialog d=new Dialog(this);
        d.setTitle("Add Workout Technique");
        d.setContentView(R.layout.activity_dialog_layout);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //date picker dialog
        date= (EditText) d.findViewById(R.id.date);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cldr=Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(addwork.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

       nameworkout = (EditText) d.findViewById(R.id.nameworkout);
        date= (EditText) d.findViewById(R.id.date);
        set= (EditText) d.findViewById(R.id.set);
        reps= (EditText) d.findViewById(R.id.reps);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String wkout=nameworkout.getText().toString();
                String da=date.getText().toString();
                String se=set.getText().toString();
                String re=reps.getText().toString();

                //SET DATA
                model s=new model();
                s.setDesc(wkout);
                s.setDate(da);
                s.setReps(re);
                s.setSets(se);

                //SAVE
                if(wkout != null && wkout.length()>0)
                {
                    if(helper.save(s))
                    {
                        nameworkout.setText("");
                        date.setText("");
                        set.setText("");
                        reps.setText("");
                        Toast.makeText(addwork.this,"Saved",Toast.LENGTH_SHORT).show();
                        adapter=new myadapter(addwork.this,helper.retrieve());
                        rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }
                }else
                {
                    Toast.makeText(addwork.this, "Workout Technique Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        d.show();
        d.getWindow().setAttributes(lp);
    }
}