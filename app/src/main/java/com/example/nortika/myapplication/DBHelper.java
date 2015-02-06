package com.example.nortika.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nortika on 2015-02-06.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mom.db";
    private static final int DB_VER = 1;

    public DBHelper(Context context) {
        super(context,DB_NAME,null,DB_VER);
    }


    public void onCreate(SQLiteDatabase db) {

        String sql = "create table mom_task(" + "_id integer primary key autoincrement," + "task text," + "reg_date text);";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

        String sql_dropTable = "DROP TABLE IF EXISTS " + "mom_task;";
        db.execSQL(sql_dropTable);
    }
}

