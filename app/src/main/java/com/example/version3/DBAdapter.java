package com.example.version3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    //primary key
    static final String KEY_ROWID = "_id";
    //date column
    static final String KEY_DATE = "date";
    //memo column
    static final String KEY_MEMO = "memo";
    //time column
    static final String KEY_TIME = "time";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "MyDB";
    //Table name
    static final String DATABASE_TABLE = "memos";
    static final int DATABASE_VERSION = 1;
    //create table sql statement
    static final String DATABASE_CREATE =
            "create table memos (_id integer primary key autoincrement, "
            + "date text not null, memo text not null, time text not null);";

    final Context context;
    DataBaseHelper DBHelper;
    SQLiteDatabase db;
   //DBAdapter Constructor
    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DataBaseHelper(context);
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---insert a memo into the database---
    public long insertMemo(String date, String memo, String time) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_MEMO, memo);
        initialValues.put(KEY_TIME, time);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---retrieves all the memos---
    public Cursor getAllMemos() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_DATE,
                KEY_MEMO, KEY_TIME}, null, null, null, null, null);
    }
    //---retrieves a particular memo---


    public Cursor getMemo(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{KEY_ROWID, KEY_DATE, KEY_MEMO, KEY_TIME},
                KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    //---updates a memo---

    public boolean updateMemo(long rowId, String memo) {
        ContentValues args = new ContentValues();
      //  args.put(KEY_DATE, date);
        args.put(KEY_MEMO, memo);
      //  args.put(KEY_TIME,time);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    //deletes memo at rowID
    public boolean deleteMemo(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
   //Sets up database and updates it
    private class DataBaseHelper extends SQLiteOpenHelper {
        DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS memos");
            onCreate(db);
        }






    }

}

