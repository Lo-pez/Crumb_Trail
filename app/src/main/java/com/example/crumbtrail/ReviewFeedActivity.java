package com.example.crumbtrail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.crumbtrail.adapters.ReviewAdapter;
import com.example.crumbtrail.data.model.Food;
import com.example.crumbtrail.data.model.Review;
import com.example.crumbtrail.databinding.ActivityReviewFeedBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewFeedActivity extends AppCompatActivity{
    public static final String TAG = "FeedActivity";
    private static final int REQUEST_CODE = 42;
    protected ReviewAdapter adapter;
    protected List<Review> allReviews;
    private ActivityReviewFeedBinding binding;
    private RecyclerView rvReviews;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        Food food = Parcels.unwrap(intent.getParcelableExtra("Food"));
        setUpSwipeContainer(food);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        long fdcId = food.getFCDID();
        Objects.requireNonNull(getSupportActionBar()).setTitle(food.getBrandName());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        rvReviews = binding.reviewsRv;
        linearLayoutManager = new LinearLayoutManager(this);
        rvReviews.setLayoutManager(linearLayoutManager);
        allReviews = new ArrayList<>();
        adapter = new ReviewAdapter(this, allReviews);
        rvReviews.setAdapter(adapter);
        Log.i(TAG, Long.toString(fdcId));
        queryReviews(food);

        FloatingActionButton btnOpenReview = binding.btnOpenReview;

        btnOpenReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnOpenReview) {
                    // Compose icon has been selected
                    // Navigate to the compose activity
                    Intent intent = new Intent(ReviewFeedActivity.this, ComposeActivity.class);
                    intent.putExtra("food", Parcels.wrap(food));
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });

        setUpEndlessScrolling(food);
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setUpEndlessScrolling(Food food) {
        // Retain an instance so that you can call `resetState()` for fresh searches
        // Triggered only when new data needs to be appended to the list
        // Add whatever code is needed to append new items to the bottom of the list
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                queryReviews(food);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvReviews.addOnScrollListener(scrollListener);
    }

    private void setUpSwipeContainer(Food food) {
        swipeContainer = (SwipeRefreshLayout) binding.reviewSwipeContainer;
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            adapter.clear();
            queryReviews(food);
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    private void queryReviews(Food food) {
        // specify what type of data we want to query - Review.class
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        // include data referred by user key
//        query.findInBackground(new FindCallback<Review>() {
//            @Override
//            public void done(List<Review> objects, ParseException e) {
//                for (int i = 0; i < objects.size(); ++i) {
//                    if (objects.get(i).getFCDId().equals(food.getFDCID()))
//                        allReviews.add(0, objects.get(i));
//                }
//            }
//        });
//        ParseObject obj = ParseObject.createWithoutData(Review.KEY_FCDID, String.valueOf(food.getFDCID()));
        query.whereEqualTo(Review.KEY_FCDID, String.valueOf(food.getFCDID()));
        query.include(Review.KEY_FCDID);
        query.include(Review.KEY_USER);
        // order Reviews by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for Reviews
        query.findInBackground((Reviews, e) -> {
            // check for errors
            if (e != null) {
                Log.e(TAG, "Issue with getting Reviews", e);
                return;
            }

            // for debugging purposes let's print every Review description to logcat
            for (Review Review : Reviews) {
                Log.i(TAG, "Review: " + Review.getBody());
            }

            // save received Reviews to list and notify adapter of new data
            allReviews.addAll(Reviews);
            adapter.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);
        });
    }
}
