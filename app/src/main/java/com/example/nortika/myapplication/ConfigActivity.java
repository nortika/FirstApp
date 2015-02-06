package com.example.nortika.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nortika on 2015-01-15.
 */
public class ConfigActivity extends Activity implements TextView.OnEditorActionListener {

    private Button onBtn, offBtn;
    private EditText editText;
    private TextView txtWeek;

    DBAdapter handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new DBAdapter(getApplicationContext());

        editText = (EditText)findViewById(R.id.editText);
        Typeface face = Typeface.createFromAsset(getAssets(), "font/tetrisL.ttf");

        editText.setOnEditorActionListener(this);
        editText.setTypeface(face);

        txtWeek = (TextView)findViewById(R.id.txtWeek);
        txtWeek.setTypeface(face);

        Date rightNow = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("MMM dd", new Locale("en", "US"));
        String strCurDate = sdf.format(rightNow);
        txtWeek.setText(getWeek() + ", " + strCurDate);

        //SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy hh:mm", new Locale("en", "US"));
        //t.setText(Html.fromHtml("<u>" + sitename + "</u>")); // 밑줄

        onBtn= (Button)findViewById(R.id.btn1);
        offBtn= (Button)findViewById(R.id.btn2);

        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this, ScreenService.class);
                startService(intent);
            }
        });

        offBtn.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this, ScreenService.class);
                stopService(intent);
            }
        });
    }

    protected String getWeek() {

        Calendar cal= Calendar.getInstance();

        final String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return week[cal.get(Calendar.DAY_OF_WEEK)-1];
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if(v.getId()==R.id.editText && actionId== EditorInfo.IME_ACTION_DONE){ // 뷰의 id를 식별, 키보드의 완료 키 입력 검출
            DialogSimple();
        }

        return false;
    }

    private void DialogSimple(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);


        alt_bld.setMessage("Do you want to add this ?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editText = (EditText)findViewById(R.id.editText);
                        handler.insert_task(editText.getText().toString());
                        handler.Close();
                        editText.setText("");
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("Task add");
        // Icon for AlertDialog
       // alert.setIcon(R.drawable.icon);
        alert.show();
    }
}
