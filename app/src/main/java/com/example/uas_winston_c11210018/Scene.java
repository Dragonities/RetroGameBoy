package com.example.uas_winston_c11210018;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene  {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receivetouch(MotionEvent event);
}
