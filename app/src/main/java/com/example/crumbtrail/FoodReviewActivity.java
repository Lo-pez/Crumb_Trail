package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.crumbtrail.data.model.Food;

public class FoodReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_review);

        Intent intent = getIntent();
        String fdcid = intent.getStringExtra("FDCID");

    }
}