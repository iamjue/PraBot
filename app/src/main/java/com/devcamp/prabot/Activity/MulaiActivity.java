package com.devcamp.prabot.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.devcamp.prabot.App.AppController;
import com.devcamp.prabot.Data.DataSandiItem;
import com.devcamp.prabot.Data.DataUpdateItem;
import com.devcamp.prabot.R;
import com.devcamp.prabot.Adapter.SpinnerAdapter;
import com.devcamp.prabot.Server.ApiServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MulaiActivity extends AppCompatActivity {
    Button btnGo;
    Spinner spinnerHuruf;
    ProgressDialog pDialog;
    SpinnerAdapter adapter;
    List<DataSandiItem> dataSandiItem = new ArrayList<>();
    TextView tvOutput;
    ImageView imgGambar;
    WebView web;


    private static final String TAG = MulaiActivity.class.getSimpleName();
    Handler handler = new Handler();
    Runnable refresh;

    String huruf, drajatX, drajatY, gambar, xhuruf, xdrajatX, xdrajatY, xgambar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai);

        spinnerHuruf = findViewById(R.id.spinner);
        btnGo = findViewById(R.id.btn_go);
        imgGambar = findViewById(R.id.img_gambar);
        tvOutput = findViewById(R.id.tv_output);
        web = findViewById(R.id.web);

        web.loadUrl(ApiServer.server + "sandi_android.php");
        web.setWebViewClient(new WebViewClient());


        spinnerHuruf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                huruf = dataSandiItem.get(position).getHuruf();
                drajatX = dataSandiItem.get(position).getDerajat_lengan_x();
                drajatY = dataSandiItem.get(position).getDerajat_lengan_y();
                gambar = dataSandiItem.get(position).getGambar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        adapter = new SpinnerAdapter(MulaiActivity.this, dataSandiItem);
        spinnerHuruf.setAdapter(adapter);


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Add();

            }
        });
        callDataSpinner();
        refresh = new Runnable() {
            public void run() {
                // Do something
                handler.postDelayed(refresh, 1000);
                Update();

            }
        };
        handler.post(refresh);

    }


    private void callDataSpinner() {
        dataSandiItem.clear();

        pDialog = new ProgressDialog(MulaiActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();

        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(ApiServer.server + "sandi_data.php",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                DataSandiItem item = new DataSandiItem();


                                item.setHuruf(obj.getString("huruf"));
                                item.setDerajat_lengan_x(obj.getString("derajat_lengan_x"));
                                item.setDerajat_lengan_y(obj.getString("derajat_lengan_y"));
                                item.setGambar(obj.getString("gambar"));

                                dataSandiItem.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MulaiActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);

    }

    private void Add() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiServer.server + "update_android.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Showing response message coming from server.

                        String resetKiri = "Reset Left";
                        String resetKanan = "Reset Right";
                        if (ServerResponse.toString().trim().equals("Semaphore Flags Reset Right")) {
                            tvOutput.setText(resetKanan);
                            Toast.makeText(MulaiActivity.this, resetKanan, Toast.LENGTH_SHORT).show();
                        } else if (ServerResponse.toString().trim().equals("Semaphore Flags Reset Left")) {
                            tvOutput.setText(resetKiri);
                            Toast.makeText(MulaiActivity.this, resetKiri, Toast.LENGTH_SHORT).show();
                        } else {
                            tvOutput.setText(ServerResponse.toString().trim());
                            Toast.makeText(MulaiActivity.this, ServerResponse.toString().trim(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.


                        // Showing error message if something goes wrong.
                        Toast.makeText(MulaiActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("huruf", huruf);
                params.put("derajat_lengan_x", drajatX);
                params.put("derajat lengan_y", drajatY);
                params.put("gambar", gambar);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MulaiActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    private void Update() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiServer.server + "sandi_android.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//
                try {
                    JSONObject responseObject = new JSONObject(response);
                    xhuruf = responseObject.getJSONArray("sandi_android").getJSONObject(0).getString("huruf");
                    xdrajatX = responseObject.getJSONArray("sandi_android").getJSONObject(0).getString("derajat_lengan_x");
                    xdrajatY = responseObject.getJSONArray("sandi_android").getJSONObject(0).getString("derajat_lengan_y");
                    xgambar = responseObject.getJSONArray("sandi_android").getJSONObject(0).getString("gambar");


                } catch (Exception e) {
                    e.printStackTrace();
                }
                imgGambar.setImageResource(R.drawable.semaphore_positions);
                String xy = "0";
                if (xdrajatX.toString().equals(xy) && xdrajatY.toString().equals(xy)) {
                    Glide.with(MulaiActivity.this).load(xgambar).into(imgGambar);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
