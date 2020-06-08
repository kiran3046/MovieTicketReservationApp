package com.example.a300272555.movieticketapp;

import android.provider.BaseColumns;

/**
 * Created by 300272555 on 11/21/2017.
 */
// Class to DEFINE COLUMNS OF TABLE MovieDetails
public class MovieDetailsEntry implements BaseColumns{

    public static final String TABLE_NAME = "MovieDetails";
    public static final String COLUMN_NAME_Movie_Number = "MovieId";
    public static final String COLUMN_NAME_Movie_Name = "MovieName";
    public static final String COLUMN_NAME_Movie_startTime = "StartTime";
    public static final String COLUMN_NAME_Movie_BookedSeatNo = "BookedSeatNo";
    public static final String COLUMN_NAME_Movie_Date = "MovieDate";


}


