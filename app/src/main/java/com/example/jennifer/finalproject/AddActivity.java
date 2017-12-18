 package com.example.jennifer.finalproject;

 import android.app.Activity;
 import android.content.ContentValues;
 import android.content.Context;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
 import android.os.AsyncTask;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ProgressBar;
 import android.widget.Toast;
 import android.support.design.widget.Snackbar;

 import java.util.ArrayList;

 public class AddActivity extends Activity {
     ProgressBar bar;
     EditText day;
     EditText time;
     EditText temperature;
     Button save;
     Button saveAs;

     ArrayList<String> list = new ArrayList<String>();
     ArrayAdapter adapter;
     ChatAdapter messageAdapter;
     private static SQLiteDatabase chatDatabase;
     Cursor cursor;
     DatabaseHelper helper;

     View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Log.i("AddActivity", "In onCreate()");

        view = (View)findViewById(R.layout.activity_add);
        bar = (ProgressBar)findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);

        day = (EditText)findViewById(R.id.dayOfWeek);
        time = (EditText)findViewById(R.id.time);
        temperature = (EditText)findViewById(R.id.temperature);

        save = (Button)findViewById(R.id.saveButton);
        saveAs = (Button)findViewById(R.id.saveAsButton);

        messageAdapter = new ChatAdapter(this);
        Context context = getApplicationContext();
        helper = new DatabaseHelper(context);
        chatDatabase = helper.getWritableDatabase();
        final ContentValues cv = new ContentValues();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes in the edit activity
                Snackbar snackbar = Snackbar.make(view, "Your message was succesfully saved.", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
        saveAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mday = day.getText().toString();
                String mtime = time.getText().toString();
                String mtemp = temperature.getText().toString();
                list.add(mday);
                list.add(mtime);
                list.add(mtemp);

                Log.i("List", String.valueOf(list));
                messageAdapter.notifyDataSetChanged();
                cv.put("day", mday);
                cv.put("time", mtime);
                cv.put("temperature", mtemp);
                chatDatabase.insert(DatabaseHelper.name, null, cv);

                Toast toast = Toast.makeText(AddActivity.this, "You have successfully saved your alert", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

    }
     class ChatAdapter extends ArrayAdapter<String> {
         public ChatAdapter(Context ctx) {

             super(ctx, 0);
         }

         public long getItemID(int position) {
             return position;
         }

         public int getCount() {
             return list.size();
         }

         public String getItem(int position) {
             return list.get(position);
         }

         public View getView(int position, View convertView, ViewGroup parent) {
             LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
             View result = null ;
             if(position%2 == 0) {
                 result = inflater.inflate(R.layout.chat_row_incoming, null);
             }else {
                 result = inflater.inflate(R.layout.chat_row_outgoing, null);
             }
             TextView message = (TextView)result.findViewById(R.id.messageText);
             message.setText(getItem(position)  ); // get the string at position
             return result;
             return null;
         }
     }

         class AddQuery extends AsyncTask<Object, Object, Cursor> {
             DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
             SQLiteDatabase db;
             String day;
             String time;
             String temp;

             @Override
             protected Cursor doInBackground(Object... params) {
                 Log.i("AddActivity", "In doInBackground()");
                 helper.onOpen(db);
                 return null;
             }

             @Override
             protected void onProgressUpdate(Object... value) {

                 bar.setVisibility(View.VISIBLE);
                 //bar.setProgress(value[0]);

                 Log.i("AddActivity", "In onProgressUpdate");
             }

             @Override
             public void onPostExecute(Cursor result) {
                 Log.i("AddActivity", "In onPostExecute()");
                 //add elements to database
             }
         }

}
