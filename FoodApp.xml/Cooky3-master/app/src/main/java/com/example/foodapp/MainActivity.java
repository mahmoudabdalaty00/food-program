package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodapp.models.CategoryModel;
import com.example.foodapp.models.DishModel;
import com.example.foodapp.recyclerViews.CategoriesRecyclerAdapter;
import com.example.foodapp.recyclerViews.LatestDishsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    EditText etSearch;
    RecyclerView latestHorRecy;
    RecyclerView categoriesRecy;

    CategoriesRecyclerAdapter categoriesRecyclerAdapter;
    LatestDishsRecyclerAdapter latestDishsRecyclerAdapter;

    ArrayList<DishModel> dishesArrayList;
    public static ArrayList<CategoryModel> categoriesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latestHorRecy = findViewById(R.id.hor_recy_latest_dishes_home);
        categoriesRecy = findViewById(R.id.recy_categories_home);
        etSearch = findViewById(R.id.et_search_home);

        dishesArrayList = new ArrayList<>();
        categoriesArrayList = new ArrayList<>();

        getCategories();
        getLatestDishs();
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OnSearchActivity.class));

            }
        });

    }

    public void getLatestDishs(){
        StringRequest request = new StringRequest(0, "https://www.themealdb.com/api/json/v2/9973533/latest.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray meals = jsonObject.getJSONArray("meals");
                    Log.e(TAG, "onResonse:  reach array done!!!!!!!!");
                    for (int i = 0; i < meals.length(); i++) {
                        JSONObject meal = meals.getJSONObject(i);
                        dishesArrayList.add(new DishModel(
                                meal.getString("idMeal"),
                                meal.getString("strMeal"),
                                meal.getString("strMealThumb"))
                        );
                        latestDishsRecyclerAdapter = new LatestDishsRecyclerAdapter(dishesArrayList, MainActivity.this);
                        latestHorRecy.setAdapter(latestDishsRecyclerAdapter);
                        latestHorRecy.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

                        latestDishsRecyclerAdapter.setOnClick(new Consumer<DishModel>() {
                            @Override
                            public void accept(DishModel dishModel) {
                                Intent intent = new Intent(MainActivity.this , DishDetailsActivity.class);
                                intent.putExtra("mealID", dishModel.getIdMeal());
                                startActivity(intent);
                            }
                        });
                        Log.e(TAG, "onResponse: " + dishesArrayList.get(i).getStrMeal());
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onREsponse: " + e.getLocalizedMessage() + "catch of getLatestDishes()");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onREsponse: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    public void getCategories(){
        StringRequest request = new StringRequest(0, "https://www.themealdb.com/api/json/v1/1/categories.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray categories = jsonObject.getJSONArray("categories");

                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject category = categories.getJSONObject(i);
                        categoriesArrayList.add(new CategoryModel(
                                category.getString("idCategory"),
                                category.getString("strCategory"),
                                category.getString("strCategoryThumb"),
                                category.getString("strCategoryDescription"))
                        );

                        categoriesRecyclerAdapter = new CategoriesRecyclerAdapter(categoriesArrayList, MainActivity.this);
                        categoriesRecy.setAdapter(categoriesRecyclerAdapter);
                       // categoriesRecy.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        categoriesRecy.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));

                        categoriesRecyclerAdapter.setOnClick(new Consumer<CategoryModel>() {
                            @Override
                            public void accept(CategoryModel categoryModel) {
                                Intent intent = new Intent(MainActivity.this , CategoryDetailsActivity.class);
                                intent.putExtra("categoryName", categoryModel.getStrCategory());
                                intent.putExtra("strCategoryDescription", categoryModel.getStrCategoryDescription());
                                Log.e(TAG, "accept(): "+categoryModel.getStrCategoryDescription());

                                intent.putExtra("categoryID", categoryModel.getIdCategory());

                                startActivity(intent);
                            }
                        });
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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }


}