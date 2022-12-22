package com.example.uas_winston_c11210018;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class gameplayscene extends AppCompatActivity implements Scene{
    private Rect r = new Rect();

    private rectplayer player;
    private Point playerpoint;
    private Obstaclemanager obstaclemanager;

    private boolean movingplayer = false;
    private boolean gameover = false;

    private long gameovertime;

    private orientation orientation;
    private long frametime;
int score = 0;
public gameplayscene(){
    player = new rectplayer(new Rect(100,100,200,200), Color.rgb(255,0,0));
    playerpoint = new Point(constant.SCREEN_WIDTH/2, 3*constant.SCREEN_HEIGHT/4);
    player.update(playerpoint);


    obstaclemanager = new  Obstaclemanager(200,350,75, Color.BLACK);

    orientation = new orientation();
    orientation.register();
    frametime = System.currentTimeMillis();




}
    public  void reset(){

        playerpoint = new Point(constant.SCREEN_WIDTH/2, 3*constant.SCREEN_HEIGHT/4);
        player.update(playerpoint);
        obstaclemanager = new  Obstaclemanager(200,350,75, Color.BLACK);
        movingplayer = false;
    }
    @Override
    public void terminate() {

    }

    @Override
    public void receivetouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!gameover && player.getRectangle().contains((int)event.getX(), (int)event.getY()))
                    movingplayer = true;
                if (gameover && System.currentTimeMillis() - gameovertime >= 2000){

                    reset();
                    gameover = false;
                    orientation.newgame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameover && movingplayer)
                    playerpoint.set((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingplayer = false;
                break;
        }
    }



    @Override
    public void update() {
        if (!gameover) {
            if (frametime <constant.INIT_Time){
                frametime = constant.INIT_Time;
            }
            int elapsedtime = (int)(System.currentTimeMillis() - frametime);
            frametime = System.currentTimeMillis();
            if (orientation.getOrientation() != null && orientation.getStartorientation() != null){
                float pitch = orientation.getOrientation()[1] - orientation.getStartorientation()[1];
                float roll = orientation.getOrientation()[2] - orientation.getStartorientation()[2];

                float speedx = 2*roll * constant.SCREEN_WIDTH/1000f;
                float speedy = pitch * constant.SCREEN_HEIGHT/1000f;

                playerpoint.x += Math.abs(speedx*elapsedtime) > 5 ? speedx*elapsedtime : 0;
                playerpoint.y += Math.abs(speedy*elapsedtime) > 5 ? speedy*elapsedtime : 0;
                }

            if (playerpoint.x < 0) {
                playerpoint.x = 0;
            }
            else if (playerpoint.x > constant.SCREEN_WIDTH) {
                playerpoint.x = constant.SCREEN_WIDTH;
            }
            if (playerpoint.y < 0) {
                playerpoint.y = 0;
            }
            else if (playerpoint.y > constant.SCREEN_HEIGHT) {
                playerpoint.y = constant.SCREEN_HEIGHT;
            }

            player.update(playerpoint);
            obstaclemanager.update();

            if (obstaclemanager.playercollide(player)){
                gameover =true;
                gameovertime = System.currentTimeMillis();
            }
        }


    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstaclemanager.draw(canvas);
        if (gameover){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawcentertext(canvas, paint, "Gameover");


        }
    }


    private void drawcentertext(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cwidth = r.width();
        paint.getTextBounds(text,0,text.length(),r);
        float x = cwidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() /2f - r.bottom;
        canvas.drawText(text,x,y,paint);

    }
    }
