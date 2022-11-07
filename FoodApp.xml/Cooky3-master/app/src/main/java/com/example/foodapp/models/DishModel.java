package com.example.foodapp.models;

import java.util.ArrayList;

public class DishModel {
    String idMeal, strMeal, strCategory, strArea;
    String instructions, mealThumb, youtube;
    ArrayList<String> ingredients;
    ArrayList<String> measures;

    public DishModel(String idMeal, String strMeal, String mealThumb) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.mealThumb = mealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getMealThumb() {
        return mealThumb;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getMeasures() {
        return measures;
    }

    public void setMeasures(ArrayList<String> measures) {
        this.measures = measures;
    }
}
