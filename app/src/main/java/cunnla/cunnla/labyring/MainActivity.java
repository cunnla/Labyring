package cunnla.cunnla.labyring;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParser;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;

    Canvas canvas;
    DrawView drawView;

    private static final String TAG = "myLogs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        drawView = new DrawView(this);
        drawView = (DrawView)findViewById(R.id.drawView);

        canvas = new Canvas();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "Sensor");

        int p0 = (int)event.values[0];
        int p1 = (int)event.values[1];
        int p2 = (int)event.values[2];

        Log.d(TAG, p0 + " "+p1+" "+p2);

        if (p2<-10){ drawView.moveRight(canvas);}
        if (p2>10) { drawView.moveLeft(canvas);}
        if (p1>2) { drawView.moveUp(canvas);}
        if (p1<-10) { drawView.moveDown(canvas);}

        if (drawView.winGame()) {
            Log.d(TAG, "YOU WIN!!! main");
            Intent intent = new Intent(this, WinGameActivity.class);
            intent.putExtra("game", "Congratulations! You won the game!");
            startActivityForResult(intent, 1);
        }

        if (drawView.explode()) {
            Log.d(TAG, "Expload!!");
            Intent intent = new Intent(this, WinGameActivity.class);
            intent.putExtra("game", "Ooops! You lost the game!");
            startActivityForResult(intent, 1);
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "On resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        Log.d(TAG, "On pause");
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        Log.d(TAG, "Got Result!");
        drawView.startAgain();
    }



}