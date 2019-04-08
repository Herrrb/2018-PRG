package com.example.herrrb.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.herbwen.myapplication.MainActivity;
import com.herbwen.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the intent that started this activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String time_message = intent.getStringExtra(MainActivity.TIME_MESSAGE);

        String time_m = "程序运行时间:" + time_message + "ms";
        int num_1 = appearNumber(message, "1");
        int num_0 = appearNumber(message, "0");

        String num = "0的个数:" + Integer.toString(num_0) + "; 1的个数:" + Integer.toString(num_1);
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        TextView numberOf = findViewById(R.id.numberOf);
        numberOf.setText(num);

        TextView tc = findViewById(R.id.TC);
        tc.setText(time_m);
    }


    public static int appearNumber(String srcText, String findText){
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()){
            count++;
        }
        return count;
    }
}
