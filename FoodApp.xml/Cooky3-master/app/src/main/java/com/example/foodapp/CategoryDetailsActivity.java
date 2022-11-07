package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodapp.models.CategoryModel;
import com.example.foodapp.models.DishModel;
import com.example.foodapp.recyclerViews.CategoriesRecyclerAdapter;
import com.example.foodapp.recyclerViews.LatestDishsRecyclerAdapter;
import com.example.foodapp.recyclerViews.MealsRecyclerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CategoryDetailsActivity extends AppCompatActivity {
    public static final String TAG = "CategoryDetailsActivity";
    RecyclerView mealsRecy;

    ArrayList<CategoryModel> mealsArrayList;
    ViewPager viewPager;
    public static TabLayout mTabLayout;


    TextView tvCategoryDescription;

    public static int pos = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        mealsRecy = findViewById(R.id.recy_meals_category_details);
        tvCategoryDescription = findViewById(R.id.tv_category_description_details);
        viewPager= findViewById(R.id.pager);
        mTabLayout= findViewById(R.id.tab_layout);

        mealsArrayList = new ArrayList<>();

        pos = Integer.parseInt(getIntent().getStringExtra("categoryID"));


        loopingOnCategories();


    }

    private void loopingOnCategories(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<CategoryModel> categories = MainActivity.categoriesArrayList;

        for(CategoryModel category: categories){
            ViewPagerItemFragment fragment = ViewPagerItemFragment.getInstance(category);
            fragments.add(fragment);

        }
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(pos-1);
        mTabLayout.setupWithViewPager(viewPager, true);

    }
}