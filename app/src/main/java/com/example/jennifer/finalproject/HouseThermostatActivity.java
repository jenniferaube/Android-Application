package com.example.jennifer.finalproject;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class HouseThermostatActivity extends AppCompatActivity {
    public static java.lang.String DAY = "dayofweek";
    public static java.lang.String TIME = "time";
    public static java.lang.String TEMP = "temperature";
    String ACTIVITY_NAME = "HouseThermostatActivity";
    public static java.lang.String ID = "ID";
    Toolbar toolbar;
    private static SQLiteDatabase chatDatabase;
    Cursor cursor;
    Context context;
    DatabaseHelper helper;
    ArrayList<String> listDay = new ArrayList<String>();
    ArrayList<String> listTime = new ArrayList<String>();
    ArrayList<String> listTemp = new ArrayList<String>();
    ThermostatAdapter thermostatAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_thermostat);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("House Thermostat");
        setSupportActionBar(toolbar);
        listView = (ListView)findViewById(R.id.menuListView);

        thermostatAdapter = new ThermostatAdapter(this);
        listView.setAdapter(thermostatAdapter);
        context = getApplicationContext();
        helper = new DatabaseHelper(context);
        chatDatabase = helper.getWritableDatabase();
        final ContentValues cv = new ContentValues();
        cursor = chatDatabase.query(DatabaseHelper.name, new String[]{DatabaseHelper.KEY_ID, DatabaseHelper.KEY_DAY,  DatabaseHelper.KEY_TIME, DatabaseHelper.KEY_TEMP}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String day = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DAY));
                listDay.add(day);
                String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TIME));
                listTime.add(time);
                String temp = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TEMP));
                listTemp.add(temp);
                //Log.i("HouseThermostatActivity", String.valueOf(list));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        Log.i(ACTIVITY_NAME, "Cursor's column count=" + cursor.getColumnCount());
        for (int i=0; i<cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, cursor.getColumnName(i));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(ACTIVITY_NAME, "listview has been clicked");
                //user clicks on alarm use the id to pass to the editActivity to open the database and get all the info
                Bundle bundle = new Bundle();
                cursor.moveToPosition(position);
                bundle.putString(ID, cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                bundle.putString(DAY, cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DAY)));
                bundle.putString(TIME, cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));
                bundle.putString(TEMP, cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TEMP)));
                Intent editIntent = new Intent(HouseThermostatActivity.this, EditActivity.class);
                editIntent.putExtras(bundle);
                startActivityForResult(editIntent, 2);
            }
        });
    }
   /* public void onMessageSelected(Context c, int position){
        helper = new DatabaseHelper(c);
        chatDatabase = helper.getWritableDatabase();
        String [] args={String.valueOf(position)};
        chatDatabase.delete(DatabaseHelper.name, DatabaseHelper.KEY_ID + "=?", args);

        Log.i(ACTIVITY_NAME, "In onMessageSelected");
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(ACTIVITY_NAME, "In onActivityResult");
        Log.i(ACTIVITY_NAME, "resultcode " + resultCode + " request code " + requestCode);
        Log.i(ACTIVITY_NAME, "listday " + listDay + " listtime " + listTime + " listtemp " + listTemp);
       /* listDay.remove(resultCode);
        listTime.remove(resultCode);
        listTemp.remove(resultCode);*/
        /*thermostatAdapter = new ThermostatAdapter(this);
        listView.setAdapter(thermostatAdapter);*/
        Log.i(ACTIVITY_NAME, "listday " + listDay + " listtime " + listTime + " listtemp " + listTemp);
        startActivity(getIntent());



    }
    class ThermostatAdapter extends ArrayAdapter<String> {

        public ThermostatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public long getItemID(int position) {
            return position;
        }

        public int getCount(){
            return listDay.size();
        }
        public String getItem(int position){
            Log.i("list", String.valueOf(listDay) );
            return listDay.get(position);
        }
        public View getView(int position, View view, ViewGroup parent){
            ViewHolder viewHolder;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(view == null) {
                view = mInflater.inflate(R.layout.alarm_list, null);
                viewHolder = new ViewHolder();
                viewHolder.textViewDay = (TextView) view.findViewById(R.id.dayOfWeekTextView);
                viewHolder.textViewTime = (TextView) view.findViewById(R.id.timeTextView);
                viewHolder.textViewTemp = (TextView) view.findViewById(R.id.tempTextView);
                view.setTag(viewHolder);
                if (position %2 ==0) {
                    view.setBackgroundColor(Color.LTGRAY);
                }else{
                    view.setBackgroundColor(Color.WHITE);
                }

            }else{
                viewHolder = (ViewHolder) view.getTag();
            }

                viewHolder.textViewDay.setText(listDay.get(position));
                viewHolder.textViewTime.setText(listTime.get(position));
                viewHolder.textViewTemp.setText(listTemp.get(position) + "\u2103");
            Log.i(ACTIVITY_NAME, "In ViewHolder: listday " + listDay + " listtime " + listTime + " listtemp " + listTemp);

            return view;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.thermostat_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_home:
                startActivity(new Intent(HouseThermostatActivity.this, HouseThermostatActivity.class));
                return true;
            case R.id.action_add:
                startActivityForResult(new Intent(HouseThermostatActivity.this, AddActivity.class), 1);
                return true;
            case R.id.action_instructions:
                startActivity(new Intent(HouseThermostatActivity.this, InstructionActivity.class));
                return true;
            case R.id.action_help:
                //dialog notification
                int version = DatabaseHelper.VERSION_NUM;
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Do you want to go back");
                builder.setMessage("Author: Jennifer Aube\nVersion: " + version + "\n\nClick on the 3 dots at the top of the page to open a menu to move around the app." +
                        "\nCheck out the instructions page for how to use this app.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                Dialog customDialog = builder.create();
                customDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private static class ViewHolder{
        /*creating new textviews but not directly using them, they are being stored in the viewholder*/
        public TextView textViewDay;
        public TextView textViewTime;
        public TextView textViewTemp;
    }


}
