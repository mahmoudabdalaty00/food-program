package com.example.foodapp.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.models.CategoryModel;

import java.util.ArrayList;

public class IngreidentsRecyclerAdapter extends RecyclerView.Adapter<IngreidentsRecyclerAdapter.ViewHolder> {
    ArrayList<String> data;
    Context context;

    public IngreidentsRecyclerAdapter(ArrayList<String> data, Context context) {
        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public IngreidentsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngreidentsRecyclerAdapter.ViewHolder holder, int position) {
        holder.tvIngrediant.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngrediant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIngrediant = itemView.findViewById(R.id.tv_list_item);
        }
    }
}
