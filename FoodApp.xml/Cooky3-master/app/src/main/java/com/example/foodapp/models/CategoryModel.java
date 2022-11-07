package com.example.foodapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryModel implements Parcelable {
    String idCategory, strCategory, strCategoryThumb, strCategoryDescription;

    public CategoryModel(String idCategory, String strCategory, String strCategoryThumb) {
        this.idCategory = idCategory;
        this.strCategory = strCategory;
        this.strCategoryThumb = strCategoryThumb;
    }

    public CategoryModel(String idCategory, String strCategory, String strCategoryThumb, String strCategoryDescription) {
        this.idCategory = idCategory;
        this.strCategory = strCategory;
        this.strCategoryThumb = strCategoryThumb;
        this.strCategoryDescription = strCategoryDescription;
    }

    public CategoryModel(CategoryModel categoryModel) {
        this.idCategory = categoryModel.idCategory;
        this.strCategory = categoryModel.strCategory;
        this.strCategoryThumb = categoryModel.strCategoryThumb;
        this.strCategoryDescription = categoryModel.strCategoryDescription;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strCategory);
        dest.writeString(strCategoryDescription);
        dest.writeString(strCategoryThumb);
    }
    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    protected CategoryModel(Parcel in) {
        strCategory = in.readString();
        strCategoryDescription = in.readString();
        strCategoryThumb = in.readString();
    }
}
