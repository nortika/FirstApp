package com.example.nortika.myapplication;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by nortika on 2015-01-29.
 */
public class TouchEventView extends View {

    private ImageButton imgCenter;
    private  int x, y;

    public TouchEventView(Context context){
        super(context);
    }

    protected  void onDraw(Canvas canvas){
        canvas.drawBitmap(BitmapFactory.decodeResource(
                getResources(),
                R.drawable.center),
                x, y, null);
    }

    public boolean onTouchEvent(MotionEvent event){
        x=(int)event.getX();
        y=(int)event.getY();

        if(event.getAction()==MotionEvent.ACTION_DOWN){
        }
        if(event.getAction()==MotionEvent.ACTION_MOVE){
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
        }

        invalidate();
        //return super.onTouchEvent(event);
        return true;
    }
}
