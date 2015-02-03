package com.example.nortika.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nortika on 2015-01-15.
 */
public class LockScreenActivity extends Activity implements OnGestureListener, View.OnTouchListener {

    private TextView taskLabel, dateLabel;
    private ImageButton btnRight, btnLeft;
    private int idx = 0;
    private int totalCnt = 0;
    String s[] = null;

    private ImageButton imgUnlock, imgIntoApp, imgCenter;
    private  int x, y;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private GestureDetector gestureScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_lockscreen);

        imgCenter = (ImageButton) this.findViewById(R.id.imgCenter);
        imgCenter.setOnTouchListener(this);

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

    private final int START_DRAG = 0;
    private final int END_DRAG = 1;
    private int isMoving;
    private boolean start_yn = true;

    public boolean onTouch(View v, MotionEvent event) {

        Vibrator mVibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {3, 3};

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (start_yn) {
                Log.d("position", "x:"+event.getRawX() + ", y:"+event.getRawY());
                start_yn = false;
            }
            isMoving = START_DRAG;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            // 손을 뗀 위치가 아래 지점이면 홈이동
            if((event.getRawX()>800) && (event.getRawY()>1508)){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            // 손을 뗀 위치가 아래 지점이면 어플이동
            else if((event.getRawX()<265) && (event.getRawY()>1508)){
                Intent configActivity = new Intent(LockScreenActivity.this, ConfigActivity.class);
                configActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(configActivity);
            }

            v.findViewById(R.id.imgCenter).setX(430);
            v.findViewById(R.id.imgCenter).setY(1430);
            isMoving = END_DRAG;

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (isMoving == START_DRAG) {
                //Log.d("position", "x:"+event.getRawX() + ", y:"+event.getRawY());

                // 홈화면으로 이동
                if((event.getRawX()>800) && (event.getRawY()>1508)){
                    v.findViewById(R.id.imgCenter).setX(839);
                    v.findViewById(R.id.imgCenter).setY(1422);

                    mVibe.vibrate(pattern, 0);
                    mVibe.vibrate(5);
                }
                else if((event.getRawX()<265) && (event.getRawY()>1508)){
                    v.findViewById(R.id.imgCenter).setX(-7);
                    v.findViewById(R.id.imgCenter).setY(1418);

                    mVibe.vibrate(pattern, 0);
                    mVibe.vibrate(5);
                }
                else {
                    v.setX((int) event.getRawX()-150);
                    v.setY((int) event.getRawY()-180);
                }
            }
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {

        return gestureScanner.onTouchEvent(event);
    }

    public boolean onDown(MotionEvent e) {
        return true;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;

            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                taskLabel.setText(s[--idx]);
                btnRight.setVisibility(View.VISIBLE);

                if(idx == 0) {
                    btnLeft.setVisibility(View.INVISIBLE);
                }
            }
            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                taskLabel.setText(s[++idx]);
                btnLeft.setVisibility(View.VISIBLE);

                if((idx + 1) == totalCnt) {
                    btnRight.setVisibility(View.INVISIBLE);
                }
            }
            // down to up swipe
            else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            }
            // up to down swipe
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            }
        } catch (Exception e) {

        }
        return true;
    }

    public void onLongPress(MotionEvent e) {
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }
}

