package cunnla.cunnla.labyring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinGameActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonOK;
    TextView tv;
    Intent intent;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_game);


        buttonOK = (Button)findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(this);
        tv = (TextView)findViewById(R.id.tv);

        this.setFinishOnTouchOutside(false);  // so that this window is not closed on touch outside it

        intent = getIntent();
        tv.setText(intent.getStringExtra("game"));
    }

    public void onClick(View v) {
        setResult(RESULT_OK, intent);
        finish();
    }


}