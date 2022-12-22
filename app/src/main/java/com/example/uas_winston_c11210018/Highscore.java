package com.example.uas_winston_c11210018;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Highscore extends AppCompatActivity {

    TextView tvscore;
    int lastscore;
    int best1,best2,best3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        tvscore = findViewById(R.id.tvscore2);

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        lastscore = preferences.getInt("lastscore",0);
        best1 = preferences.getInt("best1",0);
        best2 = preferences.getInt("best2",0);
        best3 = preferences.getInt("best3",0);

        if (lastscore > best3){
            best3 = lastscore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3" ,best3);
            editor.apply();
        }
        if (lastscore > best2){
            int temp = best2;
            best2 = lastscore;
            best3 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3" ,best3);
            editor.putInt("best2" ,best2);
            editor.apply();
        } if (lastscore > best1){
            int temp = best1;
            best1 = lastscore;
            best2 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2" ,best2);
            editor.putInt("best1" ,best1);
            editor.apply();
        }

        tvscore.setText("Last score: " + lastscore + "\n" + "BEST 1: " +best1 + "\n" + "BEST 2: " +best2 + "\n" + "BEST 3: " +best3);
    }
}