package com.example.a300272555.movieticketapp;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import static com.example.a300272555.movieticketapp.R.id.listPictures;

public class MovieScheduler2 extends AppCompatActivity implements View.OnClickListener{

// second activity called when  movie is scheduled successfully by admin
    String movieDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_scheduler2);
        Bundle bundle=getIntent().getExtras();
        String movieTitle=bundle.getString("movieTitle");
        movieDate=bundle.getString("movieDate");
        TextView tv= (TextView) findViewById(R.id.textViewMovieScheduled);
        String s=tv.getText().toString();
        s=movieTitle+" "+s;
        tv.setText(s);
        Button btnScheduleAnother=(Button) findViewById(R.id.btnScheduleAnother);
        btnScheduleAnother.setOnClickListener(this);
        Button btnSignout =(Button) findViewById(R.id.btnSignOut);
        btnSignout.setOnClickListener(this);


    }


    // Signout button or Schedule another movie button listeners
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btnScheduleAnother:
                Intent myintent=new Intent(MovieScheduler2.this,MovieScheduler.class);

                startActivity(myintent);

                break;

            case R.id.btnSignOut:

                Intent myintent2=new Intent(MovieScheduler2.this,HomePage.class);
                myintent2.putExtra("movieDate",movieDate);
                startActivity(myintent2);
                break;

            default:
                break;
        }
    }
}
