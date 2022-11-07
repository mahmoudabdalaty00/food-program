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
import com.example.foodapp.models.DishModel;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LatestDishsRecyclerAdapter extends RecyclerView.Adapter<LatestDishsRecyclerAdapter.viewHolder> {
    ArrayList<DishModel> dishesArrarList;
    Context context;
    Consumer<DishModel> onClick;

    public void setOnClick(Consumer<DishModel> onClick){
        this.onClick = onClick;
    }

    public LatestDishsRecyclerAdapter(ArrayList<DishModel> dishesArrarList, Context context) {
        this.dishesArrarList = dishesArrarList;
        this.context = context;
    }

    @NonNull
    @Override
    public LatestDishsRecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hor_recy_item, parent, false);
        return new LatestDishsRecyclerAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dishName.setText(dishesArrarList.get(position).getStrMeal());
        holder.thumbnail.layout(0,0,0,0);
        Glide.with(context).load(dishesArrarList.get(position).getMealThumb()).into(holder.thumbnail);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClick != null){
                    onClick.accept(dishesArrarList.get(position));
                }
            }

        });
    }

    @Override
    public int getItemCount() {

        return dishesArrarList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView dishName;
        ImageView thumbnail;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.tv_food_name_card);
            thumbnail = itemView.findViewById(R.id.img_dish_card);
        }
    }
}
