package com.example.uas_winston_c11210018;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class gamepanel extends SurfaceView implements SurfaceHolder.Callback  {
    private MainThread thread;
private scenemanager manager;

    public gamepanel(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(),this);
        constant.currentcontext = context;

        manager = new scenemanager();

        setFocusable(true);

    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread = new MainThread(getHolder(),this);
        constant.INIT_Time = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            boolean retry = true;
            while (retry){
                try {
                    thread.setRunning(false);
                    thread.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
                retry = false;
            }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manager.receivetouch(event);
        return true;
    }
    public void update(){
        manager.update();
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    manager.draw(canvas);

    }



}
