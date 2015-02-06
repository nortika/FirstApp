package com.example.nortika.myapplication;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nortika on 2015-02-06.
 */
public class DBAdapter {

    static final String task_dbName = "mom_task";

    //데이터 베이스를 이용하는 컨텍스트
    private Context context;
    //데이터 연동객체
    private SQLiteDatabase db;
    Date rightNow;


    public DBAdapter(Context context) {
        this.context = context;
        this.open();

        //test code
        //this.insert_dialog("1 day class","start english study 1day");
        //Log.d("kingreturn", "db insert done");
    }

    public void open() throws SQLException {
        try {

            db = (new DBHelper(context).getWritableDatabase());
        } catch(SQLiteException e) {
            db = (new DBHelper(context).getReadableDatabase());
        }
    }

    public void insert_task(String task) {
        try {
            ContentValues values = new ContentValues();
            values.put("task",task);

            rightNow = new Date();
            SimpleDateFormat CurDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
            String strCurDate = CurDateFormat.format(rightNow);

            values.put("reg_date",strCurDate);

            long result = db.insert(task_dbName,null,values);
        } catch(Exception e) {
            Log.d("DBAdapter.insert_task", e.getMessage());
        }
    }

    public Cursor select_all_task() {
        Cursor c = db.query("mom_task", //table name
                new String[] {"_id","task","reg_date"}, //colum 명세
                null, //where
                null, //where 절에 전달할 데이터
                null, //group by
                null, //having
                "_id" + " DESC" //order by
        );
        return c;
    }

    /*public ArrayList<Task> get_all_task() {
        ArrayList<Task> arrTask = new ArrayList<Task>();
        Cursor c = select_all_task();

        if(c.moveToFirst()) {
            int indexId = c.getColumnIndex("_id");
            int indexTask = c.getColumnIndex("task");
            int indexDate = c.getColumnIndex("reg_date");
            do {
                String ID = c.getString(indexId);
                String Task = c.getString(indexTask);
                String Date = c.getString(indexDate);
                arrTask.add(new Task(ID, Task, Date));
            } while(c.moveToNext());
        }

        return arrTask;
    }*/

    public void delete_dialog(String id) {
        db.delete(task_dbName, //table name
                "_id = ?", //where 절
                new String[] {id} //where절 들어갈 데이터
        );
    }

    public void Close() {
        db.close();
    }

}
