package com.db.alan.dbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class StartCsc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.startstudy);

        findViewById(R.id.main1).setOnClickListener(myClick);
        findViewById(R.id.main2).setOnClickListener(myClick);
        findViewById(R.id.main3).setOnClickListener(myClick);
        findViewById(R.id.main4).setOnClickListener(myClick);
        findViewById(R.id.main5).setOnClickListener(myClick);
        findViewById(R.id.main6).setOnClickListener(myClick);
        findViewById(R.id.main7).setOnClickListener(myClick);

        Button.OnClickListener myClick = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.main1:
                        startActivity(new Intent(StartCsc.this),ActivityStudy.class);
                        break;
                    case R.id.main2:
                        startActivity(new Intent(StartCsc.this),ActivityStudy3.class);
                        break;
                    case R.id.main3:
                        startActivity(new Intent(StartCsc.this),ActivityStud4.class);
                        break;
                    case R.id.main4:
                        startActivity(new Intent(StartCsc.this),ActivityStud5.class);
                        break;
                    case R.id.main5:
                        startActivity(new Intent(StartCsc.this),ActivityStudy2.class);
                        break;
                    case R.id.main6:
                        startActivity(new Intent(StartCsc.this),About.class);
                        break;
                    case R.id.main7:
                        finish();
                        break;
                }
            }
        }
    }
}
