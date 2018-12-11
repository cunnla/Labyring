package cunnla.cunnla.labyring;

import android.util.Log;

public class Coordinates {

    int x,y;
    final static int pixelRate = 90;
    final static int fieldSize = 6;  // count starting from 0, so the actual number of cells is fieldSize+1
    // the pixel field size is pixelRate x fieldSize+1, and it should be no more than 630 (or so)

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }


   public int coordToPx (int i){
       i = i*pixelRate;
       return i;
   }


    public String toString(){
        String result = x+","+y;
        return result;
    }

    public boolean equals(Coordinates c){
       if ((this.x==c.x)&&(this.y==c.y)) {
           return true;
       } else return false;
    }


}
