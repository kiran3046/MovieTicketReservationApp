package com.example.a300272555.movieticketapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyData extends ArrayAdapter<ItemData> {

    int groupid;
    Activity context;
    ArrayList<ItemData> list;
    LayoutInflater inflater;


    public MyData(Activity c, int groupid, ArrayList<ItemData> list) {
        super(c,groupid,list);
        this.list=list;
        inflater= LayoutInflater.from(c);  //(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView=inflater.inflate(groupid,parent,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.imageView);
        imageView.setImageResource(list.get(position).getImageId());
        TextView textView=(TextView)itemView.findViewById(R.id.textView);
        textView.setText(list.get(position).getText());
        return itemView;

    }

    //Gets a View that displays in the drop down popup the data at the specified position in the data set.
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        Log.d("MyData", position + "");
        return getView(position,convertView,parent);

    }
}
