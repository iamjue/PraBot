package com.devcamp.prabot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplasActivity extends AppCompatActivity {
    private int waktu_loading=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Home = new Intent(SplasActivity.this, LoginActivity.class);
                startActivity(Home);
                finish();
            }
        },waktu_loading);
    }
}