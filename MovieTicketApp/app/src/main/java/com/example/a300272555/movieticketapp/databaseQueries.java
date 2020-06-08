package com.example.a300272555.movieticketapp;

/**
 * Created by 300272555 on 11/21/2017.
 */


// Database queries to store User details and Movie details
    // user name and email is required to login
    // MovideDetails database has MovieId , MovieName ,MovieDate , MovieStartTime , BookedSeats
public class databaseQueries {


    public static final String SQL_CREATE_USER =
            "CREATE TABLE USER (UserName text, Email text )";

    public static final String SQL_CREATE_MOVIE =
            "CREATE TABLE MovieDetails (MovieId integer , MovieName text, StartTime text, BookedSeatNo integer, MovieDate text)";

    public static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS  USER  ";

    public static final String SQL_DELETE_Movie_Details =
            "DROP TABLE IF EXISTS MovieDetails  ";


    }
