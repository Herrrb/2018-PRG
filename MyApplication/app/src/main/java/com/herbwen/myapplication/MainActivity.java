package com.herbwen.myapplication;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TimeUtils;
import android.view.View;
import android.widget.EditText;

import com.example.herrrb.myapplication.DisplayMessageActivity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.herbwen.myapplication.MESSAGE";
    public static final String TIME_MESSAGE = "com.herbwen.muapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //    Called when the user taps the send button
    public void sendMessage(View view){
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        int size;
        size = Integer.parseInt(message);
//        int[] final_result_ = generator(size);
        long startTime = System.currentTimeMillis();
        String result_ = generator(size);
        long endTime = System.currentTimeMillis();
//        String result_ = Arrays.toString(final_result_);
        long time = endTime - startTime;
        String time_ = Long.toString(time);
        intent.putExtra(EXTRA_MESSAGE, result_);
        intent.putExtra(TIME_MESSAGE, time_);
        startActivity(intent);
    }

    public int[] seq(Double first_num, int size) {
        int lambda_ = 4;
        double[] l = new double[size];
        l[0] = first_num;
        int[] l1 = new int[size];
        if(first_num > 0.5){
            l1[0] = 1;
        } else {
            l1[0] = 0;
        }
        for(int i=1; i<size; i++){
            l[i] = lambda_ * l[i-1] * (1 - l[i-1]);
            if(l[i] > 0.5){
                l1[i] = 1;
            } else {
                l1[i] = 0;
            }
        }
        return l1;
    }

    public int[] select(double first_num, int size, double a, double b) {
        double[] l = new double[size];
        l[0] = first_num;
        int[] l1 = new int[size];
        if(first_num > 0.5) {
            l1[0] = 1;
        } else {
            l1[0] = 0;
        }
        for(int i=1; i<size; i++){
            l[i] = (a/l[i-1] + 4 * l[i-1] * (1 - l[i-1])) % b;
            if(l[i] > 0.5){
                l1[i] = 1;
            } else {
                l1[i] = 0;
            }
        }
        return l1;
    }

    public String choose(int[] s1, int[] s2, int[] ss){
        int len = s1.length;
        StringBuilder l = new StringBuilder();
        for(int i=0; i<len-30; i++){
            int s = ss[i+30];
            if(s==0){
                l.append(s1[i+30]);
            } else {
                l.append(s2[i+30]);
            }
        }
        return l.toString();
    }

    public double[] get_first_value(){
        double f1 = Process.myPid() * get_time() / Math.pow(10, 19);
        double f2 = Process.myTid() * get_time() / Math.pow(10, 19);
        double f3 = get_time() / Math.pow(10, 18);
        double[] l = new double[3];
        l[0] = Math.abs(f1);
        l[1] = Math.abs(f2);
        l[2] = Math.abs(f3);
        return l;
    }

    public long get_time(){
        long time = System.currentTimeMillis();
        long time1 = System.nanoTime();
        String time_s = Long.toString(time);
        String time_s1 = Long.toString(time1);
        String time_s_0 = time_s1.substring(3);
        String time_s_1 = time_s.substring(8, 13) + time_s.substring(10, 13);
        return Long.parseLong(time_s_0) * Long.parseLong(time_s_1);
    }

    public String generator(int size){
        int size_ = size + 30;
        double[] first_value = get_first_value();
        double f1 = first_value[0];
        double f2 = first_value[1];
        double f3 = first_value[2];
        int[] seq_1 = seq(f1, size_);
        int[] seq_2 = seq(f2, size_);
        int[] seq_select = select(f3, size_, 1000, 1);
        return choose(seq_1, seq_2, seq_select);
    }
}


