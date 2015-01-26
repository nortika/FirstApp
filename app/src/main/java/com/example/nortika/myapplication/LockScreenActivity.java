package com.example.nortika.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nortika on 2015-01-15.
 */
public class LockScreenActivity extends ActionBarActivity {

    private TextView taskLabel, dateLabel, timeLabel;
    private ImageButton btnRight, btnLeft;
    private int idx = 0;
    private int totalCnt = 0;
    String s[] = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_lockscreen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        btnRight = (ImageButton)findViewById(R.id.imgRight);
        btnLeft = (ImageButton)findViewById(R.id.imgLeft);
        taskLabel= (TextView)findViewById(R.id.txtTaskList);
        dateLabel=(TextView)findViewById(R.id.txtDate);
        timeLabel=(TextView)findViewById(R.id.txtTime);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/tetrisL.ttf");
        taskLabel.setTypeface(face);
        dateLabel.setTypeface(face);
        timeLabel.setTypeface(face);

        btnRight.setVisibility(View.INVISIBLE);
        btnLeft.setVisibility(View.INVISIBLE);

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH:mm");

        String strCurDate = CurDateFormat.format(date);
        String strCurTime = CurTimeFormat.format(date);

        dateLabel.setText(strCurDate);
        timeLabel.setText(strCurTime);

        s = new String[]{ "맥 가져오기", "병원가기" };
        totalCnt = s.length;

        if(s.length == 0)
            taskLabel.setText("할일 없음");
        else if(s.length == 1){
            taskLabel.setText(s[0]);
        }
        else if(2 <= s.length){
            taskLabel.setText(s[0]);
            btnRight.setVisibility(View.VISIBLE);
        }

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskLabel.setText(s[++idx]);
                btnLeft.setVisibility(View.VISIBLE);

                if((idx + 1) == totalCnt) {
                    btnRight.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskLabel.setText(s[--idx]);
                btnRight.setVisibility(View.VISIBLE);

                if(idx == 0) {
                    btnLeft.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}

