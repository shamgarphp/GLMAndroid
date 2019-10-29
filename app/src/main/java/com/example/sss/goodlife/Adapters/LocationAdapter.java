package com.example.sss.goodlife.Adapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.goodlife.Models.Locations;
import com.example.sss.goodlife.R;

import java.util.ArrayList;

public class LocationAdapter extends ArrayAdapter<Locations> {
    private final Context context;
    private final ArrayList<Locations> values;

    public LocationAdapter(Context context, ArrayList<Locations> values) {
        super(context, R.layout.spinner_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public Locations getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.spinner_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(values.get(position).getArea());
        // Change icon based on name
        return rowView;
    }

    @Override
    public View getDropDownView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.spinner_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(values.get(position).getArea());
        // Change icon based on name
        return rowView;
    }
}