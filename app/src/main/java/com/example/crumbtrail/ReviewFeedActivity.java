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
import android.widget.TextView;

import com.example.crumbtrail.adapters.ReviewAdapter;
import com.example.crumbtrail.data.model.Food;
import com.example.crumbtrail.data.model.Review;
import com.example.crumbtrail.databinding.ActivityReviewFeedBinding;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewFeedActivity extends AppCompatActivity{
    public static final String TAG = "FeedActivity";
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
        setUpSwipeContainer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Food food = Parcels.unwrap(intent.getParcelableExtra("Food"));
        Long fdcId = food.getFDCID();
        Objects.requireNonNull(getSupportActionBar()).setTitle(fdcId.toString());


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
        Log.i(TAG, fdcId.toString());
        queryReviews(fdcId);

        setUpEndlessScrolling(fdcId);

        // initialize the array that will hold Reviews and create a ReviewsAdapter
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setUpEndlessScrolling(Long fdcId) {
        // Retain an instance so that you can call `resetState()` for fresh searches
        // Triggered only when new data needs to be appended to the list
        // Add whatever code is needed to append new items to the bottom of the list
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                queryReviews(fdcId);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvReviews.addOnScrollListener(scrollListener);
    }

    private void setUpSwipeContainer() {
        swipeContainer = (SwipeRefreshLayout) binding.reviewSwipeContainer;
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            adapter.clear();
            queryReviews(null);
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    private void queryReviews(Long fdcId) {
        // specify what type of data we want to query - Review.class
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        // include data referred by user key
        query.include(Review.KEY_FCDID);
        query.include(Review.KEY_USER);
        if (fdcId != null)
            query.whereEqualTo(Review.KEY_FCDID, fdcId.toString());
        // limit query to latest 20 items
        query.setLimit(20);
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
