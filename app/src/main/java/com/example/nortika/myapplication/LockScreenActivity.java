package com.example.nortika.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nortika on 2015-01-15.
 */
public class LockScreenActivity extends FragmentActivity implements OnGestureListener {

    private TextView taskLabel, dateLabel;
    private ImageButton btnRight, btnLeft;
    private int idx = 0;
    private int totalCnt = 0;
    String s[] = null;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private GestureDetector gestureScanner;
    Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_lockscreen);

        /*MainTimerTask timerTask = new MainTimerTask();
        mTimer = new Timer();
        mTimer.schedule(timerTask, 500, 1000);*/

        gestureScanner = new GestureDetector(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        btnRight = (ImageButton)findViewById(R.id.imgRight);
        btnLeft = (ImageButton)findViewById(R.id.imgLeft);
        taskLabel= (TextView)findViewById(R.id.txtTaskList);
        dateLabel=(TextView)findViewById(R.id.txtDate);

        //날짜표시
        Date rightNow = new Date();

        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String strCurDate = CurDateFormat.format(rightNow);
        dateLabel.setText(strCurDate);

        //시간표시
        TextClock txtClock = (TextClock) findViewById(R.id.textClock);

        // 글씨체
        Typeface face = Typeface.createFromAsset(getAssets(), "font/tetrisL.ttf");
        taskLabel.setTypeface(face);
        dateLabel.setTypeface(face);
        txtClock.setTypeface(face);

        btnRight.setVisibility(View.INVISIBLE);
        btnLeft.setVisibility(View.INVISIBLE);

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

    /*private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {

            dateLabel=(TextView)findViewById(R.id.txtDate);
            timeLabel=(TextView)findViewById(R.id.txtTime);

            Date rightNow = new Date();

            SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH:mm");

            String strCurDate = CurDateFormat.format(rightNow);
            String strCurTime = CurTimeFormat.format(rightNow);

            dateLabel.setText(strCurDate);
            timeLabel.setText(strCurTime);
        }
    };

    class MainTimerTask extends TimerTask {
        public void run() {
            mHandler.post(mUpdateTimeTask);
        }
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mTimer.cancel();
        super.onPause();
    }

    @Override
    protected void onResume() {
        MainTimerTask timerTask = new MainTimerTask();
        mTimer.schedule(timerTask, 500, 3000);
        super.onResume();
    }*/

    public boolean onTouchEvent(MotionEvent me) {
        return gestureScanner.onTouchEvent(me);
    }

    public boolean onDown(MotionEvent e) {
        //viewA.setText("-" + "DOWN" + "-");
        return true;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;

            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                //Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
                taskLabel.setText(s[--idx]);
                btnRight.setVisibility(View.VISIBLE);

                if(idx == 0) {
                    btnLeft.setVisibility(View.INVISIBLE);
                }
            }
            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                //Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
                taskLabel.setText(s[++idx]);
                btnLeft.setVisibility(View.VISIBLE);

                if((idx + 1) == totalCnt) {
                    btnRight.setVisibility(View.INVISIBLE);
                }
            }
            // down to up swipe
            else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                //Toast.makeText(getApplicationContext(), "Swipe up", Toast.LENGTH_SHORT).show();
            }
            // up to down swipe
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                //Toast.makeText(getApplicationContext(), "Swipe down", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
        return true;
    }

    public void onLongPress(MotionEvent e) {
        Toast mToast = Toast.makeText(getApplicationContext(), "Long Press", Toast.LENGTH_SHORT);
        mToast.show();
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //viewA.setText("-" + "SCROLL" + "-");
        return true;
    }

    public void onShowPress(MotionEvent e) {
        //viewA.setText("-" + "SHOW PRESS" + "-");
    }

    public boolean onSingleTapUp(MotionEvent e) {
        Toast mToast = Toast.makeText(getApplicationContext(), "Single Tap", Toast.LENGTH_SHORT);
        mToast.show();
        return true;
    }
}

