package com.devcamp.prabot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.devcamp.prabot.R;

public class AboutAppActivity extends AppCompatActivity {
    CardView cardView;
    Button btnBio, btnSelesai;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        cardView = (CardView)findViewById(R.id.cv_aboutApp);

        btnSelesai = (Button)findViewById(R.id.btn_selesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutAppActivity.this, HomeActivity.class));
                finish();
            }
        });

        btnBio = (Button)findViewById(R.id.btn_biodata);
        btnBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutAppActivity.this, AboutBioActivity.class));
                finish();
            }
        });
    }
}
