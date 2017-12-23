package com.example.jennifer.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * The following code is cited from
 * https://android--examples.blogspot.ca/2015/04/timepickerdialog-in-android.html
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    TextView timeView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK
                ,this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        timeView = new TextView(getActivity());
        timeView.setText("Select a time");
        timeView.setBackgroundColor(Color.parseColor("#EEE8AA"));
        timeView.setPadding(5, 3, 5, 3);
        timeView.setGravity(Gravity.CENTER_HORIZONTAL);
        timePickerDialog.setCustomTitle(timeView);

        return timePickerDialog;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        timeView = (TextView) getActivity().findViewById(R.id.timeTxtView);
        timeView.setText("");
        String aMpM = "AM";
        if(hourOfDay >11){
            aMpM = "PM";
        }
        int currentHour = hourOfDay;

        timeView.setText(" " + timeView.getText()+ String.valueOf(currentHour)
                + " : " + String.valueOf(minute) + " " + aMpM );

    }
}
