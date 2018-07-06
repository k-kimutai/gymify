package com.gymify.android.gymify;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.gymify.android.gymify.m_UI.btm_nav_helper;
import com.gymify.android.gymify.m_model.instructor;


public class instructors extends AppCompatActivity {
    RecyclerView rv1;
     SwipeRefreshLayout swipeRefresh1;

    DatabaseReference myref;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors);
        rv1 = (RecyclerView) findViewById(R.id.rv1);
        rv1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv1.setLayoutManager(new LinearLayoutManager(this));
        myref= FirebaseDatabase.getInstance().getReference().child("/Instructor");
        final FirebaseRecyclerAdapter<instructor,iviewholder> recyclerAdapter=new FirebaseRecyclerAdapter<instructor,iviewholder>(
                instructor.class,
                R.layout.model2,
                iviewholder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(iviewholder viewHolder, instructor model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setEmail(model.getEmail());
                viewHolder.setPhone(model.getPhone());
                viewHolder.setSpec(model.getSpec());

            }
        };
        rv1.setAdapter(recyclerAdapter);
        swipeRefresh1=(SwipeRefreshLayout)findViewById(R.id.swiper2);
        swipeRefresh1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rv1.setAdapter(recyclerAdapter);
                swipeRefresh1.setRefreshing(false);
            }
        });


        BottomNavigationView bottomNavigationView=(BottomNavigationView) findViewById(R.id.navigation);
        btm_nav_helper.disableShiftMode(bottomNavigationView);
        Menu menu=bottomNavigationView .getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.locations:
                        Intent intent=new Intent(instructors.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityForResult(intent, 0);
                        overridePendingTransition(0,0); //0 for no animation
                        break;
                    case R.id.workouts:
                        Intent intent1=new Intent(instructors.this,addwork.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityForResult(intent1, 0);
                        overridePendingTransition(0,0); //0 for no animation
                        break;
                    case R.id.instructors:
                        Intent intent3=new Intent(instructors.this,instructors.class);
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
    public static class iviewholder extends RecyclerView.ViewHolder {
        View mView;
        TextView textname;
        TextView textspecs;
        TextView textemail;
        TextView textphone;

        public iviewholder(View itemView) {
            super(itemView);
            mView=itemView;
            textname = (TextView)itemView.findViewById(R.id.textname);
            textspecs = (TextView) itemView.findViewById(R.id.textspecs);
            textemail=(TextView) itemView.findViewById(R.id.textemail);
            textphone=(TextView) itemView.findViewById(R.id.textphone);

        }
        public void setName(String name)
        {
            textname.setText(name+"");
        }
        public void setSpec(String spec)
        {
            textspecs.setText(spec);
        }
        public void setEmail(String email)
        {
            textemail.setText(email);
        }
        public void setPhone(String phone)
        {
            textphone.setText(phone);
        }

        {

        }
    }
}