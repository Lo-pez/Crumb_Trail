package com.example.crumbtrail.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.crumbtrail.R;
import com.example.crumbtrail.adapters.FoodAdapter;
import com.example.crumbtrail.adapters.ReviewAdapter;
import com.example.crumbtrail.data.model.Food;
import com.example.crumbtrail.data.model.Review;
import com.example.crumbtrail.databinding.ActivityReviewFeedBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Headers;

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";
    public static final int requestCode = 100;
    final int RequestCameraPermissionID = 1001;
    private LinearLayoutManager linearLayoutManager;
    private Handler handler = new Handler();
    private Runnable runnable;
    protected FoodAdapter foodAdapter;
    protected List<Food> foods;
    private RecyclerView searchRv;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchView searchView = view.findViewById(R.id.searchView);

        searchRv = view.findViewById(R.id.searchRv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        searchRv.setLayoutManager(linearLayoutManager);
        foods = new ArrayList<>();
        foodAdapter = new FoodAdapter(getContext(), foods);
        searchRv.setAdapter(foodAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Remove all previous callbacks.
                handler.removeCallbacks(runnable);

                runnable = () -> {
                    String searchNameQuery = searchView.getQuery().toString();
                    Log.i(TAG, searchNameQuery);
                    queryFDC(searchNameQuery);
                    Log.i(TAG, foods.toString());
                };
                handler.postDelayed(runnable, 750);

                return false;
            }
        });
    }

    public void queryFDC(String mQuery) {
        String FOOD_URL = String.format("https://api.nal.usda.gov/fdc/v1/foods/search?api_key=%s" , requireContext().getString(R.string.food_api_key) ) + "&query=" + mQuery + "&dataType=Branded&sortBy=dataType.keyword&sortOrder=asc";
        Log.i(TAG, FOOD_URL);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(FOOD_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("foods");
                    Log.i(TAG, "Results: " + results.toString());
                    foods.addAll(Food.fromJsonArray(results));
                    Log.i(TAG, "foods: " + foods.size());
                    foodAdapter.notifyItemRangeChanged(0, foods.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure" + throwable);
            }
        });
    }
}