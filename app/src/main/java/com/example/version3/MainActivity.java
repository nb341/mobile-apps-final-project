package com.example.version3;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    //Used for  edit custom dialog
    AlertDialog.Builder builder;
    //Used for custom_row list
    ListView lv;
    //stores list of objects of type memos
    ArrayList < Memo > memo;
    //Used to get custom_row and implement it in a list view
    CustomAdapter adapter;
    //Used to access SQLITE database
    DBAdapter db;
    Memo m;

    @Override protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        setViews ();
        Display ();
        registerForContextMenu (lv);

    }




    public void setViews ()
    {

        //Initializes DBAdapter to be used in the MainActivity
        db = new DBAdapter (this);
        //sets list view
        lv = (ListView) findViewById (R.id.List_View);
        //Makes list clickable
        lv.setOnItemClickListener (new AdapterView.OnItemClickListener ()
        {
            @Override
            public void onItemClick (AdapterView <
                    ? >parent, View view,
                                     int position, long id)
            {

            }


        });

        //sets up floating action button
        FloatingActionButton fb = findViewById (R.id.floatingActionButton);

        //tells the floating action button to do when clicked
        fb.setOnClickListener (new View.OnClickListener ()
        {
            @Override public void onClick (View v)
            {
                //opens Input Dialog when floating action button is clicked
                OpenDialog ();}
        });



    }

    //Create single choice option menu item when a row on the custom list view is clicked
    @Override
    public void onCreateContextMenu (ContextMenu menu, View v,
                                     ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu (menu, v, menuInfo);
        //Adds Edit and Delete as menu options
        menu.add ("Edit");
        menu.add ("Delete");

    }

    @Override public boolean onContextItemSelected (final MenuItem item)
    {
        //Gets items from custom adapter stored in the arraylist
        AdapterView.AdapterContextMenuInfo mems =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo ();
        //gets the position of the item clicked
        Memo m1 = memo.get (mems.position);
        //id stores the value of the primary key of the item that was clicked
        final long id = m1.getId ();


        //If Delete option is chosen
        if (item.getTitle () == "Delete")
        {

            //opens database to begin delete
            db.open ();
            //if database delete query returns true displays toast confirming delete
            if (db.deleteMemo (id))
                Toast.makeText (this, "Delete Successful",
                        Toast.LENGTH_LONG).show ();
            else
                //if delete fails, displays delete failed
                Toast.makeText (this, "Delete Failed", Toast.LENGTH_LONG).show ();
            //closes database
            db.close ();
            //Displays update list view
            Display ();
        }
        //if Edit option is chosen
        else if (item.getTitle () == "Edit")
        {
            //Alert dialog used to open custom dialog for to update memo
            final AlertDialog.Builder builder =
                    new AlertDialog.Builder (MainActivity.this);
            //For the custom dialog layout
            LayoutInflater inflater = getLayoutInflater ();
            //Sets the view of the custom dialog
            final View view = inflater.inflate (R.layout.edit_dialog, null);
            //Makes edit text in the edit_dialog.xml accessible in the main activity
            EditText ed = (EditText) view.findViewById (R.id.edit_dialog_text);
            //sets the edit text in the edit_dialog.xml to the memo value of the that was clicked in the list
            ed.setText (m1.getMemo ());
            //sets up custom dialog for update memo
            builder.setView (view).setTitle ("Update Memo").
                    setNegativeButton ("Cancel", new DialogInterface.OnClickListener ()
                    {
                        @Override
                        public void onClick (DialogInterface dialog,
                                             int which)
                        {

                        }
                        //sets positive button to Update and performs update when clicked
                    }).setPositiveButton ("Update",
                    new DialogInterface.
                            OnClickListener ()
                    {
                        @Override
                        public void
                        onClick (DialogInterface
                                         dialog, int which)
                        {


                            //opens database to begin update
                            db.open ();
                            //testing for null values
                            System.out.
                                    println
                                            ("THIS IS THE MEMO +>>>>>>>>>>>>>>> "
                                                    + memo);;
                            // Set up back Edit text in the edit_dialog xml to make it accessible within alert dialog
                            EditText ed =
                                    view.findViewById (R.id.
                                            edit_dialog_text);
                            //Gets updated memo from textbox
                            String memo_1 =
                                    ed.getText ().toString ();
                            //performs update and return confirmation messages
                            if (db.
                                    updateMemo (id,
                                            memo_1))
                                Toast.
                                        makeText (MainActivity.
                                                        this,
                                                "Update Successful",
                                                Toast.
                                                        LENGTH_LONG).
                                        show ();
                            else
                                //returns Update failed to screen if update fail
                                Toast.
                                        makeText (MainActivity.
                                                        this,
                                                "Update Failed",
                                                Toast.
                                                        LENGTH_LONG).
                                        show ();
                            //closes database
                            db.close ();
                            //displays updated list of memos
                            Display ();}
                    }
            );
            //to display the Update memo dialog ie the layout in the edit_dialog.xml file
            AlertDialog alertDialog = builder.create ();
            alertDialog.show ();

        }

        return true;
    }

    //Opens input dialog to enter new memos when implemented
    public void OpenDialog ()
    {
        InputDialog id = new InputDialog ();
        id.show (getSupportFragmentManager (), "Input Dialog");
    }



    //Display lists of memos from database
    public void Display ()
    {
        //opens database to being display
        db.open ();
        //sets cursor value from the DBAdapter class when db.getAllMemos invoked
        Cursor c = db.getAllMemos ();

        //moves cursor to first position
        if (c.moveToFirst ())
        {

            //creates and initialize empty arraylist of type memo
            memo = new ArrayList < Memo > ();
            //clears old values in the arraylist to prevent previous garbage values
            memo.clear ();

            //Stores values from database in Memo object  and then adds it to arraylist
            do
            {
                m =
                        new Memo (c.getLong (0), c.getString (1), c.getString (2),
                                c.getString (3));
                memo.add (m);

            }
            while (c.moveToNext ());

            //sets up custom adapter to display the values in the arraylist
            adapter = new CustomAdapter (this, memo);

            //gets list view from activity_main.xml
            lv = (ListView) findViewById (R.id.List_View);

            //formats listview to custom list view from custom adapter
            lv.setAdapter (adapter);
        }
        //closes database
        db.close ();
    }


}
