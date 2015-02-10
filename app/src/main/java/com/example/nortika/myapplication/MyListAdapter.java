package com.example.nortika.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nortika on 2015-02-10.
 */
public class MyListAdapter {
    Context maincon;
    LayoutInflater inflater;
    ArrayList<Task> arrTask;

    int layout;

    public MyListAdapter(Context context, int alayout, ArrayList<Task> arrSrcTask) {

        maincon = context;
        arrTask = arrSrcTask;
        layout = alayout;
        // ListView에서 사용한 View를 정의한 xml 를 읽어오기 위해
        // LayoutInfalater 객체를 생성

        inflater = LayoutInflater.from(maincon);
    }

    /*public int getCount() {
        return arrTask.size();
    }

    public Object getItem(int position) {
        return arrTask.get(position).task;
    }

    public long getItemId(int position) {
        return position;
    }
    // 각 항목의 뷰 생성

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        img.setImageResource(arSrc.get(position).icon);
        TextView txt = (TextView) convertView.findViewById(R.id.text);
        txt.setText(arSrc.get(position).name);
        Button btn = (Button) convertView.findViewById(R.id.btn);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String str = arSrc.get(pos).name + "를 주문합니다.";
                Toast.makeText(maincon, str, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }*/
}
