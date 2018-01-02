 package com.example.jennifer.finalproject;

 import android.app.Dialog;
 import android.app.DialogFragment;
 import android.content.ContentValues;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.database.sqlite.SQLiteDatabase;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.support.v7.app.AlertDialog;
 import android.support.v7.app.AppCompatActivity;
 import android.util.Log;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ProgressBar;
 import android.widget.RadioButton;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;

 import java.util.ArrayList;

 /**
  * The following code is cited from
  * https://android--examples.blogspot.ca/2015/04/timepickerdialog-in-android.html
  */
 public class AddActivity extends AppCompatActivity {
     ProgressBar bar;
     TextView time;
     EditText temperature;
     Button add;
     ArrayList<String> list = new ArrayList<String>();
     private static SQLiteDatabase chatDatabase;
     Database helper;
     String mday, mtime, mtemp, tempType;
     ContentValues cv;
     Spinner daySpinner;
     View view;
     RadioButton celsius, farenheit;
     AlertDialog.Builder builder;
     String spinnerDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Log.i("AddActivity", "In onCreate()");

        bar = (ProgressBar)findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);

        celsius = (RadioButton)findViewById(R.id.celsiusButton);
        farenheit = (RadioButton) findViewById(R.id.farenheitButton);


        time = (TextView)findViewById(R.id.timeTxtView);
        temperature = (EditText)findViewById(R.id.temperatureEditView);

        add = (Button)findViewById(R.id.saveButton);
        daySpinner = (Spinner)findViewById(R.id.spinnerEditMode);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // time.setText("");
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });
        Context context = getApplicationContext();
        helper = new Database(context);
        chatDatabase = helper.getWritableDatabase();
        cv = new ContentValues();

        builder = new AlertDialog.Builder(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(celsius.isChecked()){
                    tempType = "\u2103";

                }
                if(farenheit.isChecked()){
                    tempType = "\u2109";

                }
                spinnerDefault = daySpinner.getSelectedItem().toString();
                if(spinnerDefault.equals("Select a day")){
                    builder.setTitle("Must make a selection");
                    builder.setMessage("You must select a day.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //close this dialog and return to activity to make the selection to add the alarm
                        }
                    });

                    Dialog customDialog = builder.create();
                    customDialog.show();
                }else {
                    bar.setVisibility(View.VISIBLE);
                    bar.setProgress(0);
                    mday = daySpinner.getSelectedItem().toString();
                    mtime = time.getText().toString();
                    mtemp = temperature.getText().toString();
                    AddQuery addQuery = new AddQuery();
                    addQuery.execute(mday, mtime, mtemp, tempType);
                }
            }
        });
    }
         class AddQuery extends AsyncTask<String, Integer, String> {
             Database helper = new Database(getApplicationContext());

             @Override
             protected String doInBackground(String... params) {
                 Log.i("AddActivity", "In doInBackground()");
                 publishProgress(15);
                 helper.onOpen(chatDatabase);
                 publishProgress(30);
                 list.add(params[0]);
                 publishProgress(45);
                 list.add(params[1]);
                 publishProgress(60);
                 list.add(params[2]);
                 publishProgress(85);
                 list.add(params[3]);

                 cv.put("day", params[0]);
                 cv.put("time", params[1]);
                 cv.put("temperature", params[2]);
                 cv.put("type", params[3]);
                 chatDatabase.insert(Database.name, null, cv);
                 publishProgress(100);
                 return null;
             }
             @Override
             protected void onProgressUpdate(Integer... value) {
                 Log.i("AddActivity", "In onProgressUpdate");
                 bar.setProgress(value[0]);
             }
             @Override
             public void onPostExecute(String result) {
                 Log.i("AddActivity", "In onPostExecute()");
                 bar.setVisibility(View.INVISIBLE);
                 Toast toast = Toast.makeText(AddActivity.this, "You have successfully saved your alert", Toast.LENGTH_SHORT);
                 toast.show();
                 finish();
             }
         }

}
