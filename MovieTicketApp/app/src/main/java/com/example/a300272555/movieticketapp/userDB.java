package com.example.a300272555.movieticketapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by 300272555 on 11/21/2017.
 */

public class userDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "userDB.db";

    public userDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(com.example.a300272555.movieticketapp.databaseQueries.SQL_CREATE_USER);
        db.execSQL(com.example.a300272555.movieticketapp.databaseQueries.SQL_CREATE_MOVIE);



        Log.d(TAG,"onCreate******************************************");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       // db.execSQL(com.example.a300272555.movieticketapp.databaseQueries.SQL_DELETE_CARNAMES );
        db.execSQL(com.example.a300272555.movieticketapp.databaseQueries.SQL_DELETE_USERS); // delete
        db.execSQL(com.example.a300272555.movieticketapp.databaseQueries.SQL_DELETE_Movie_Details);
        Log.d("userDB.db","OnUpgrade");


        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("userDB.db","onDowngrade");
        onUpgrade(db, oldVersion, newVersion);
    }
}
