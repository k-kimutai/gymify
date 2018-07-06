package com.gymify.android.gymify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gymify.android.gymify.m_UI.btm_nav_helper;

import static com.gymify.android.gymify.m_UI.btm_nav_helper.disableShiftMode;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=(BottomNavigationView) findViewById(R.id.navigation);
        btm_nav_helper.disableShiftMode(bottomNavigationView);
        Menu menu=bottomNavigationView .getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.locations:
                        break;
                    case R.id.workouts:
                        Intent intent2=new Intent(MainActivity.this,addwork.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityForResult(intent2, 0);
                        overridePendingTransition(0,0); //0 for no animation
                        break;
                    case R.id.instructors:
                        Intent intent3=new Intent(MainActivity.this,instructors.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityForResult(intent3, 0);
                        overridePendingTransition(0,0); //0 for no animation
                        break;
                    case R.id.profile:
                        break;

                }
                return;
            }
        });
    }

}
