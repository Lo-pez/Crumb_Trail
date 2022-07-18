package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.crumbtrail.data.model.Food;

import org.parceler.Parcels;

public class FoodReviewActivity extends AppCompatActivity {

    private static final String TAG = "FoodReviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_food_review);
        Food food = (Food) Parcels.unwrap(getIntent().getParcelableExtra("Food"));


    }
}