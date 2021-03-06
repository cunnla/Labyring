package cunnla.cunnla.labyring;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    SensorManager sensorManager;
    Sensor sensor;
    long lastUpdate = 0;  //making the app work slower


    Canvas canvas;
    DrawView drawView;

    LinearLayout llDrawView, llDrawButtons, v;

    Button saveGame;
    Button loadGame;
    Button startAgain;
    SharedPreferences sPref;

    private static final String TAG = "myLogs";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);


        saveGame = (Button)findViewById(R.id.saveGame);
        saveGame.setOnClickListener(this);
        loadGame = (Button)findViewById(R.id.loadGame);
        loadGame.setOnClickListener(this);
        startAgain = (Button)findViewById(R.id.startAgain);
        startAgain.setOnClickListener(this);


        drawView = new DrawView(this);
        drawView.reset();
        drawView = (DrawView)findViewById(R.id.drawView);
        v = (LinearLayout) findViewById(R.id.v);
        llDrawView = (LinearLayout) findViewById(R.id.llDrawView);
        llDrawButtons = (LinearLayout) findViewById(R.id.llDrawButtons);
        if (drawView.isEmpty()) {
            llDrawButtons.removeAllViews();
            llDrawView.removeAllViews();
        }

        canvas = new Canvas();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, 1, 1,"Draw a map");
        menu.add(0, 2, 2,"Load a map");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Intent intent;
        switch (item.getItemId()){
            case 1:   // Draw and Save
                intent = new Intent(this, SaveMap.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, LoadMap.class);
                startActivityForResult(intent, 2);
                break;

        }

        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 1:  // WinGame or Explode
                drawView.startAgain();
                break;
            case 2:  // Load Map
               // Log.d("Files", "Load Map: Got here");
                llDrawButtons.removeAllViews();
                llDrawView.removeAllViews();

                Bundle bundle = data.getExtras();
                drawView.vMatrix = (int[][])bundle.getSerializable("vMatrix");

                //Log.d("Files", "Load Map: Got here");
                llDrawView.addView(drawView);
                llDrawButtons.addView(saveGame);
                llDrawButtons.addView(loadGame);
                llDrawButtons.addView(startAgain);
                drawView.invalidate();
                break;


        }
    }

        @Override
        public void onClick (View v){

            switch (v.getId()) {
                case R.id.saveGame:
                    sPref = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putInt("hero.x", drawView.hero.x);
                    ed.putInt("hero.y", drawView.hero.y);
                    ed.commit();
                    Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.loadGame:
                    sPref = getPreferences(MODE_PRIVATE);
                    drawView.hero.x = sPref.getInt("hero.x", drawView.heroStart.x);
                    drawView.hero.y = sPref.getInt("hero.y", drawView.heroStart.y);
                    Toast.makeText(this, "Data loaded", Toast.LENGTH_SHORT).show();
                    drawView.invalidate();
                    break;
                case R.id.startAgain:
                    drawView.startAgain();
                    break;

            }
        }


        @Override
        public void onSensorChanged (SensorEvent event){
            Log.d(TAG, "Sensor");
            Log.d(TAG, event.values[0] + " " + event.values[1] + " " + event.values[2]);


            long actualTime = event.timestamp;  //making the app work slower
            final int sensorValue = 1;

            if (actualTime - lastUpdate > 200000000) { //making the app work slower

                if (event.values[0] < -sensorValue) {
                    drawView.moveRight(canvas);
                }
                if (event.values[0] > sensorValue) {
                    drawView.moveLeft(canvas);
                }
                if (event.values[1] < sensorValue) {
                    drawView.moveUp(canvas);
                }
                if (event.values[1] > sensorValue) {
                    drawView.moveDown(canvas);
                }
                lastUpdate = actualTime; //making the app work slower

            }


            if (drawView.winGame()) {
                Log.d(TAG, "YOU WIN!!! main");
                Intent intent = new Intent(this, WinGameActivity.class);
                intent.putExtra("game", "Congratulations! You won the game!");
                startActivityForResult(intent, 1);
            }

            if (drawView.explode()) {
                Log.d(TAG, "Explode!!");
                Intent intent = new Intent(this, WinGameActivity.class);
                intent.putExtra("game", "Ooops! You lost the game!");
                startActivityForResult(intent, 1);
            }


        }

        @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){

        }

        @Override
        protected void onResume () {
            super.onResume();
            sensorManager.registerListener(this, sensor, 1000000);
            Log.d(TAG, "On resume");
        }

        @Override
        protected void onPause () {
            super.onPause();
            sensorManager.unregisterListener(this);
            Log.d(TAG, "On pause");
        }



  }