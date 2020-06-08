package com.example.a300272555.movieticketapp;

/**
 * Created by 300272555 on 11/9/2017.
 */


// For displaying Movie Title and Movie Image inside  listview
public class ItemData {
    String text;
    Integer imageId;
    public ItemData(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }

    public Integer getImageId(){
        return imageId;
    }
}
