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
import com.example.foodapp.R;
import com.example.foodapp.models.CategoryModel;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MealsRecyclerAdapter extends RecyclerView.Adapter<MealsRecyclerAdapter.ViewHolder> {
    ArrayList<CategoryModel> mealsArrayList;
    Context context;
    Consumer<CategoryModel> onClick;

    public void setOnClick(Consumer<CategoryModel> onClick){
        this.onClick = onClick;
    }

    public MealsRecyclerAdapter(ArrayList<CategoryModel> categoriesArrayList, Context context) {
        this.mealsArrayList = categoriesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mealName.setText(mealsArrayList.get(position).getStrCategory());
        holder.thumbnail.layout(0,0,0,0);
        Glide.with(context).load(mealsArrayList.get(position).getStrCategoryThumb()).into(holder.thumbnail);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClick != null){
                    onClick.accept(mealsArrayList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.tv_meal_name_card);
            thumbnail = itemView.findViewById(R.id.img_meal_card);
        }
    }
}
