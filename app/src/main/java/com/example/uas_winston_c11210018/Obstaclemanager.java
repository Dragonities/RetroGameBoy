package com.example.uas_winston_c11210018;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Obstaclemanager extends AppCompatActivity {

    private ArrayList<obstacle> obstacles;
    private int playergap;
    private int obstaclegap;
    private int obstacleheight;
    private int color;
    private long starttime;
    private  long inittime;
    private int score = 0;

    public Obstaclemanager(int playergap, int obstaclegap, int obstacleheight, int color){
        this.playergap = playergap;
        this.obstaclegap = obstaclegap;
        this.obstacleheight = obstacleheight;
        this.color = color;

        starttime = inittime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateobstacle();

    }

    public boolean playercollide(rectplayer player){
        for (obstacle ob : obstacles){
            if(ob.playercollide(player)) {

                return true;
            }
        }

        return false;

    }

    private void populateobstacle(){
        int currY = -5*constant.SCREEN_HEIGHT/4;

        while (currY < 0){
            int startx =  (int)(Math.random()*(constant.SCREEN_WIDTH - playergap));
            obstacles.add(new obstacle(obstacleheight,color, startx, currY,playergap));
            currY += obstacleheight + obstaclegap;



        }
    }
    public void update(){
        if (starttime <constant.INIT_Time) {
            starttime = constant.INIT_Time;
        }
            int elapsedtime = (int) (System.currentTimeMillis() - starttime);
            starttime = System.currentTimeMillis();
            float speed = (float) (Math.sqrt(5 + (starttime - inittime) / 10000.0)) * constant.SCREEN_HEIGHT / 10000.0f;
            for (obstacle ob : obstacles) {
                ob.incrementY(speed * elapsedtime);

            }

        if (obstacles.get(obstacles.size()-1).getRectangle().top >= constant.SCREEN_HEIGHT){

            int startx = (int) (Math.random()*(constant.SCREEN_WIDTH - playergap));
            obstacles.add(0,new obstacle(obstacleheight, color, startx, obstacles.get(0).getRectangle().top - obstacleheight - obstaclegap,playergap));
            obstacles.remove(obstacles.size() - 1);

            score++;

        }
//        SharedPreferences preferences = getSharedPreferences("PREFS2", 0);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("lastscore2", score);
//        editor.apply();



    }
    public void draw(Canvas canvas){
        for (obstacle ob : obstacles){
            ob.draw(canvas);
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            canvas.drawText("" + score,50,50 + paint.descent() - paint.ascent(), paint);
        }
    }
}
