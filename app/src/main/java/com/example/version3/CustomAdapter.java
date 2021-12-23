package com.example.version3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter<Memo> {

    public CustomAdapter(@NonNull Context context, ArrayList<Memo> memo) {
        super(context, R.layout.custom_row,memo);
    }

        @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Inflates layout, sets it up to look like the custom row
        LayoutInflater inflate = LayoutInflater.from(getContext());
        //sets up view to access layout within customer adapter class

        View custom_row = inflate.inflate(R.layout.custom_row, parent, false);
        //gets the position of the row in the listview
        Memo memo = getItem(position);
        //Sets up text views in custom row layout xml
        TextView timeText = (TextView) custom_row.findViewById(R.id.time_view);
        TextView memoText =  (TextView) custom_row.findViewById(R.id.memo_view);

       //sets textview to time in database at particular row
       timeText.setText(memo.getTime());
       //sets textview to memo in database at a particular row
       memoText.setText(memo.getMemo());
       //returns the view to be accessed where necessary
        return custom_row;

    }


}
