package com.example.a300272555.movieticketapp;
// Activity is used by Guest to select and Book a movie
import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.example.a300272555.movieticketapp.R.id.listPictures;
import static com.example.a300272555.movieticketapp.R.id.textViewDate1;
import static com.example.a300272555.movieticketapp.R.id.textViewTime1;

public class PickMovieGuest extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int[] pictures = {R.drawable.p1, R.drawable.blade_runner, R.drawable.beauty_and_beast, R.drawable.fast_and_furious, R.drawable.wonderwoman, R.drawable.justice_league};
    private String[] names = {"Phillauri", "Blade Runner", "Beauty and the Beast", "Fast and Furious", "WonderWoman", "Justice League"};
    String selectedMovie;
    int movieNumber;
    int infants;
    int adults;
    int seniors;
    static int id;
    SQLiteDatabase wdb;
    Spinner spinner;  // movie times spinner
    NumberPicker numPicker1=null;  // number pickers to select no of seats
    NumberPicker numPicker2=null;
    NumberPicker numPicker3=null;
    TextView t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_movie_guest);
        t1=(TextView)findViewById(R.id.textViewTime1);
        final TextView tvDate = (TextView) findViewById(R.id.textViewDate1);
        numPicker1=(NumberPicker) findViewById(R.id.numberPicker1);
        numPicker2=(NumberPicker) findViewById(R.id.numberPicker2);
        numPicker3=(NumberPicker) findViewById(R.id.numberPicker3);


        Button b = (Button) findViewById(R.id.btnPickDate);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();

            }

        });



        numPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal)
            {
               infants=newVal;


            }
        });
        numPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal)
            {
                 adults=newVal;
            }
        });
        numPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal)
            {
                 seniors=newVal;
            }
        });

        numPicker1.setMaxValue(38);
        numPicker1.setMinValue(0);
        numPicker1.setWrapSelectorWheel(false);
        numPicker2.setMaxValue(38);
        numPicker2.setMinValue(0);
        numPicker2.setWrapSelectorWheel(false);
        numPicker3.setMaxValue(38);
        numPicker3.setMinValue(0);
        numPicker3.setWrapSelectorWheel(false);


        // create a database connection using databaseValidateUserClass
        wdb = databaseValidateUserClass.getWritable(this);

        spinner = (Spinner) findViewById(R.id.spinnerTime);


        ArrayList<ItemData> list = new ArrayList<>();
        for (int i = 0; i < pictures.length; i++) {
            list.add(new ItemData(names[i], pictures[i]));
        }


        // custom_items layout adapter
        MyData adapter = new MyData(this, R.layout.custom_items, list);

        //set adapter in listview
        final ListView lv = (ListView) findViewById(listPictures);
        lv.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lv.setFocusedByDefault(false);
        }

        // On selecting a picture
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                selectedMovie = names[i];
                if (selectedMovie == null) {
                    Toast.makeText(PickMovieGuest.this, "Please select a movie to proceed ", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(PickMovieGuest.this, "You selected " + selectedMovie, Toast.LENGTH_LONG).show();
                    movieNumber = i;
                    id=i;

                    showTimings(i);

                }

            }
        });
        Button btnProceed = (Button) findViewById(R.id.btnProceedToPay);
        btnProceed.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                String movieDate=tvDate.getText().toString();
            // if user do not select a movie
                if (selectedMovie == null) {
                    Toast.makeText(PickMovieGuest.this, "Please select a movie to proceed ", Toast.LENGTH_LONG).show();
                } // if no of seats is not selected
                else if (infants == 0 && adults == 0 && seniors == 0) {
                    Toast.makeText(PickMovieGuest.this, "Please select no of seats to proceed ", Toast.LENGTH_LONG).show();

                }else   if (movieDate == null) {
                    Toast.makeText(PickMovieGuest.this, "Please select a Date to proceed ", Toast.LENGTH_LONG).show();
                }
                else{
                 Intent myintent = new Intent(PickMovieGuest.this, PickSeat.class);
                     myintent.putExtra("movieTitle",selectedMovie);
                    myintent.putExtra("movieId",movieNumber);
                    myintent.putExtra("movieStartTime",t1.getText());
                    myintent.putExtra("infants",infants);
                    myintent.putExtra("adults",adults);
                    myintent.putExtra("seniors",seniors);
                    myintent.putExtra("movieDate",movieDate);

                    startActivity(myintent);
                }
            }

        });

}


    private void showDate() {

        DialogFragment newFragment = new MyDatePicker();


        // sending some data to the Fragment via Bundle
        Bundle args = new Bundle();
        args.putInt("dTheme", 4);
        args.putInt("destination",R.id.textViewDate1);

        newFragment.setArguments(args);

        newFragment.show(getFragmentManager(),"Date Picker");


    }

// method used to get all timings from database , store in an array and then put that array into time spinner
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showTimings(int i) {
        List<String> startTimings = getAllTimings(i);
        String[] timings = new String[startTimings.size()];


        for (int j = 0; j < startTimings.size(); j++) {
            timings[j] = startTimings.get(j);

        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, timings);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

    }


// select movie Start times from database
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public List<String> getAllTimings(int movieNum){
        List<String> startTimes = new ArrayList<String>();
        String movienum=Integer.toString(movieNum);
        Cursor cursor = wdb.rawQuery("SELECT StartTime FROM MovieDetails where MovieId=?",new String[] {movienum}, null);
        String []startTimeMovie=new String[cursor.getCount()];
        int size=startTimeMovie.length;
        // looping through all rows and adding to list
        int i=0;

        if (cursor.moveToFirst()) {

            do {


                startTimeMovie[i]=cursor.getString(0);
                i++;
            } while (cursor.moveToNext() && i <startTimeMovie.length);
        }

        for(int j=0;j<size;j++)
            startTimes.add(startTimeMovie[j]);


        Set<String> primesWithoutDuplicates = new LinkedHashSet<String>(startTimes);

        startTimes.clear();
        startTimes.addAll(primesWithoutDuplicates);



        // closing connection
        cursor.close();


        // returning lables
        return startTimes;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a time spinner item
        String time = parent.getItemAtPosition(position).toString();

        t1.setText(time);
    }
    // if movie time is not selected
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(arg0.getContext(), "Please Select a time to proceed " , Toast.LENGTH_LONG).show();
    }






}
