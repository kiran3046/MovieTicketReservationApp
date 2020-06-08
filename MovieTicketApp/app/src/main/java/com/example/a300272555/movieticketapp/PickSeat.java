package com.example.a300272555.movieticketapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
// This activity is called when user selects a movie and proceed to select seats
public class PickSeat extends AppCompatActivity {
    Boolean[] btnSelected= new Boolean[40]; // array to store selected or not selected status of each seat
    int noOfSelectedSeats;

    SQLiteDatabase wdb;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_seat);
         Bundle bundle = getIntent().getExtras();
        final String movieTitle = bundle.getString("movieTitle");
        final int movieId = bundle.getInt("movieId");
        final String movieStartTime = bundle.getString("movieStartTime");
        final int infants = bundle.getInt("infants");
        final int adults = bundle.getInt("adults");
        final int seniors = bundle.getInt("seniors");
        final String movieDate = bundle.getString("movieDate");
        final int totalTickets=infants+adults+seniors;
        noOfSelectedSeats=0;

        Log.d("movieDate", movieDate);
        Log.d("movieTitle", movieTitle);
        Log.d("movieId", Integer.toString(movieId));
        Log.d("movieTitle", movieTitle);
        Log.d("movieStartTime", movieStartTime);

        // get ids of booked seat buttons from database
        wdb = databaseMovieDetailsClass.getWritable(this);
        // array to store ids of all seat buttons
        Button[] seatButtons = new Button[40];
        final ArrayList<Integer> selectedButtons=new ArrayList<Integer>();

        // check which seats are booked and show  them as booked to user  for a particular movie ,date and time
        List<String> bookedSeats = checkBookedSeats(movieId, movieTitle, movieDate, movieStartTime);

        // if there are some seats already booked
        if (bookedSeats != null) {
            for (int i = 0; i < bookedSeats.size(); i++) {

                String buttonID = "btn"+bookedSeats.get(i);
                Log.d("bookedseatno",buttonID+" ----  "+bookedSeats.get(i));
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                Button btn = (Button) (findViewById(resID));
                btn.setBackground(getResources().getDrawable(R.drawable.booked));
                btn.setEnabled(false);
            }

        }
        // set btnselected flag as false for all buttons
        for(int i=0;i<40;i++){
            btnSelected[i]=false;
        }


        //
    for (int i = 1; i < seatButtons.length; i++) {

        int m = i;
        String buttonID = "btn" + m;

        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        Log.d("buttonid", "idd---" + buttonID);
        Log.d("resId", "idd---" + resID);

        seatButtons[i] = ((Button) findViewById(resID));
        final int finalId = resID;
        final int buttonNum = i;

        // set listeners to all buttons
        if (seatButtons[i].isEnabled() && noOfSelectedSeats<=totalTickets) {

            seatButtons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int btnId = finalId;
                    Button btn = (Button) findViewById(btnId);
                    if (!btnSelected[buttonNum]) {
                        btn.setBackground(getResources().getDrawable(R.drawable.selected));
                        btnSelected[buttonNum] = true;
                        noOfSelectedSeats=noOfSelectedSeats+1;
                       selectedButtons.add(buttonNum);

                    } else {
                        btn.setBackground(getResources().getDrawable(R.drawable.available));
                        btnSelected[buttonNum] = false;
                        noOfSelectedSeats=noOfSelectedSeats-1;

                        try {
                            selectedButtons.remove(buttonNum);
                        }
                        catch(Exception ex)
                        {
                            System.out.println(ex);
                        }
                    }

                }


            });

        }

    }

        // go to Receipt page i.e. Payment Activity after selecting seats and confirming seats

        Button btnProceed = (Button) findViewById(R.id.btnConfirmSeats);
        btnProceed.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                if (noOfSelectedSeats != totalTickets) {
                    Toast.makeText(PickSeat.this, "Please select no of seats as selected previously", Toast.LENGTH_LONG).show();
                }
              else  {

                    for (int i = 0; i < selectedButtons.size(); i++) {
                       System.out.println("Seat no "+selectedButtons.get(i));
                    }



                    Intent myintent = new Intent(PickSeat.this, PaymentActivity.class);
                    myintent.putExtra("selectedSeatsList",selectedButtons);
                    myintent.putExtra("movieTitle",movieTitle);
                    myintent.putExtra("movieId",movieId);
                    myintent.putExtra("movieStartTime",movieStartTime);
                    myintent.putExtra("infants",infants);
                    myintent.putExtra("adults",adults);
                    myintent.putExtra("seniors",seniors);
                    myintent.putExtra("movieDate",movieDate);

                    startActivity(myintent);
                }
            }

        });

}







    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public List<String> checkBookedSeats(int movieId , String movieTitle, String movieDate, String movieStartTime){

        List<String> bookedSeatsList = getAllBookedSeats(movieId,movieTitle,movieDate,movieStartTime);


        return bookedSeatsList;


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public List<String> getAllBookedSeats(int movieId , String movieTitle, String movieDate, String movieStartTime){
        List<String> bookedSeatsList = new ArrayList<String>();
        String movieNum=Integer.toString(movieId);
        Cursor cursor = wdb.rawQuery("SELECT BookedSeatNo FROM MovieDetails where MovieId = ?and MovieName = ?and MovieDate = ?and StartTime = ?",new String[] {movieNum,movieTitle,movieDate,movieStartTime}, null);
        String []bookedSeats=new String[cursor.getCount()];
        int size=bookedSeats.length;
        // looping through all rows and adding to list
        int i=0;

        if (cursor.moveToFirst()) {

            do {


                bookedSeats[i]=cursor.getString(0);
                i++;
            } while (cursor.moveToNext() && i <bookedSeats.length);
        }

        for(int j=0;j<size;j++)
            bookedSeatsList.add(bookedSeats[j]);


        Set<String> primesWithoutDuplicates = new LinkedHashSet<String>(bookedSeatsList);

        bookedSeatsList.clear();
        bookedSeatsList.addAll(primesWithoutDuplicates);



        // closing connection
        cursor.close();


        return bookedSeatsList;
    }





}
