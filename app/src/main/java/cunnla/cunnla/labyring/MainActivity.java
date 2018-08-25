package cunnla.cunnla.labyring;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton buttonUp;
    ImageButton buttonDown;
    ImageButton buttonRight;
    ImageButton buttonLeft;

    Canvas canvas;
    DrawView drawView;

    private static final String TAG = "myLogs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUp = (ImageButton)findViewById(R.id.buttonUp);
        buttonUp.setOnClickListener(this);
        buttonDown = (ImageButton)findViewById(R.id.buttonDown);
        buttonDown.setOnClickListener(this);
        buttonLeft = (ImageButton)findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(this);
        buttonRight = (ImageButton)findViewById(R.id.buttonRight);
        buttonRight.setOnClickListener(this);


        drawView = new DrawView(this);
        drawView = (DrawView)findViewById(R.id.drawView);

        canvas = new Canvas();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonUp:
                drawView.moveUp(canvas);
                break;
            case R.id.buttonDown:
                drawView.moveDown(canvas);
                break;
            case R.id.buttonLeft:
                drawView.moveLeft(canvas);
                break;
            case R.id.buttonRight:
                drawView.moveRight(canvas);
                break;
        }

        if (drawView.winGame()) {
            Log.d(TAG, "YOU WIN!!! main");
            Intent intent = new Intent(this, WinGameActivity.class);
            startActivityForResult(intent, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        Log.d(TAG, "Got Result!");
        drawView.startAgain();
    }


}