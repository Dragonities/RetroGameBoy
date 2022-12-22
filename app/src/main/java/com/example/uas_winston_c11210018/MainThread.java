package com.example.uas_winston_c11210018;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    public static final int MAX_FPS =30;
    private double averagefps;
    private SurfaceHolder surfaceHolder;
    private gamepanel gamepanel;
    private boolean running;
    public static Canvas canvas;

    public void setRunning(boolean running){
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, gamepanel gamepanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamepanel = gamepanel;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totaltime = 0;
        long targettime = 1000/MAX_FPS;

        while (running){
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamepanel.update();
                    this.gamepanel.draw(canvas);
                }

            }catch (Exception e){
                e.printStackTrace();
            } finally {
                if (canvas != null){
                    try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targettime - timeMillis;
            try {
                if (waitTime > 0){
                    this.sleep(waitTime);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            totaltime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == MAX_FPS){
                averagefps = 1000/((totaltime/frameCount)/1000000);
                frameCount = 0;
                totaltime = 0;
                System.out.println(averagefps);
            }
        }
    }
}
