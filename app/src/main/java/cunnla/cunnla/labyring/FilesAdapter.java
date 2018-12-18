package cunnla.cunnla.labyring;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesAdapter<File> extends ArrayAdapter<File> {

    private ArrayList<File> list;
   // private Context mContext;

    TextView tvFileName;

    public FilesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<File> list) {
        super(context, 0, list);

      this.list = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        File currentFile = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_files, parent, false);
        }


        String filePath = currentFile.toString();
        String fileName = filePath.substring(filePath.lastIndexOf("/")+1);

        tvFileName = (TextView) convertView.findViewById(R.id.tvFileName);
        tvFileName.setText(fileName);


        return convertView;

    }

}
