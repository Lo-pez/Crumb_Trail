package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.crumbtrail.data.model.Food;
import com.example.crumbtrail.data.model.Review;
import com.example.crumbtrail.databinding.ActivityComposeBinding;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;


public class ComposeActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 42;
    public static final String TAG = "ComposeActivity";
    public static final int MAX_REVIEW_LENGTH = 200;

    EditText etComposeScr;
    Button btnTweet;
    RatingBar ratingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComposeBinding binding = ActivityComposeBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        Food food = Parcels.unwrap(intent.getParcelableExtra("food"));

        btnTweet = binding.btnReview;
        etComposeScr = binding.etComposeScr;
        ratingBar = binding.ratingBar;

        etComposeScr.setText(food.getBrandName());

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewContent = etComposeScr.getText().toString();
                if (reviewContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "Sorry, your review cannot be empty.",Toast.LENGTH_LONG).show();
                    return;
                }
                if (reviewContent.length() > MAX_REVIEW_LENGTH) {
                    Toast.makeText(ComposeActivity.this, "Sorry, your review is too long.",Toast.LENGTH_LONG).show();
                    return;
                }

                float rating = ratingBar.getRating();
                ProgressBar progressBar = binding.load;
                progressBar.setVisibility(View.VISIBLE);
                Review review = new Review();
                review.setBody(reviewContent);
                review.setUser(ParseUser.getCurrentUser());
                review.setLikedBy(new ArrayList<>());
                review.setComments(new ArrayList<>());
                review.setRating(rating);
                review.setFCDId(food.getFCDID());
                review.saveInBackground(e -> {
                    if (e != null) {
                        Log.e(TAG, "Error while saving new post!", e);
                        Toast.makeText(ComposeActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "Post save was successful!");
                });
                progressBar.setVisibility(View.GONE);
                finish();
            }
        });
    }
}