package com.example.a300272555.movieticketapp;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;


import static android.content.ContentValues.TAG;
import static com.example.a300272555.movieticketapp.R.id.listPictures;
import static com.example.a300272555.movieticketapp.R.id.showStart;
import static com.example.a300272555.movieticketapp.R.layout.activity_movie_scheduler;

public class MovieScheduler extends AppCompatActivity implements View.OnClickListener {
// Admin Module Activity used by admin to schedule movies

    private int[] pictures = {R.drawable.p1, R.drawable.blade_runner, R.drawable.beauty_and_beast, R.drawable.fast_and_furious, R.drawable.wonderwoman, R.drawable.justice_league};
    private String[] names = {"Phillauri", "Blade Runner", "Beauty and the Beast", "Fast and Furious", "WonderWoman", "Justice League"};
    private userDB db;
    int flag=1;
    String selectedMovie;
    int movieNumber;

    SQLiteDatabase wdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        wdb = databaseMovieDetailsClass.getWritable(this);
         super.onCreate(savedInstanceState);
        setContentView(activity_movie_scheduler);


        // Button to select date
        Button b = (Button) findViewById(R.id.btnDatePicker);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });


        //store all pictures and there names
        ArrayList<ItemData> list = new ArrayList<>();
        for (int i = 0; i < pictures.length; i++) {
            list.add(new ItemData(names[i], pictures[i]));
        }

        // custom_items layout adapter
        MyData adapter = new MyData(this, R.layout.custom_items, list);

        //set adapter in listview
        final  ListView lv = (ListView) findViewById(listPictures);
        lv.setAdapter(adapter);

        // On selecting a picture
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                selectedMovie = names[i];
                movieNumber = i;

            }
        });

// Buttons capture StartTime , RunTime , CleaningTime , Theatre Close Time selected by ADMIN

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        Button btnRun = (Button) findViewById(R.id.btnRun);
        btnRun.setOnClickListener(this);
        Button btnClean = (Button) findViewById(R.id.btnClean);
        btnClean.setOnClickListener(this);
        Button btnCloseTime = (Button) findViewById(R.id.btnCloseTime);
        btnCloseTime.setOnClickListener(this);
        Button btnSchedule=(Button) findViewById(R.id.btnSchedule); // Schedule movie
        btnSchedule.setOnClickListener(this);


    }

    // method used to show date dialog picker
    private void showDate() {

        DialogFragment newFragment = new MyDatePicker();
        Bundle args = new Bundle();
        args.putInt("dTheme", 4);
        args.putInt("destination",R.id.txtShowDate);

         newFragment.setArguments(args);
         newFragment.show(getFragmentManager(),"Date Picker");

    }

    @Override
    public void onClick(View v) {
        db = new userDB(this);
        String startTime;
        String runTime;
        String cleaningTime;
        String closeTime;


        TextView tvStart = (TextView) findViewById(R.id.showStart);
        TextView tvRun = (TextView) findViewById(R.id.showRun);
        TextView tvClean = (TextView) findViewById(R.id.showClean);
        TextView tvClose = (TextView) findViewById(R.id.showClose);
        TextView tvDate=(TextView) findViewById(R.id.txtShowDate);

        final Calendar calendar = Calendar.getInstance();
        // Get the current hour and minute
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        switch (v.getId()) {


            case R.id.btnStart:

                TimePickerDialog tpd1 = new TimePickerDialog(MovieScheduler.this,
                        android.R.style.Theme_Material_Light, timePickerL, hour, minute, false);
                flag=1;
                tpd1.show();
                break;

            case R.id.btnRun:
                TimePickerDialog tpd2 = new TimePickerDialog(MovieScheduler.this,
                        android.R.style.Theme_Material_Light, timePickerL, hour, minute, false);
                flag=2;
                tpd2.show();
                break;

            case R.id.btnClean:
                TimePickerDialog tpd3 = new TimePickerDialog(MovieScheduler.this,
                        android.R.style.Theme_Material_Light, timePickerL, hour, minute, false);
                flag=3;
                tpd3.show();
                break;
            case R.id.btnCloseTime:
                TimePickerDialog tpd4 = new TimePickerDialog(MovieScheduler.this,
                        android.R.style.Theme_Material_Light, timePickerL, hour, minute, false);
                flag=4;
                tpd4.show();
                break;

            case R.id.btnSchedule:
                long newRowId=0;
                if(selectedMovie == null) {
                    Toast.makeText(MovieScheduler.this, "Please select a movie to proceed ", Toast.LENGTH_LONG).show();
                    break;
                }else if(tvStart.getText().equals("") || tvRun.getText().equals("") || tvClean.getText().equals("")
                            || tvClose.getText().equals("")){
                    Toast.makeText(MovieScheduler.this, "Please enter  all input timings to schedule ", Toast.LENGTH_LONG).show();
                    break;
                }else if(tvDate.getText().equals("")){
                    Toast.makeText(MovieScheduler.this, "Please select a date to proceed ", Toast.LENGTH_LONG).show();
                    break;
                }
                else
                    Toast.makeText( MovieScheduler.this, "You selected " + selectedMovie, Toast.LENGTH_LONG).show();
                startTime=tvStart.getText().toString()+":00";
                runTime =  tvRun.getText().toString()+":00";
                cleaningTime =  tvClean.getText().toString()+":00";
                closeTime =  tvClose.getText().toString()+":00";
                Log.d("btnSchedule",startTime);
                try {

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                  // convert string times to date format to calculate startTimes
                    Date start = timeFormat.parse(startTime);
                    Date run = timeFormat.parse(runTime);
                    Date clean= timeFormat.parse(cleaningTime);
                    Date close=timeFormat.parse(closeTime);

                    // Arraylist to store [movienumber][startTime[i]] i is 1st showTime ,2nd showTime etc
                    ArrayList[][] list = new ArrayList[10][10];
                    list[movieNumber][0]=new ArrayList();
                    list[movieNumber][0].add(timeFormat.format(new Date(start.getTime())));
                    long previousStartTime=start.getTime();
                    int i=1;
                    // calculate startTimes till the time theatre closes
                    while(previousStartTime<close.getTime())
                    {
                        long movieStartTime = previousStartTime + run.getTime() + clean.getTime();
                        list[movieNumber][i]=new ArrayList();
                        list[movieNumber][i].add(timeFormat.format(new Date(movieStartTime)));
                        previousStartTime=movieStartTime;
                        i++;
                    }
                 /*   for(int j=0;j<i;j++) {
                        Log.d("movielist", list[movieNumber][j].toString());
                    }  */
                    try {

                        // capture all startimes in values and store into database , seat no 1 is booked by default
                        // for first entry
                        ContentValues values = new ContentValues();
                        for (int j = 0; j < i; j++) {
                            values.put(MovieDetailsEntry.COLUMN_NAME_Movie_Date, tvDate.getText().toString());
                            values.put(MovieDetailsEntry.COLUMN_NAME_Movie_Number, movieNumber);
                            values.put(MovieDetailsEntry.COLUMN_NAME_Movie_Name, selectedMovie);
                            values.put(MovieDetailsEntry.COLUMN_NAME_Movie_startTime, list[movieNumber][j].toString());
                            values.put(MovieDetailsEntry.COLUMN_NAME_Movie_BookedSeatNo, 1);
                             newRowId = wdb.insert(MovieDetailsEntry.TABLE_NAME, null, values);
                            // eID.setText(newRowId + "");
                         //   Toast.makeText(MovieScheduler.this, "entry no " + newRowId, Toast.LENGTH_LONG).show();
                        }
                    } catch(SQLiteException exception) {
                        Toast.makeText(MovieScheduler.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                }catch(Exception ex){
                    Log.d("Exception :btnSchedule",ex.toString());
                    System.out.println(ex.getCause());
                }





                Intent myintent=new Intent(MovieScheduler.this,MovieScheduler2.class);
               myintent.putExtra("movieTitle",selectedMovie);
             startActivity(myintent);

            default:
                tvStart.setText("");
                tvRun.setText("");
                tvClean.setText("");
                tvClose.setText("");
                break;
        }
    }


// select times from timepicker dialog for each of the buttons pressed using a flag
    private TimePickerDialog.OnTimeSetListener timePickerL = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub

            TextView tvStart = (TextView) findViewById(R.id.showStart);
            TextView tvRun = (TextView) findViewById(R.id.showRun);
            TextView tvClean = (TextView) findViewById(R.id.showClean);
            TextView tvClose = (TextView) findViewById(R.id.showClose);
            int hour = hourOfDay;
            int minute = minutes;

            switch (flag) {


                case 1:

                    tvStart.setText(hour + ":" + minute);

                    break;

                case 2:

                    tvRun.setText(hour + ":" + minute);
                    break;

                case 3:

                    tvClean.setText(hour + ":" + minute);
                    break;

                case 4:
                    tvClose.setText(hour + ":" + minute);
                    break;

                default:

                    break;
            }


        }
    };
}