package cunnla.cunnla.labyring;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LoadMap extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button loadMap;
    DrawView drawView;
    TextView getFiles;

    ListView filesListView;
    ArrayList<File> filesArrayList;

    int[][] vMatrix = new int[7][7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_map);

        loadMap = (Button)findViewById(R.id.loadMap);
        loadMap.setOnClickListener(this);

        drawView = new DrawView(this);
        filesListView = (ListView)findViewById(R.id.filesListView);
        filesListView.setOnItemClickListener(this);



        // ======= creating the list of files to choose from  =========

        filesArrayList = new ArrayList<File>();


        getFiles = (TextView)findViewById(R.id.getFiles);
        getFiles.setText("");
        Log.d("Files", "Files list: " + getFilesDir().listFiles()[0].getName()+", "+getFilesDir().listFiles()[1].getName());


        String s = "Files list: ";
        if (getFilesDir().listFiles() != null && getFilesDir().listFiles().length > 0) {
            for (File myFile : getFilesDir().listFiles()) {
                filesArrayList.add(myFile);
                s += myFile.getName() + ", ";
            }
        }
        getFiles.setText(s);
        FilesAdapter<File> filesAdapter = new FilesAdapter<File>(this, android.R.layout.simple_list_item_1,filesArrayList);
        filesListView.setAdapter(filesAdapter);
    }

    @Override       // if we click on a file
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Log.d("Files", "itemClick: position = " + position + ", id = " + id);
        File selectedFile = (File) parent.getAdapter().getItem(position); // getting our object File from position in ListView
        String fileName = selectedFile.toString().substring(selectedFile.toString().lastIndexOf("/")+1);
        Log.d("Files", "itemClick: file name = " + fileName);
// ==============  load a map
        try {
            FileInputStream fin = openFileInput(fileName);
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

        Log.d("Files", "Putting vMatrix to file: vMatrix[3][5]" + vMatrix[5][3]);

        Bundle bundle = new Bundle();
        bundle.putSerializable("vMatrix",vMatrix);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();

    }



    public void onClick(View v){
        switch (v.getId()) {
            case R.id.loadMap:
                try {
                    FileInputStream fin = openFileInput("map2");
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
