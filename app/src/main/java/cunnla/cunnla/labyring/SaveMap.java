package cunnla.cunnla.labyring;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileOutputStream;

public class SaveMap extends AppCompatActivity implements View.OnClickListener {

    Canvas canvas;
    DrawView drawView;

    Button saveMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_map);

        saveMap = (Button)findViewById(R.id.saveMap);
        saveMap.setOnClickListener(this);

        // === draw some map ====
        drawView = new DrawView(this);
        drawView = (DrawView)findViewById(R.id.drawView);
        drawView.reset();
        drawView.invalidate();

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.saveMap:
                try {
                    ////   ===== map1 =====
  //                  FileOutputStream fOut = openFileOutput("map1", Context.MODE_PRIVATE);
//
 //                   int[][] vMatrix = new int[][]{{0, 0, 0, 2, 0, 0, 0},
 //                                                 {0, 4, 0, 1, 1, 1, 0},
  //                                                {0, 1, 0, 0, 0, 1, 0},
  //                                                {0, 1, 1, 1, 1, 1, 0},
   //                                               {0, 1, 0, 0, 0, 0, 0},
   //                                               {0, 1, 1, 1, 1, 4, 0},
   //                                               {0, 0, 0, 3, 0, 0, 0}};

                    ////   ===== map2 =====
                    FileOutputStream fOut = openFileOutput("map2", Context.MODE_PRIVATE);

                    int[][] vMatrix = new int[][]{{0, 0, 0, 0, 0, 2, 0},
                                                  {0, 1, 1, 1, 1, 1, 0},
                                                  {0, 1, 0, 0, 0, 0, 0},
                                                  {0, 1, 0, 1, 1, 1, 0},
                                                  {0, 1, 0, 0, 0, 4, 0},
                                                  {0, 1, 1, 1, 1, 1, 0},
                                                  {0, 0, 0, 0, 0, 3, 0}};

                    for (int x = 0; x <= Coordinates.fieldSize; x++) {
                        String mResult = "";
                        for (int y = 0; y <= Coordinates.fieldSize; y++) {
                            fOut.write(vMatrix[x][y]);
                            mResult += vMatrix[x][y];
                        }
                        Log.d("Files", "Write file: " + mResult);
                    }
                    fOut.flush();
                    fOut.close();
                } catch (Exception e) {
                    Log.e("Files", e.getMessage());
                    e.printStackTrace();
                }

                break;
        }
    }
}
