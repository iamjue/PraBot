package com.devcamp.prabot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.devcamp.prabot.R;

public class HelpActivity extends AppCompatActivity {
    CardView cardView;
    Button btnSelesai;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_help);

        cardView = (CardView) findViewById(R.id.cardview_help);

        btnSelesai = (Button) findViewById(R.id.btn_selesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}
