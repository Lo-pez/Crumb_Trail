package com.example.crumbtrail.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the food object. The data used here is obtained from the FCD API.
 */
@Parcel
public class Food {
    private long fdcID;
    private String description;
    private String brandName;
    private String foodCategory;

    public long getFCDID() { return fdcID; }

    public String getDescription() { return description; }

    public String getBrandName() { return brandName; }

    public String getFoodCategory() { return foodCategory; }

    // empty constructor needed by the Parceler library
    public Food() {
    }

    public Food(JSONObject food) throws JSONException {
        brandName = food.getString("brandOwner");
        foodCategory = food.getString("foodCategory");
        description = food.getString("description");
        fdcID = food.getLong("fdcId");
    }

    public static List<Food> fromJsonArray(JSONArray foodJsonArray) throws JSONException {
        List<Food> foods = new ArrayList<>();
        for (int i = 0; i < foodJsonArray.length(); ++i) {
            foods.add(new Food(foodJsonArray.getJSONObject(i)));
        }
        return foods;
    }
}