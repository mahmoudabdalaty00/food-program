package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodapp.models.DishModel;
import com.example.foodapp.recyclerViews.LatestDishsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

public class OnSearchActivity extends AppCompatActivity {
    public static final String TAG = "OnSearchActivity";
    RecyclerView searchRecy;
    LatestDishsRecyclerAdapter recyclerAdapter;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_search);

        searchRecy = findViewById(R.id.search_recy);
        etSearch = findViewById(R.id.et_search_OnSearch);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchMeals(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void searchMeals(String word){
        ArrayList<DishModel> searchMealsArrayList = new ArrayList<>();

        StringRequest request = new StringRequest(0, "https://www.themealdb.com/api/json/v1/1/search.php?s="+ word,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray meals = jsonObject.getJSONArray("meals");
                    Log.e(TAG,"reach array done");
                    for (int i = 0; i < meals.length(); i++) {

                        JSONObject meal = meals.getJSONObject(i);
                        Log.e(TAG,"inside loop done");
                        Log.e(TAG,meal.getString("strMeal")+" print strMeal");

                        if(meal.getString("strMeal").toLowerCase().contains(word)){
                            searchMealsArrayList.add(new DishModel(
                                    meal.getString("idMeal"),
                                    meal.getString("strMeal"),
                                    meal.getString("strMealThumb"))
                            );
                        }
                        //Log.e(TAG, "arrayList : ************************************** "+ searchMealsArrayList.get(i));
                        recyclerAdapter = new LatestDishsRecyclerAdapter(searchMealsArrayList, OnSearchActivity.this);
                        searchRecy.setAdapter(recyclerAdapter);
                        searchRecy.setLayoutManager(new LinearLayoutManager(OnSearchActivity.this, LinearLayoutManager.VERTICAL, false));

                        recyclerAdapter.setOnClick(new Consumer<DishModel>() {
                            @Override
                            public void accept(DishModel dishModel) {
                                Intent intent = new Intent(OnSearchActivity.this , DishDetailsActivity.class);
                                intent.putExtra("mealID", dishModel.getIdMeal());
                                startActivity(intent);
                            }
                        });
                        Log.e(TAG, "onResponse: " + searchMealsArrayList.get(i).getStrMeal());
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onREsponse: " + e.getLocalizedMessage()+ "  catch  on search");
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