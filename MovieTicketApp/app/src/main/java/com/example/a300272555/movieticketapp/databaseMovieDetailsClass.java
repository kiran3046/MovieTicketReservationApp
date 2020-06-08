package com.example.a300272555.movieticketapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 300272555 on 11/24/2017.
 */


//  class to set conncetion with database named USERDB moviedetails
public class databaseMovieDetailsClass {
    private static SQLiteDatabase wdb = null;
    private static userDB db = null;
    public static SQLiteDatabase getWritable(Context context) {
        if (db == null)
            db = new userDB(context);

        if (wdb == null)
            wdb = db.getWritableDatabase();

        return wdb;
    }






}
