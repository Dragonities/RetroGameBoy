package com.example.uas_winston_c11210018;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class scenemanager {
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int activeScene;

    public scenemanager(){
        activeScene =0;
        scenes.add(new gameplayscene());
    }
    public void receivetouch(MotionEvent event){
        scenes.get(activeScene).receivetouch(event);
    }
    public void update(){
        scenes.get(activeScene).update();
    }
    public void draw(Canvas canvas){
        scenes.get(activeScene).draw(canvas);
    }
}
