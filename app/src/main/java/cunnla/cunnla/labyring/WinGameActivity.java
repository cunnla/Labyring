package cunnla.cunnla.labyring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinGameActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_game);


        buttonOK = (Button)findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(this);

        this.setFinishOnTouchOutside(false);
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }


}