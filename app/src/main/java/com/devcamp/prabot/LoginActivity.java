package com.devcamp.prabot;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btnLogin;
    TextInputLayout etUsername, etPassword;


    int success;
    ConnectivityManager conMgr;

    private String url = "http://all.3jnc.tech/imanika/api/login.php";

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ProgressBar loading;


    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

//        conMgr = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );
//        {
//            if (conMgr.getActiveNetworkInfo() != null
//                    && conMgr.getActiveNetworkInfo().isAvailable()
//                    && conMgr.getActiveNetworkInfo().isConnected()) {
//            } else {
//                Toast.makeText( getApplicationContext(), "No Internet Connection",
//                        Toast.LENGTH_LONG ).show();
//            }
//        }
        loading = findViewById(R.id.loading_in);

        btnLogin = (Button) findViewById( R.id.btn_login );

        etUsername = findViewById( R.id.edt_username );
        etPassword =findViewById( R.id.edt_password );

        sessionManager = new SessionManager(this);



        btnLogin.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = etUsername.getEditText().getText().toString().trim();
                String password = etPassword.getEditText().getText().toString().trim();

                if (!username.isEmpty() || !password.isEmpty()) {
                    login(username, password);

                } else {
                    etUsername.setError("Mohon Masukkan Email");
                    etPassword.setError("Mohon Masukkan Password");
                }


            }
        } );
    }

    private void login(final String username, final String password) {
        loading.setVisibility(View.VISIBLE);
//        btnLogin.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("username").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id_user").trim();

                                    Toast.makeText(LoginActivity.this,
                                            "Success Login. \nYour Name : "
                                                    + name + "\nYour Email : "
                                                    + email, Toast.LENGTH_SHORT)
                                            .show();

                                    sessionManager.createSession(name, email, id);

                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    intent.putExtra("username", name);
                                    intent.putExtra("email", email);
                                    //intent.putExtra("id", id);
                                    startActivity(intent);

                                    loading.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Login Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btnLogin.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Login Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("username", username);
                param.put("password", password);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }


}
