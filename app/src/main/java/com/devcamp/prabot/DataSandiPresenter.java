package com.devcamp.prabot;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataSandiPresenter{
    MainView mainView;
    StringRequest stringRequest;
    ApiServer server;
    Context context;

    public DataSandiPresenter(MainView mainView, ApiServer server, Context context) {
        this.mainView = mainView;
        this.server = server;
        this.context = context;
    }

    public MainView getMainView() {
        return mainView;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public StringRequest getStringRequest() {
        return stringRequest;
    }

    public void setStringRequest(StringRequest stringRequest) {
        this.stringRequest = stringRequest;
    }

    public ApiServer getServer() {
        return server;
    }

    public void setServer(ApiServer server) {
        this.server = server;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public void LoadDataSandi() {

        final ArrayList<DataSandiItem> dataSandiItems = new ArrayList<>();
        String URL = server.getDataSandi();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray sandiArray = object.getJSONArray("sandi_data");
                    for (int i = 0; i < sandiArray.length(); i++) {
                        JSONObject sandiObject = sandiArray.getJSONObject(i);
                        DataSandiItem dataSandiItem = new DataSandiItem(

                        );


                        dataSandiItems.add(dataSandiItem);

                    }


                    mainView.showSandi(dataSandiItems);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
