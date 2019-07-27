package com.devcamp.prabot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnMulai, btnHelp, btnAbout, btnExit;
    String id, username;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    String getId;
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
        btnExit.setOnClickListener(this);


        sharedpreferences = getSharedPreferences( LoginActivity.my_shared_preferences, Context.MODE_PRIVATE );
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
            case R.id.btn_exit:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean( LoginActivity.session_status, false );
                editor.putString( TAG_ID, null );
                editor.putString( TAG_USERNAME, null );
                editor.commit();

                Intent intent = new Intent( HomeActivity.this, LoginActivity.class );
                finish();
                startActivity( intent );
                break;

        }

    }
}
