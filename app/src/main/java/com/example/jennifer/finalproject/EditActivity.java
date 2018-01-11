package com.example.jennifer.finalproject;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "EditFragment";
    String id, mDay, mTime, mTemp, mType, day, time, temp, type;
    int resultCode;
    //View view;
    AlertDialog.Builder builder;
    ArrayList<String> list = new ArrayList<String>();
    Database helper;
    private static SQLiteDatabase chatDatabase;
    ContentValues cv;
    Spinner spinner;
    EditText temperature;
    TextView timeTxt, typeTxt;
    RadioButton celsius, farenheit;
    ProgressBar bar;
    RelativeLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        builder = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        day = bundle.getString("dayofweek");
        time = bundle.getString("time");
        temp = bundle.getString("temperature");
        id = bundle.getString("ID");
        type = bundle.getString("type");
        resultCode = Integer.parseInt(id);
        bar = (ProgressBar)findViewById(R.id.progressBarEdit);
        bar.setVisibility(View.INVISIBLE);

        celsius = (RadioButton) findViewById(R.id.celsiusEdit);
        farenheit = (RadioButton) findViewById(R.id.farenheitEdit);
        if(type.equals("\u2103")){
            celsius.setChecked(true);
            type = "\u2103";
        }
        if(type.equals("\u2109")){
            farenheit.setChecked(true);
            type = "\u2109";
        }
        Context context = getApplicationContext();
        helper = new Database(context);
        chatDatabase = helper.getWritableDatabase();
        cv = new ContentValues();

        spinner =(Spinner) findViewById(R.id.spinnerEditMode);
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(day)){
                index = i;
            }
        }
        spinner.setSelection(index);

        timeTxt = (TextView) findViewById(R.id.timeTxtView);
        timeTxt.setText(time);
        timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });
        temperature = (EditText) findViewById(R.id.temperatureEditView);
        temperature.setText(temp);
        view = (RelativeLayout) findViewById(R.id.relativeLayout);
        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "delete button clicked");
               /* onMessageSelected(resultCode);
                Snackbar snackbar = Snackbar.make(view, "Your item has been deleted.", Snackbar.LENGTH_SHORT);
                snackbar.show();
                setResult(resultCode);
                finish();*/
               bar.setVisibility(View.VISIBLE);
               DeleteQuery query = new DeleteQuery();
               query.execute();
            }
        });
        Button saveButton = (Button)findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(celsius.isChecked()){
                    type = "\u2103";
                }
                if(farenheit.isChecked()){
                    type = "\u2109";
                }
                bar.setVisibility(View.VISIBLE);

                //custom dialog box save or save as choice
                builder.setTitle("How would you like to save?");
                builder.setMessage("If you like to save the changes you made to this alarm, click save. \nIf you would like to keep the old alarm and save this one as new, click save as.");
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //delete old and add new
                        getStrings();
                        onMessageSelected(resultCode);
                        helper.onOpen(chatDatabase);
                        addList();

                        cv.put("day", mDay);
                        cv.put("time", mTime);
                        cv.put("temperature", mTemp);
                        cv.put("type", mType);
                        chatDatabase.insert(Database.name, null, cv);
                        setResult(resultCode);

                        finish();
                    }
                });
                builder.setNegativeButton("Save As", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //add new
                        getStrings();
                        addList();

                        cv.put("day", mDay);
                        cv.put("time", mTime);
                        cv.put("temperature", mTemp);
                        cv.put("type", mType);
                        chatDatabase.insert(Database.name, null, cv);
                        setResult(resultCode);
                        finish();
                    }
                });
                Dialog customDialog = builder.create();
                customDialog.show();
            }
        });
    }
    public void onMessageSelected(int position) {
        Log.i("EditActivity", "In onMessageSelected");
        String [] args={String.valueOf(position)};
        chatDatabase.delete(Database.name, Database.KEY_ID + "=?", args);
    }
    public void getStrings(){
        mDay = spinner.getSelectedItem().toString();
        mTime = timeTxt.getText().toString();
        mTemp = temperature.getText().toString();
        mType = type;
    }
    public void addList(){
        list.add(mDay);
        list.add(mTime);
        list.add(mTemp);
        list.add(mType);
    }

    class DeleteQuery extends AsyncTask<String, Integer, String> {
        Database helper = new Database(getApplicationContext());

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.i("EditActivity", "In doInBackground()");

                publishProgress(30);
                Thread.sleep(1000);
                onMessageSelected(resultCode);
                publishProgress(60);
                Thread.sleep(1000);
                setResult(resultCode);
                publishProgress(90);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... value) {
            Log.i("EditActivity", "In onProgressUpdate");
            bar.setProgress(value[0]);
        }
        @Override
        public void onPostExecute(String result) {
            Log.i("EditActivity", "In onPostExecute()");
            bar.setVisibility(View.INVISIBLE);
            Snackbar snackbar = Snackbar.make(view, "Your item has been deleted.", Snackbar.LENGTH_SHORT);
            snackbar.show();
            finish();
        }
    }
}

