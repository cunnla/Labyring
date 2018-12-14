package cunnla.cunnla.labyring;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoadMap extends AppCompatActivity implements View.OnClickListener {

    Button loadMap;
    DrawView drawView;
    Canvas canvas;

    int[][] vMatrix = new int[7][7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_map);

        loadMap = (Button)findViewById(R.id.loadMap);
        loadMap.setOnClickListener(this);

        drawView = new DrawView(this);
        //canvas = new Canvas();


    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.loadMap:
                try {
                    FileInputStream fin = openFileInput("map1");
                    for (int x=0; x<=Coordinates.fieldSize; x++) {
                        String mResult = "";
                        for (int y=0; y<=Coordinates.fieldSize; y++) {
                            vMatrix[x][y] = fin.read();
                            mResult+=vMatrix[x][y];
                        }
                        Log.d("Files", "Read file: " + mResult);
                    }
                    fin.close();
                } catch (FileNotFoundException e) {
                    Log.e("Files", e.getMessage());
                } catch (IOException e) {
                    Log.e("Files", e.getMessage());
                }
                break;
        }

        Log.d("Files", "Putting vMatrix to file: vMatrix[3][6]" + vMatrix[6][3]);

        Bundle bundle = new Bundle();
        bundle.putSerializable("vMatrix",vMatrix);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
