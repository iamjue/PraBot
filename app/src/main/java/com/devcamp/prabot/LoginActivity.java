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
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Button btnLogin;
    TextInputLayout tvl_username, tvl_pass;
    Intent intent;

    int success;
    ConnectivityManager connectivityManager;

    private String url_login = "http://all.3jnc.tech/imanika/api/login.php";

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedPreferences;
    Boolean session = false;
    String id, username;
    public  static final  String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInatanceState) {
        super.onCreate(savedInatanceState);
        setContentView(R.layout.activity_login);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (connectivityManager.getActiveNetworkInfo()!=null
            && connectivityManager.getActiveNetworkInfo().isAvailable()
            && connectivityManager.getActiveNetworkInfo().isConnected()){
            }else {
                Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        }
        btnLogin = (Button)findViewById(R.id.btn_login);
        tvl_username = (TextInputLayout)findViewById(R.id.edt_username);
        tvl_pass = (TextInputLayout)findViewById(R.id.edt_password);

        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session =sharedPreferences.getBoolean(session_status,false);
        id = sharedPreferences.getString(TAG_USERNAME, null);

        if (session){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tvl_username.getEditText().getText().toString();
                String password = tvl_pass.getEditText().getText().toString();

                if (username.trim().length() > 0 && password.trim().length() > 0 ){
                    if (connectivityManager.getActiveNetworkInfo()!=null
                    && connectivityManager.getActiveNetworkInfo().isAvailable()
                    && connectivityManager.getActiveNetworkInfo().isConnected()){
                    }else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Kolom Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private  void checkLogin (final  String username, final String password){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loging in ..");
        showDialog();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response : " + response.toString());
                hideDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        String username = jsonObject.getString(TAG_USERNAME);
                        String id = jsonObject.getString(TAG_ID);

                        Log.e("Successfully Login", jsonObject.toString());

                        Toast.makeText(getApplicationContext(), jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id);
                        editor.putString(TAG_USERNAME, username);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra(TAG_ID, id);
                        intent.putExtra(TAG_USERNAME, username);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString(TAG_MESSAGE),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error" + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
        @Override
            protected Map<String, String> getParams(){
            Map<String, String> params  = new HashMap<String, String>();
            params.put("username", username);
            params.put("password", password);

            return params;
        }
    };
    AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

}
