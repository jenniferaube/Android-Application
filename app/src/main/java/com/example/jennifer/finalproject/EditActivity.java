package com.example.jennifer.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class EditActivity extends FragmentActivity implements EditFragment.OnMessageSelectedListener{
    private static SQLiteDatabase chatDatabase;
    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Bundle bundle = getIntent().getExtras();
        String idPassed = bundle.getString(HouseThermostatActivity.ID);
        String day = bundle.getString(HouseThermostatActivity.DAY);
        String time = bundle.getString(HouseThermostatActivity.TIME);
        String temp = bundle.getString(HouseThermostatActivity.TEMP);
        bundle.putString("id", idPassed);
        bundle.putString("day", day);
        bundle.putString("time", time);
        bundle.putString("temp", temp);

        EditFragment fragment = new EditFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, fragment).commit();

    }

    @Override
    public void onMessageSelected(int position) {
        Context context = getApplicationContext();
        helper = new DatabaseHelper(context);
        chatDatabase = helper.getWritableDatabase();
        String [] args={String.valueOf(position)};
        chatDatabase.delete(DatabaseHelper.name, DatabaseHelper.KEY_ID + "=?", args);
        Log.i("EditActivity", "In onMessageSelected");
    }
}
