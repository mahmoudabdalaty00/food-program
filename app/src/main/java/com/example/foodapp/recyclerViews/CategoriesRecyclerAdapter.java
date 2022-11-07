package com.example.foodapp.recyclerViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.CategoryDetailsActivity;
import com.example.foodapp.models.CategoryModel;
import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder> {
    ArrayList<CategoryModel> categoriesArrayList;
    Context context;
    Consumer<CategoryModel> onClick;

    public void setOnClick(Consumer<CategoryModel> onClick){
        this.onClick = onClick;
    }

    public CategoriesRecyclerAdapter(ArrayList<CategoryModel> categoriesArrayList, Context context) {
        this.categoriesArrayList = categoriesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.categoryName.setText(categoriesArrayList.get(position).getStrCategory());
        holder.thumbnail.layout(0,0,0,0);
        Glide.with(context).load(categoriesArrayList.get(position).getStrCategoryThumb()).into(holder.thumbnail);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClick != null){
                    onClick.accept(categoriesArrayList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.tv_category_name_card);
            thumbnail = itemView.findViewById(R.id.img_category_card);
        }
    }
}
