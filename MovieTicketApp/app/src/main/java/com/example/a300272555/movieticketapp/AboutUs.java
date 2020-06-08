package com.example.a300272555.movieticketapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        // button to go back to homepage
        Button btnBack = (Button) findViewById(R.id.btnBack);


        btnBack.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {

                Intent myintent=new Intent(AboutUs.this,HomePage.class);


                startActivity(myintent);

            }

        });

    }
}
