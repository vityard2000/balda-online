package com.tems.baldaonline;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "myTag";

    TextView tv1;
    Button bt1;
    Button bt2;
    Button bt_to_activity_two;
    Toast toast_bt1;
    Toast toast_bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.activity_main__tv_1);
        bt1 = findViewById(R.id.activity_main__bt_1);
        bt2 = findViewById(R.id.activity_main__bt_2);
        bt_to_activity_two = findViewById(R.id.activity_main__bt_to_activity_two);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt_to_activity_two.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Stop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroy");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_main__bt_1:
                //tv1.setText("кнопка 1");
                Toast.makeText(this, "соханение", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.activity_main__bt_2:
                //tv1.setText("кнопка 2");
                Toast.makeText(this, "загрузка", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_main__bt_to_activity_two:
                Intent intent = new Intent(this, ActivityTwo.class);
                startActivity(intent);
                break;
        }
    }
}

