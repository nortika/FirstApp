package com.example.nortika.myapplication;

/**
 * Created by nortika on 2015-02-06.
 */
public class Task {
    String _id;
    String taskContent;
    String regDate;

    public void Task(String id, String task, String date){
        _id = id;
        taskContent = task;
        regDate = date;
    }
}
