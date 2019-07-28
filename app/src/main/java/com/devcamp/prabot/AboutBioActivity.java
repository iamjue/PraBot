package com.devcamp.prabot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class AboutBioActivity extends AppCompatActivity {

    Button btnKembali;
    CardView cardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_bio);

        cardView = (CardView) findViewById(R.id.cv_biodata);

        btnKembali = (Button) findViewById(R.id.btn_kembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutBioActivity.this, AboutAppActivity.class));
                finish();
            }
        });
    }
}