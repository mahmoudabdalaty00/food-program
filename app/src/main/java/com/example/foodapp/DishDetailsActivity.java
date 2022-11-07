package com.example.foodapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.foodapp.models.DishModel;
import com.example.foodapp.recyclerViews.IngreidentsRecyclerAdapter;
import com.example.foodapp.recyclerViews.LatestDishsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

public class DishDetailsActivity extends AppCompatActivity {

    public static final String TAG = "DishDetailsActivity";
    TextView tvMealName, tvCategory, tvCountry;
    TextView tvInstructions, tvIngrediants;
    TextView tvYoutube, tvSource;
    ImageView imgMeal;

    ArrayList<String> ingredients;
    ArrayList<String> measures;



    RecyclerView recyIngrediants;
    RecyclerView recyMeasures;
    IngreidentsRecyclerAdapter ingreidentsRecyclerAdapter1;
    IngreidentsRecyclerAdapter ingreidentsRecyclerAdapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);

        tvMealName = findViewById(R.id.tv_dish_name_detail);
        tvCategory = findViewById(R.id.tv_dish_category_details);
        tvCountry = findViewById(R.id.tv_dish_country_details);

        tvInstructions = findViewById(R.id.tv_instructions_details);
       // ingrediantsList = findViewById(R.id.list_ingrediants_details);
        recyIngrediants = findViewById(R.id.recy_ingredients_dish_details);
        recyMeasures = findViewById(R.id.recy_measures_dish_details);

        tvYoutube = findViewById(R.id.tv_youtube_details);
        tvSource = findViewById(R.id.tv_source_details);
        imgMeal = findViewById(R.id.img_dish_details);

        ingredients = new ArrayList<>();
        measures = new ArrayList<>();


        String mealID = getIntent().getStringExtra("mealID");
        getMealDetailById(mealID);

    }

    public void getMealDetailById(String id){
        StringRequest request = new StringRequest(0, "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id,
                new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray meals = jsonObject.getJSONArray("meals");
                    JSONObject meal = meals.getJSONObject(0);

                    String strMeal = meal.getString("strMeal");
                    String strCategory = meal.getString("strCategory");
                    String strArea = meal.getString("strArea");

                    String strInstructions = meal.getString("strInstructions");
                    String strMealThumb = meal.getString("strMealThumb");

                    String strYoutube = meal.getString("strYoutube");
                    String strSource = meal.getString("strSource");

                    int i=1;
                    while(i<20 && (!meal.getString("strIngredient"+ i).isEmpty() || !meal.getString("strIngredient"+ i).equals(" "))){
                        ingredients.add(meal.getString("strIngredient"+ i));
                        ingreidentsRecyclerAdapter1 = new IngreidentsRecyclerAdapter(ingredients, DishDetailsActivity.this);
                        recyIngrediants.setAdapter(ingreidentsRecyclerAdapter1);
                        recyIngrediants.setLayoutManager(new LinearLayoutManager(DishDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
                        i++;
                    }
                    int k=1;
                    while(k<20 && (!meal.getString("strMeasure"+ k).isEmpty() || !meal.getString("strMeasure"+ k).equals(" "))){
                        measures.add(meal.getString("strMeasure"+ k));
                        ingreidentsRecyclerAdapter2 = new IngreidentsRecyclerAdapter(measures, DishDetailsActivity.this);
                        recyMeasures.setAdapter(ingreidentsRecyclerAdapter2);
                        recyMeasures.setLayoutManager(new LinearLayoutManager(DishDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
                        k++;
                    }


                    Glide.with(DishDetailsActivity.this).load(strMealThumb).into(imgMeal);
                    tvMealName.setText(strMeal);
                    tvCategory.setText(strCategory);
                    tvCountry.setText(strArea);
                    tvInstructions.setText(strInstructions);

                    tvYoutube.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity((new Intent(Intent.ACTION_VIEW, Uri.parse(strYoutube))));
                        }
                    });

                    tvSource.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity((new Intent(Intent.ACTION_VIEW, Uri.parse(strSource))));
                        }
                    });




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