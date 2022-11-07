package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.models.DishModel;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<String> dishModels;
    public ListViewAdapter(ArrayList<String> dishModels, Context context) {
        this.dishModels = dishModels;
        inflater = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return dishModels.size();
    }

    @Override
    public Object getItem(int position) {
        return dishModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_listview, null);

        TextView ingredient = convertView.findViewById(R.id.tv_list_item);
        ingredient.setText(dishModels.get(i));

        return convertView;
    }
}
