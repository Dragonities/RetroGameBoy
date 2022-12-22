package com.example.uas_winston_c11210018;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class rectplayer implements gameobject{

    private Rect rectangle;
    private int color;

    public Rect getRectangle(){
        return rectangle;
    }
    public rectplayer(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }
    @Override
    public void update(){

    }

    public void update(Point point){

        float oldleft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2,point.y - rectangle.height()/2,point.x + rectangle.width()/2, point.y + rectangle.height()/2);
   int state = 0;
   if (rectangle.left - oldleft > 5){
       state=1;
   } else if (rectangle.left - oldleft < -5) {
       state = 2;
   }

    }
}
