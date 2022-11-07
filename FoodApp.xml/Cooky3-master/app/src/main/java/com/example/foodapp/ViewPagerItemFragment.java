package com.example.foodapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodapp.models.CategoryModel;
import com.example.foodapp.recyclerViews.MealsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ViewPagerItemFragment extends Fragment {
    public static final String TAG = "ViewPagerItemFragment";

    private TextView mCategoryName, mDesc;

    RecyclerView mealsRecy;
    MealsRecyclerAdapter mealsAdapter;

    ArrayList<CategoryModel> mealsArrayList;
    private CategoryModel mCategoryModel;

    public static ViewPagerItemFragment getInstance(CategoryModel categoryModel){
        ViewPagerItemFragment fragment = new ViewPagerItemFragment();
        if(categoryModel != null){
            Bundle bundle = new Bundle();
            bundle.putParcelable("category", categoryModel);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CategoryModel CurrentCat = getArguments().getParcelable("category");
        int mNum = Integer.parseInt(CurrentCat.getIdCategory());
        if(getArguments() != null){
//            mCategoryModel = getArguments().getParcelable("category");
            mCategoryModel = MainActivity.categoriesArrayList.get(mNum-1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_array_list_categories_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mCategoryName = view.findViewById(R.id.tv_category_name_fragment);
        mDesc = view.findViewById(R.id.tv_category_description_details);
        init(view);
    }

    private void init(View v){
        if(mCategoryModel != null){
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            mCategoryName.setText(mCategoryModel.getStrCategory());
            mDesc.setText(mCategoryModel.getStrCategoryDescription());
            mealsRecy = v.findViewById(R.id.recy_meals_category_details);
            mealsArrayList = new ArrayList<>();

            getMealsByCategory(mCategoryModel.getStrCategory());
//            CategoryDetailsActivity.mTabLayout.getTabAt(
//                    CategoryDetailsActivity.mTabLayout.getSelectedTabPosition()).setText(mCategoryModel.getStrCategory());

        }
    }

    public void getMealsByCategory(String categoryName){

        StringRequest request = new StringRequest(0, "https://www.themealdb.com/api/json/v1/1/filter.php?c=" + categoryName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray meals = jsonObject.getJSONArray("meals");

                    for (int i = 0; i < meals.length(); i++) {
                        JSONObject meal = meals.getJSONObject(i);
                        mealsArrayList.add(new CategoryModel(
                                meal.getString("idMeal"),
                                meal.getString("strMeal"),
                                meal.getString("strMealThumb"))
                        );

                        mealsAdapter = new MealsRecyclerAdapter(mealsArrayList, getContext());
                        mealsRecy.setAdapter(mealsAdapter);
                        mealsRecy.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

                        mealsAdapter.setOnClick(new Consumer<CategoryModel>() {
                            @Override
                            public void accept(CategoryModel categoryModel) {
                                Intent intent = new Intent(getContext() , DishDetailsActivity.class);
                                intent.putExtra("mealID", categoryModel.getIdCategory());
                                startActivity(intent);
                            }
                        });
                        Log.e(TAG, "onResponse: " + mealsArrayList.get(i).getStrCategory());
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onREsponse: " + e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onREsponse: " + error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }
}














