package com.example.jennifer.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "MainActivity";
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu m){
        Log.i("TestToolbar", "In onCreateOptionsMenu()");
        getMenuInflater().inflate(R.menu.menu_main, m);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        Log.i(ACTIVITY_NAME, "In onOptionsItemSelected()");
        int id = mi.getItemId();
        switch(id){
            case R.id.action_thermostat:
                startActivity(new Intent(MainActivity.this, HouseThermostatActivity.class));
                break;
            case R.id.action_tracking:
                //start intent
                break;
            case R.id.action_nutrition:
                //start intent
                break;
            case R.id.action_automobile:
                //start intent
                break;

        }
        return super.onOptionsItemSelected(mi);
    }
}
