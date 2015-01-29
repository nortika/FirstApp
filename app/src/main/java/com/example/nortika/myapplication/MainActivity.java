package com.example.nortika.myapplication;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnTouchListener {

    Button btn = null;
    int x = 0;
    int y = 0;
    int rawx = 0;
    int rawy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new TouchEventView(this));
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById (R.id.btn1);
        btn.setOnTouchListener (this);
    }

    public   boolean onTouch (View view, MotionEvent event) {
        int eventaction = event.getAction ();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX ();
                y = (int) event.getY ();
                rawx = (int) event.getRawX ();
                rawy = (int) event.getRawY ();
                Log.d("DEBUG", "getDX =" + x + "getDY =" + y + "\n" + "getDRawX =" + rawx + "GetDRawY =" + rawy + "\n");
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int) event.getX ();
                y = (int) event.getY ();
                rawx = (int) event.getRawX ();
                rawy = (int) event.getRawY ();
                Log.d("DEBUG", "getX =" + x + "getY =" + y + "\n" + "getRawX =" + rawx + "GetRawY =" + rawy + "\n");
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return   false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
