package com.example.uas_winston_c11210018;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private final List<Snakescore> snakescoreslist = new ArrayList<>();
    private SurfaceView surfaceView;
    private TextView scoretv;

    private String movingposition = "right";
    private SurfaceHolder surfaceHolder;

    private int score =0;
    private static final int pointsize = 28;
    private static final int defaulttalepoint =3;
    private static final int snakecolor = Color.RED;
    private static final int snakeMovingSpeed = 800;

    private int positionX, positionY;
    private Timer timer;
    private Canvas canvas = null;
    Paint pointColor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        surfaceView = findViewById(R.id.surface);
        scoretv = findViewById(R.id.score);


        final AppCompatImageButton top = findViewById(R.id.top);
        final AppCompatImageButton right = findViewById(R.id.right);
        final AppCompatImageButton left = findViewById(R.id.left);
        final AppCompatImageButton down = findViewById(R.id.down);
        surfaceView.getHolder().addCallback(this);

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!movingposition.equals("down")){
                    movingposition = "top";
                }
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!movingposition.equals("left")){
                    movingposition = "right";
                }
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!movingposition.equals("right")){
                    movingposition = "left";
                }
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!movingposition.equals("top")){
                    movingposition = "down";
                }
            }
        });
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

        init();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
    private void init(){
        snakescoreslist.clear();

        scoretv.setText("0");

        score = 0;

        movingposition = "right";

        int startpositionX = (pointsize) * defaulttalepoint;

        for(int i = 0; i < defaulttalepoint; i++){

            Snakescore score = new Snakescore(startpositionX, pointsize);
            snakescoreslist.add(score);
            startpositionX = startpositionX - (pointsize * 2);
        }
        addPoint();

        movesnake();
    }
    private void addPoint(){
        int surfacewidth = surfaceView.getWidth() - (pointsize * 2);
        int surfaceheight = surfaceView.getHeight() - (pointsize  * 2);

        int randomXposition = new Random().nextInt(surfacewidth / pointsize );
        int randomYposition = new Random().nextInt(surfaceheight / pointsize );

        if ((randomXposition % 2) != 0){
            randomXposition = randomXposition+ 1;
        }

        if ((randomYposition % 2) != 0) {
            randomYposition = randomYposition+ 1;
        }
        positionX = (pointsize * randomXposition) +pointsize;
        positionY = (pointsize * randomYposition) + pointsize;
    }
    private void movesnake(){



        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int headPositionX = snakescoreslist.get(0).getPositionX();
                int headPositionY = snakescoreslist.get(0).getPositionY();

                if (headPositionX == positionX && positionY == headPositionY) {
                    growsnake();
                    addPoint();
                }
                switch (movingposition){
                    case "right":
                        snakescoreslist.get(0).setPositionX(headPositionX + (pointsize * 2));
                        snakescoreslist.get(0).setPositionY(headPositionY);
                        break;

                    case "left":
                        snakescoreslist.get(0).setPositionX(headPositionX - (pointsize * 2));
                        snakescoreslist.get(0).setPositionY(headPositionY);
                        break;

                    case "top":
                        snakescoreslist.get(0).setPositionX(headPositionX);
                        snakescoreslist.get(0).setPositionY(headPositionY - (pointsize * 2));
                        break;

                    case "down":
                        snakescoreslist.get(0).setPositionX(headPositionX );
                        snakescoreslist.get(0).setPositionY(headPositionY + (pointsize * 2));
                        break;


                }
                if (checkgameover(headPositionX, headPositionY)){
                    timer.purge();
                    timer.cancel();

                    AlertDialog.Builder builder = new AlertDialog.Builder(SnakeActivity.this);
                    builder.setMessage("Your score =" +score);
                    builder.setTitle("Game Over");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Start Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            init();
                        }
                    });
                    builder.setNegativeButton("See score", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences preferences = getSharedPreferences("PREFS",0);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("lastscore", score);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(),Highscore.class);
                            startActivity(intent);

                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            builder.show();
                        }
                    });
                }
                else {
                    canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

                    canvas.drawCircle(snakescoreslist.get(0).getPositionX(), snakescoreslist.get(0).getPositionY(), pointsize, createpointcolor());

                    canvas.drawCircle(positionX, positionY, pointsize, createpointcolor());

                    for (int i = 1;i < snakescoreslist.size(); i++){
                        int getTempPositionX = snakescoreslist.get(i).getPositionX();
                        int getTempPositionY = snakescoreslist.get(i).getPositionY();

                        snakescoreslist.get(i).setPositionX(headPositionX);
                        snakescoreslist.get(i).setPositionY(headPositionY);
                        canvas.drawCircle(snakescoreslist.get(i).getPositionX(), snakescoreslist.get(i).getPositionY(),pointsize, createpointcolor());

                        headPositionX = getTempPositionX;
                        headPositionY = getTempPositionY;
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }, 1000 - snakeMovingSpeed, 1000 - snakeMovingSpeed);
    }

    private void growsnake() {
        Snakescore snakescore =  new Snakescore(0, 0);
        snakescoreslist.add(snakescore);
        score++;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoretv.setText(String.valueOf(score));
            }
        });
    }
    private boolean checkgameover(int headpositionX, int headpositionY){
        boolean gameover = false;

        if(snakescoreslist.get(0).getPositionX() < 0 ||
        snakescoreslist.get(0).getPositionY() < 0 ||
        snakescoreslist.get(0).getPositionX() >= surfaceView.getWidth() ||
        snakescoreslist.get(0).getPositionY() >= surfaceView.getHeight()){
            gameover = true;
        }else {
            for (int i = 1; i < snakescoreslist.size(); i++){
                if (headpositionX == snakescoreslist.get(i).getPositionX() &&
                headpositionY == snakescoreslist.get(i).getPositionY()){
                    gameover = true;
                    break;
                }
            }
        }
        return gameover;
    }
    private Paint createpointcolor(){
        if(pointColor == null){
            pointColor = new Paint();
            pointColor.setColor(snakecolor);
            pointColor.setStyle(Paint.Style.FILL);
            pointColor.setAntiAlias(true);
        }
            return pointColor;

    }
}