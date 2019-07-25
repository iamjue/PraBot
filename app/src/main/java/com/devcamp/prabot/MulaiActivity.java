package com.devcamp.prabot;

import android.app.ProgressDialog;
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


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MulaiActivity extends AppCompatActivity {
    Button btnGo;
    Spinner spinnerHuruf;
    ProgressDialog pDialog;
    SpinnerAdapter adapter;
    List<DataSandiItem> dataSandiItem = new ArrayList<>();
    TextView tv;


    public static final String url = "http://all.3jnc.tech/imanika/api/sandi_data.php";

    private static final String TAG = MulaiActivity.class.getSimpleName();

    public static final String TAG_HURUF = "huruf";

    String pilih;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai);

        spinnerHuruf = (Spinner) findViewById(R.id.spinner);
        btnGo = findViewById(R.id.btn_go);


        spinnerHuruf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                pilih = dataSandiItem.get(position).huruf;
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
                Toast.makeText(MulaiActivity.this,"Pilihan anda : "+pilih, Toast.LENGTH_SHORT).show();
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
