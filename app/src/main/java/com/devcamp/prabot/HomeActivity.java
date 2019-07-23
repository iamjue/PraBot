package com.devcamp.prabot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnMulai, btnHelp, btnAbout, btnExit;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_home);

        btnMulai = (Button) findViewById(R.id.btn_mulai);
        btnMulai.setOnClickListener(this);

        btnHelp = (Button) findViewById(R.id.btn_help);
        btnHelp.setOnClickListener(this);

        btnAbout = (Button) findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(this);

        btnExit = (Button) findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                finish();
                System.exit(0);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mulai:
                Intent intentMulai = new Intent(HomeActivity.this, MulaiActivity.class);
                startActivity(intentMulai);
                break;
            case R.id.btn_help:
                Intent intentHelp = new Intent(HomeActivity.this, HelpActivity.class);
                startActivity(intentHelp);
                break;
            case R.id.btn_about:
                Intent intentAbout = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                break;

        }

    }
}
