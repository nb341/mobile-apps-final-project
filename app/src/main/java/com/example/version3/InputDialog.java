package com.example.version3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputDialog extends AppCompatDialogFragment {
EditText input;

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Gets builder to set up custom dialog for Input
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Gets Layout for custom dialog for input
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.input_dialog, null);
             //set up view for text box in input dialog xml
             input = (EditText) view.findViewById(R.id.inputDialog);
           //building custom dialog for input
        builder.setView(view)
                .setTitle("New Note")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 //Creates DBAdapter object to use within dialog box
                DBAdapter db = new DBAdapter(getActivity());
                //opens database
                db.open();
                //Gets date and time
                         String currentTime = Calendar.getInstance().getTime().toString();
                          String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                          //Gets text entered from user from the textbox in the input dialog xml
                          String memo = input.getText().toString();
                              //Try to insert memo and current time and date
                              if(db.insertMemo(date,memo, currentTime)>=0){
                                      //if insert successful returns toast message confirming insert
                                  Toast.makeText(getContext(), "Insert Successful ",Toast.LENGTH_LONG).show();
                              }
                              else{
                                    //returns insert fail if insertion into database fail
                                  Toast.makeText(getContext(), "Insert fail ",Toast.LENGTH_LONG).show();
                              }
                //closes database
                db.close();
                //Prints updated results to list view
                ((MainActivity) getActivity()).Display();
            }
        });


        return builder.create();
    }

}

