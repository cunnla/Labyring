package cunnla.cunnla.labyring;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Path;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class DrawView extends View {

    Paint paint;
    Coordinates field;

    Coordinates heroStart = new Coordinates(0,0);
    Coordinates hero = new Coordinates(0,0);
    boolean doWeDrawHero = true;
    Coordinates exit = new Coordinates(1,1);
    ArrayList<Coordinates> bombsList = new ArrayList<Coordinates>();


    private static final String TAG = "myLogs";

    boolean intersects = false;

    SharedPreferences sPref;


    int[][]vMatrix = new int[7][7];

    // 1 is path
    // 2 is exit
    // 3 is heroStart
    // 4 is the bombs


    //     ====  constructors  =====

    public DrawView(Context context){
        super(context);

        paint = new Paint();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();
    }

    //     ====  end of constructors  =====



    public void onDraw (Canvas canvas){

            //  ====    draw the matrix  ======


            for (int x = 0; x <= Coordinates.fieldSize; x++) {
                for (int y = 0; y <= Coordinates.fieldSize; y++) {

                    field = new Coordinates(x, y);  // coordinates to draw squares on the canvas
                    paint.setStrokeWidth(1);
                    paint.setStyle(Paint.Style.FILL);

                    switch (vMatrix[x][y]) {
                        case 0: //if it's the walls
                            paint.setColor(getResources().getColor(R.color.colorField));
                            break;
                        case 1:   //if it's the path
                            paint.setColor(Color.BLACK);
                            break;
                        case 2:   //if it's the exit
                            paint.setColor(getResources().getColor(R.color.colorTarget));
                            exit = new Coordinates(y, x);
                            Log.d("Matrix", "exitX=" + exit.x + ", exitY=" + exit.y);
                            break;
                        case 3:    //if it's the hero, then we determine the starting point
                            hero = new Coordinates(y, x);
                            heroStart = new Coordinates(y, x);
                            vMatrix[x][y] = 1;     // make this square a passage
                            paint.setColor(Color.BLACK);

                            Log.d("Matrix", "hero.x=" + hero.x + ", hero.y=" + hero.y);
                            Log.d("Matrix", "vMatrix[x][y]=" + vMatrix[x][y]);

                            // we do not draw the hero square here, because the hero will be always moving. We just draw the field.
                            break;
                        case 4:    //if it's the bombs
                            paint.setColor(getResources().getColor(R.color.colorBomb));
                            bombsList.add(new Coordinates(y, x));
                            break;
                    }
                    canvas.drawRect((float) field.coordToPx(y), (float) field.coordToPx(x), (float) field.coordToPx(y + 1), (float) field.coordToPx(x + 1), paint);
                }
            }
            // =====    end of drawing the matrix ======

            // === draw the hero  =====
        if (doWeDrawHero) {
            paint.setColor(getResources().getColor(R.color.colorHero));
            canvas.drawRect((float) hero.coordToPx(hero.x), (float) hero.coordToPx(hero.y), (float) hero.coordToPx(hero.x + 1), (float) hero.coordToPx(hero.y + 1), paint);
        }



    }


    public void moveUp (Canvas canvas){
        Log.d("Moving", "Moving UP");
        if ((hero.x>0)&&(hero.y>0)){Log.d("Matrix", "Next matrix cell:"+vMatrix[hero.x][hero.y-1]);}
        Log.d("Moving:", "Next move:"+hero.x+", "+(hero.y-1));

        if (hero.y>0) {
                 if (vMatrix[hero.y-1][hero.x]==0){   // if next cell is not 0, and if this is not the end of matrix
                    Log.d("Moving", "Got to matrix cell=0 condition");
                    intersects = true;
                 }
        } else if (hero.y ==0){
            Log.d("Moving", "Got to the top row");
            intersects = true;
        }

        if (!intersects) {
            hero.y-=1;
            Log.d("Moving", "new hero.y: "+hero.y);
            this.invalidate();
        }
        intersects = false;
    }

    public void moveDown (Canvas canvas){
        Log.d("Moving", "Moving DOWN");
        Log.d("Moving", "Next move:"+hero.x+", "+(hero.y+1));
 //       Log.d("Moving", "Next matrix cell:"+vMatrix[heroX][heroY+1]);

        if (hero.y<Coordinates.fieldSize) {
            if ((vMatrix[hero.y + 1][hero.x] == 0)) {   // if next cell is not 0, and if this is not the end of matrix
                Log.d("Moving", "Got to the bottom row");
                intersects = true;
            }
        } else if (hero.y == Coordinates.fieldSize){
            Log.d("Moving", "Got to matrix cell count =6 condition");
            intersects = true;
        }

        if (!intersects ) {
            hero.y+=1;
            Log.d("Moving", "new hero.y: "+hero.y);
            this.invalidate();
        }
        intersects = false;
    }

    public void moveLeft  (Canvas canvas){
        Log.d("Moving", "Moving LEFT");
        Log.d("Moving", "Next move:"+hero.x+"-1"+", "+hero.y);
        //       Log.d("Moving", "Next matrix cell:"+vMatrix[heroX-1][heroY]);
        if (hero.x>0) {
            if (vMatrix[hero.y][hero.x-1]==0){   // if next cell is not 0, and if this is not the end of matrix
                Log.d("Moving", "Got to matrix cell=0 condition");
                intersects = true;
            }
        } else if (hero.x ==0){
            Log.d("Moving", "Got to Left end");
            intersects = true;
        }

        if (!intersects) {
            hero.x-=1;
            Log.d("Moving", "new heroX: "+hero.x);
            this.invalidate();
        }
        intersects = false;
    }

    public void moveRight  (Canvas canvas){
        Log.d("Moving", "Moving RIGHT");
        Log.d("Moving", "Next move:"+hero.x+1+", "+hero.y);
        //       Log.d("Moving", "Next matrix cell:"+vMatrix[heroX+1][heroY]);
        if (hero.x<Coordinates.fieldSize) {
            if (vMatrix[hero.y][hero.x+1]==0){   // if next cell is not 0, and if this is not the end of matrix
                Log.d("Moving", "Got to matrix cell=0 condition");
                intersects = true;
            }
        } else if (hero.x ==Coordinates.fieldSize){
            Log.d("Moving", "Got to the Right end");
            intersects = true;
        }

        if (!intersects) {
            hero.x+=1;
            Log.d("Moving", "new heroX: "+hero.x);
            this.invalidate();
        }
        intersects = false;
    }


    public boolean winGame(){
        boolean win = false;
        if (hero.equals(exit)){
            win = true;
            Log.d("Matrix", "Wingame: hero.x: "+hero.x+", hero.y: "+hero.y+ " exit.x: "+exit.x+", exit.y: "+exit.y);
        }
        return win;
    }

    public boolean explode(){
        boolean explode = false;
        for (int i = 0; i < bombsList.size(); i++) {
            Coordinates bomb = bombsList.get(i);
            if (hero.equals(bomb)){
                explode = true;
  //              Log.d("Matrix", "Wingame: hero.x: "+hero.x+", hero.y: "+hero.y+ " bomb.x: "+bomb.x+", bomb.y: "+bomb.y);
            }
        }
        return explode;
    }

    public void startAgain(){
        hero.x = heroStart.x;
        hero.y = heroStart.y;
        this.invalidate();
    }

    public void reset(){
        vMatrix = new int [][] {{0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0}};

        doWeDrawHero = false;
    }

    public void setTestMatrixBebebe(){
        vMatrix = new int [][] {{0,0,4,0,0,0,0},
                {1,0,0,0,4,0,0},
                {1,0,4,0,0,0,0},
                {1,0,0,0,0,4,0},
                {0,0,4,0,0,0,0},
                {0,0,0,0,0,4,0},
                {0,0,4,0,0,0,0}};

    }

    public boolean isEmpty(){
        boolean result = true;

        for (int x = 0; x <= Coordinates.fieldSize; x++) {
            for (int y = 0; y <= Coordinates.fieldSize; y++) {
                if (vMatrix[x][y]!=0) result=false;
            }
        }
        Log.d("Matrix", "isEmpty: "+result);
        return result;
    }



}
