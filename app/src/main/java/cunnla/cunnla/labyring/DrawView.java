package cunnla.cunnla.labyring;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Path;

import static android.content.Context.MODE_PRIVATE;


public class DrawView extends View {

    Paint paint;

    Path hero;
    int heroStep = 15;
    int heroSize = 30;
    int heroXStart = 300;
    int heroYStart = 570;
    int heroX = heroXStart;
    int heroY = heroYStart;

    Path walls;
    Path target;

    Path bomb1;
    int bomb1XStart = 90;
    int bomb1YStart = 450;

    Path bomb2;
    int bomb2XStart = 510;
    int bomb2YStart = 90;

    private static final String TAG = "myLogs";

    boolean intersects = false;

    SharedPreferences sPref;


    // ////////   constructors

    public DrawView(Context context){
        super(context);

        paint = new Paint();
        walls = new Path();
        target = new Path();
        bomb1 = new Path();
        bomb2 = new Path();
        hero = new Path();

    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        walls = new Path();
        target = new Path();
        bomb1 = new Path();
        bomb2 = new Path();
        hero = new Path();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();
        walls = new Path();
        target = new Path();
        bomb1 = new Path();
        bomb2 = new Path();
        hero = new Path();
    }


    public void onDraw (Canvas canvas){
        canvas.drawColor(getResources().getColor(R.color.colorField));

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);

        walls.reset();
        walls.moveTo(270, 630);
        walls.lineTo(270, 540);
        walls.lineTo(90, 540);
        walls.lineTo(90, 90);
        walls.lineTo(180, 90);
        walls.lineTo(180, 270);
        walls.lineTo(450, 270);
        walls.lineTo(450, 180);
        walls.lineTo(270, 180);
        walls.lineTo(270, 0);

        walls.moveTo(360, 630);
        walls.lineTo(360, 540);
        walls.lineTo(540, 540);
        walls.lineTo(540, 450);
        walls.lineTo(180, 450);
        walls.lineTo(180, 360);
        walls.lineTo(540, 360);
        walls.lineTo(540, 90);
        walls.lineTo(360, 90);
        walls.lineTo(360, 0);

        canvas.drawPath(walls, paint);

        paint.setColor(getResources().getColor(R.color.colorTarget));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);
        target.reset();
        target.moveTo(271, 0);
        target.lineTo(360, 0);
        target.lineTo(360, 90);
        target.lineTo(271, 90);
        target.close();
        canvas.drawPath(target, paint);

        paint.setColor(getResources().getColor(R.color.colorBomb));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);
        bomb1.reset();
        bomb1.moveTo(bomb1XStart, bomb1YStart);
        bomb1.lineTo(bomb1XStart+30, bomb1YStart);
        bomb1.lineTo(bomb1XStart+30, bomb1YStart+30);
        bomb1.lineTo(bomb1XStart, bomb1YStart+30);
        bomb1.close();
        canvas.drawPath(bomb1, paint);

        paint.setColor(getResources().getColor(R.color.colorBomb));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);
        bomb2.reset();
        bomb2.moveTo(bomb2XStart, bomb2YStart);
        bomb2.lineTo(bomb2XStart+30, bomb2YStart);
        bomb2.lineTo(bomb2XStart+30, bomb2YStart+30);
        bomb2.lineTo(bomb2XStart, bomb2YStart+30);
        bomb2.close();
        canvas.drawPath(bomb2, paint);


        paint.setColor(getResources().getColor(R.color.colorHero));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);
        hero.reset();
        hero.moveTo(heroX, heroY);
        hero.lineTo(heroX, heroY+heroSize);
        hero.lineTo(heroX+heroSize, heroY+heroSize);
        hero.lineTo(heroX+heroSize, heroY);
        hero.close();
        canvas.drawPath(hero, paint);

    }

    public void moveUp (Canvas canvas){

        Log.d(TAG, "Moving UP");

        if (       ((heroY==540)&& (((heroX>90)&&(heroX<270)) || (((heroX+heroSize>90)&&(heroX+heroSize<270)))))
                || ((heroY==270)&& (((heroX>180)&&(heroX<450)) || (((heroX+heroSize>180)&&(heroX+heroSize<450)))))
                || ((heroY==90) && (((heroX>90)&&(heroX<180)) || (((heroX+heroSize>90)&&(heroX+heroSize<180)))))
                || ((heroY==180)&& (((heroX>270)&&(heroX<450)) || (((heroX+heroSize>270)&&(heroX+heroSize<450)))))

                || ((heroY==540)&& (((heroX>360)&&(heroX<540)) || (((heroX+heroSize>360)&&(heroX+heroSize<540)))))
                || ((heroY==450)&& (((heroX>180)&&(heroX<540)) || (((heroX+heroSize>180)&&(heroX+heroSize<540)))))
                || ((heroY==360)&& (((heroX>180)&&(heroX<540)) || (((heroX+heroSize>180)&&(heroX+heroSize<540)))))
                || ((heroY==90) && (((heroX>360)&&(heroX<540)) || (((heroX+heroSize>360)&&(heroX+heroSize<540)))))      ){
            intersects = true;
        };


        if (heroY>=1 && !intersects ) {
            heroY-=heroStep;
            this.invalidate();
        }
        intersects = false;
    }

    public void moveDown (Canvas canvas){
        Log.d(TAG, "Moving DOWN");

        if (       ((heroY+heroSize==540)&& (((heroX>90)&&(heroX<270)) || (((heroX+heroSize>90)&&(heroX+heroSize<270)))))
                || ((heroY+heroSize==270)&& (((heroX>180)&&(heroX<450)) || (((heroX+heroSize>180)&&(heroX+heroSize<450)))))
                || ((heroY+heroSize==90) && (((heroX>90)&&(heroX<180)) || (((heroX+heroSize>90)&&(heroX+heroSize<180)))))
                || ((heroY+heroSize==180)&& (((heroX>270)&&(heroX<450)) || (((heroX+heroSize>270)&&(heroX+heroSize<450)))))

                || ((heroY+heroSize==540)&& (((heroX>360)&&(heroX<540)) || (((heroX+heroSize>360)&&(heroX+heroSize<540)))))
                || ((heroY+heroSize==450)&& (((heroX>180)&&(heroX<540)) || (((heroX+heroSize>180)&&(heroX+heroSize<540)))))
                || ((heroY+heroSize==360)&& (((heroX>180)&&(heroX<540)) || (((heroX+heroSize>180)&&(heroX+heroSize<540)))))
                || ((heroY+heroSize==90) && (((heroX>360)&&(heroX<540)) || (((heroX+heroSize>360)&&(heroX+heroSize<540)))))      ){
            intersects = true;
        };

        if (heroY+heroSize<=629 && !intersects) {
            heroY += heroStep;
            this.invalidate();
        }
        intersects = false;
    }

    public void moveLeft (Canvas canvas){
        Log.d(TAG, "Moving LEFT");
        Log.d(TAG, "heroX="+heroX+", heroY="+heroY+", heroY+heroSize="+heroY+heroSize);

        if (        ((heroX==270)&& (((heroY>540)&&(heroY<630)) || (((heroY+heroSize>540)&&(heroY+heroSize<630)))))
                ||  ((heroX==90) && (((heroY>90)&&(heroY<540)) || (((heroY+heroSize>90)&&(heroY+heroSize<540)))))
                ||  ((heroX==180)&& (((heroY>90)&&(heroY<270)) || (((heroY+heroSize>90)&&(heroY+heroSize<270)))))
                ||  ((heroX==450)&& (((heroY>180)&&(heroY<270)) || (((heroY+heroSize>180)&&(heroY+heroSize<270)))))
                ||  ((heroX==270)&& (((heroY>0)&&(heroY<180)) || (((heroY+heroSize>0)&&(heroY+heroSize<180)))))

                ||  ((heroX==360)&& (((heroY>540)&&(heroY<630)) || (((heroY+heroSize>540)&&(heroY+heroSize<630)))))
                ||  ((heroX==540)&& (((heroY>450)&&(heroY<540)) || (((heroY+heroSize>450)&&(heroY+heroSize<540)))))
                ||  ((heroX==180)&& (((heroY>360)&&(heroY<450)) || (((heroY+heroSize>360)&&(heroY+heroSize<450)))))
                ||  ((heroX==540)&& (((heroY>90)&&(heroY<360)) || (((heroY+heroSize>90)&&(heroY+heroSize<360)))))
                ||  ((heroX==360)&& (((heroY>0)&&(heroY<90)) || (((heroY+heroSize>0)&&(heroY+heroSize<90)))))       ){

            intersects = true;
        }


        if ((heroX>=1)&& !intersects) {

            heroX -= heroStep;
            this.invalidate();
        }
        intersects = false;
    }

    public void moveRight (Canvas canvas){
        Log.d(TAG, "Moving RIGHT");

        if (        ((heroX+heroSize==270)&& (((heroY>540)&&(heroY<630)) || (((heroY+heroSize>540)&&(heroY+heroSize<630)))))
                ||  ((heroX+heroSize==90) && (((heroY>90)&&(heroY<540)) || (((heroY+heroSize>90)&&(heroY+heroSize<540)))))
                ||  ((heroX+heroSize==180)&& (((heroY>90)&&(heroY<270)) || (((heroY+heroSize>90)&&(heroY+heroSize<270)))))
                ||  ((heroX+heroSize==450)&& (((heroY>180)&&(heroY<270)) || (((heroY+heroSize>180)&&(heroY+heroSize<270)))))
                ||  ((heroX+heroSize==270)&& (((heroY>0)&&(heroY<180)) || (((heroY+heroSize>0)&&(heroY+heroSize<180)))))

                ||  ((heroX+heroSize==360)&& (((heroY>540)&&(heroY<630)) || (((heroY+heroSize>540)&&(heroY+heroSize<630)))))
                ||  ((heroX+heroSize==540)&& (((heroY>450)&&(heroY<540)) || (((heroY+heroSize>450)&&(heroY+heroSize<540)))))
                ||  ((heroX+heroSize==180)&& (((heroY>360)&&(heroY<450)) || (((heroY+heroSize>360)&&(heroY+heroSize<450)))))
                ||  ((heroX+heroSize==540)&& (((heroY>90)&&(heroY<360)) || (((heroY+heroSize>90)&&(heroY+heroSize<360)))))
                ||  ((heroX+heroSize==360)&& (((heroY>0)&&(heroY<90)) || (((heroY+heroSize>0)&&(heroY+heroSize<90)))))       ){
            intersects = true;
        }

        if (heroX+heroSize<=629 && !intersects) {
            heroX += heroStep;
            this.invalidate();
        }
        intersects = false;
    }

    public boolean winGame(){
        boolean win = false;
        if ((heroX>=270)&& (heroX+heroSize<=360) && (heroY>=0)&&(heroY+heroSize<=90)){
            win = true;
        }
        return win;
    }

    public boolean explode(){
        boolean explode = false;

        if (  (heroX==bomb1XStart)&&(heroY==bomb1YStart) ||
              (heroX==bomb2XStart)&&(heroY==bomb2YStart)             ){
            explode = true;
        }
        return explode;
    }

    public void startAgain(){
        heroX = heroXStart;
        heroY = heroYStart;
        this.invalidate();
    }



}
