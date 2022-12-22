package com.example.uas_winston_c11210018;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class obstacle implements gameobject{

    private Rect rectangle;
    private int color;
    private Rect rectangle2;

    public Rect getRectangle(){
        return rectangle;

    }
    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    public obstacle(int rectheight, int color, int startX, int startY, int playergap){

        this.color = color;

        rectangle = new Rect(0, startY, startX, startY + rectheight);
        rectangle2 = new Rect(startX + playergap,startY, constant.SCREEN_WIDTH, startY + rectheight);

    }

    public boolean playercollide(rectplayer player){
      return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2,player.getRectangle());
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }
    @Override
    public void update(){

    }
}
