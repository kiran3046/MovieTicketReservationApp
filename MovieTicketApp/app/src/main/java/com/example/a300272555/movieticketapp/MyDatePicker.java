package com.example.a300272555.movieticketapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MyDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Log.d("MyDatePicker","onCreateDialog");
        // Create a new instance of DatePickerDialog and return it
        /* DatePickerDialog (Context context,
                int themeResId,
                DatePickerDialog.OnDateSetListener listener,
                int year,
                int monthOfYear,
                int dayOfMonth)
            */
        int theme = 0;
        int t = getArguments().getInt("dTheme");
        switch (t) {
            case 0 : theme = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT; break;
            case 1 : theme = AlertDialog.THEME_DEVICE_DEFAULT_DARK; break;
            case 2 : theme = AlertDialog.THEME_HOLO_LIGHT; break;
            case 3 : theme = AlertDialog.THEME_HOLO_DARK; break;
            case 4 : theme = AlertDialog.THEME_TRADITIONAL; break;
            default: theme = R.style.AppTheme; break;

        }
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), theme, this, year, month, day);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return dpd;
    }

    // onDateSet is automatically called when user clicks ok
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        int output_id = getArguments().getInt("destination");

        // assume TextView
        // reference: https://developer.android.com/reference/android/app/Fragment.html#getActivity()
        // Activity getActivity () , Return the Activity this fragment is currently associated with.
        TextView tv = (TextView) getActivity().findViewById(output_id);
        String s = year + "/" + (month+1) + "/" + dayOfMonth ;
        tv.setText(s);

        Log.d("MyDatePicker","onDateSet,  " + s); // just to trace when it is called
    }
}
