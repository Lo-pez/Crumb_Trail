package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.crumbtrail.data.model.Food;
import com.example.crumbtrail.data.model.Review;
import com.example.crumbtrail.databinding.ActivityComposeBinding;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class ComposeActivity extends AppCompatActivity {

    public static final String TAG = "ComposeActivity";
    public static final int MAX_REVIEW_LENGTH = 100;

    EditText etComposeScr;
    Button btnReview;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityComposeBinding binding = ActivityComposeBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        Food food = Parcels.unwrap(intent.getParcelableExtra("food"));

        btnReview = binding.btnReview;
        etComposeScr = binding.etComposeScr;
        ratingBar = binding.ratingBar;

        btnReview.setOnClickListener(view1 -> {
            String reviewContent = etComposeScr.getText().toString();
            if (reviewContent.isEmpty()) {
                Toasty.warning(ComposeActivity.this, "Sorry, your review cannot be empty.", Toast.LENGTH_LONG).show();
                return;
            }
            if (reviewContent.length() > MAX_REVIEW_LENGTH) {
                Toasty.warning(ComposeActivity.this, "Sorry, your review is too long.",Toast.LENGTH_LONG).show();
                return;
            }

            Float rating = ratingBar.getRating();
            ProgressBar progressBar = binding.load;
            progressBar.setVisibility(View.VISIBLE);
            Review review = new Review();
            review.setBody(reviewContent);
            review.setUser(ParseUser.getCurrentUser());
            review.setLikedBy(new ArrayList<>());
            review.setRating(rating);
            review.setFCDId(food.getFCDID());
            review.saveInBackground(e -> {
                if (e != null) {
                    Log.e(TAG, "Error while saving new post!", e);
                    Toasty.error(ComposeActivity.this, "Error while saving!", Toasty.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
            });
            progressBar.setVisibility(View.GONE);
            finish();
        });
    }
}