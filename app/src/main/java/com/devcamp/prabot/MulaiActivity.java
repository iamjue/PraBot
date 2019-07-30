package com.devcamp.prabot;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class MulaiActivity extends AppCompatActivity {
    Button btnGo;
    Spinner spinnerHuruf;
    ProgressDialog pDialog;
    SpinnerAdapter adapter;
    List<DataSandiItem> dataSandiItem = new ArrayList<>();
    TextView tv;
    Context context;

    public static final String url = "http://all.3jnc.tech/prabot/api/sandi_data.php";
    public static final String urlAdd = "http://all.3jnc.tech/prabot/api/insert.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    int success;
    private static final String TAG = MulaiActivity.class.getSimpleName();

    public static final String TAG_HURUF = "huruf";

    String pilih, drajatX, drajatY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai);

        spinnerHuruf = (Spinner) findViewById(R.id.spinner);
        btnGo = findViewById(R.id.btn_go);

        tv = findViewById(R.id.txt);
        spinnerHuruf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                pilih = dataSandiItem.get(position).huruf;
                drajatX = dataSandiItem.get(position).derajat_lengan_x;
                drajatY = dataSandiItem.get(position).derajat_lengan_y;
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
                Toast.makeText(MulaiActivity.this, "Pilihan anda : " + pilih, Toast.LENGTH_SHORT).show();
                Add();
                //tv.setText(pilih + " " + drajatX + " " + drajatY);
                tv.setText(pilih + " ");
            }
        });
        callData();

    }

    private void callData() {
        dataSandiItem.clear();

        pDialog = new ProgressDialog(MulaiActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();

        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                DataSandiItem item = new DataSandiItem();


                                item.setHuruf(obj.getString(TAG_HURUF));
                                item.setDerajat_lengan_x(obj.getString("derajat_lengan_x"));
                                item.setDerajat_lengan_y(obj.getString("derajat_lengan_y"));

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

//               StringRequest strReq = new StringRequest(Request.Method.POST, urlAdd, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Response: " + response.toString());
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    success = jObj.getInt(TAG_SUCCESS);
//
//                    // Cek error node pada json
//                    if (success == 1) {
//                        Log.d("Add", jObj.toString());
//
//
//                        Toast.makeText(MulaiActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
//
//                    } else {
//                        Toast.makeText(MulaiActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Error: " + error.getMessage());
//                Toast.makeText(MulaiActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams(){
//
//                Map<String, String> params = new HashMap<String, String>();
//                // jika id kosong maka simpan, jika id ada nilainya maka update
//
//                params.put("huruf", "A");
//                params.put("derajat_lengan_x", "90");
//                params.put("derajat_lengan_y", "90");
//
//                return params;
//            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//
//                headers.put("huruf", pilih);
//                headers.put("derajat_lengan_x", drajatX);
//                headers.put("derajat_lengan_y", drajatY);
//
//
//                return headers;
//            }
//
//        };
//        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
//       / RequestQueue requestQueue = Volley.newRequestQueue(MulaiActivity.this);

        // Adding the StringRequest object into requestQueue.
//        requestQueue.add(strReq);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlAdd,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.


                        // Showing response message coming from server.
                        Toast.makeText(MulaiActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
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
                params.put("huruf", pilih);
                params.put("derajat_lengan_x", drajatX);
                params.put("derajat lengan_y", drajatY);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MulaiActivity.this);

        // Adding the StringRequest object into requestQueue.
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
