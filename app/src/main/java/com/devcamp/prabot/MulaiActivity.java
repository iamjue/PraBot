package com.devcamp.prabot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MulaiActivity extends AppCompatActivity {
    Button btnGo;
    Spinner spinnerHuruf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai);

        spinnerHuruf = (Spinner)findViewById(R.id.spinner);
        btnGo = (Button)findViewById(R.id.btn_go);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pilih Sandi Huruf"+spinnerHuruf.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
