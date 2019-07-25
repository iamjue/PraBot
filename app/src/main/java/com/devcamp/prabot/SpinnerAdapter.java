package com.devcamp.prabot;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataSandiItem> dataSandiItems;

    public SpinnerAdapter(Activity activity, List<DataSandiItem> dataSandiItems) {
        this.activity = activity;
        this.dataSandiItems = dataSandiItems;
    }

    @Override
    public int getCount() {
        return dataSandiItems.size();
    }

    @Override
    public Object getItem(int location) {
        return dataSandiItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_datasandi, null);

        TextView dataSandi = (TextView) convertView.findViewById(R.id.tv_spinner);

        DataSandiItem data;
        data = dataSandiItems.get(position);

        dataSandi.setText(data.getHuruf() +" - X = "+data.derajat_lengan_x+" - Y = "+data.derajat_lengan_y );

        return convertView;
    }
}
