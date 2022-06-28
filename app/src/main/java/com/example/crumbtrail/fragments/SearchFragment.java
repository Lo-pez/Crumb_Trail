package com.example.crumbtrail.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.crumbtrail.R;
import com.example.crumbtrail.adapters.FoodAdapter;
import com.example.crumbtrail.data.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class SearchFragment extends DialogFragment {
    public String FOOD_URL;
    List<Food> foods;
    private LinearLayoutManager linearLayoutManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String mQuery = getArguments().getString("query");
        FOOD_URL = String.format("https://api.nal.usda.gov/fdc/v1/foods/search?api_key=%s" , getString(R.string.food_api_key)) + "&query=" + mQuery + "&dataType=Branded&pageSize=3&pageNumber=1&sortBy=dataType.keyword&sortOrder=asc";
        Log.i(TAG, FOOD_URL);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_search, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        RecyclerView foodRv = view.findViewById(R.id.foodRv);
        foods = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());

        foodRv.setLayoutManager(linearLayoutManager);
        FoodAdapter foodAdapter = new FoodAdapter(getContext(), foods);
        foodRv.setAdapter(foodAdapter);

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
                    foodAdapter.notifyItemRangeChanged(0, Food.fromJsonArray(results).size());
                    Log.i(TAG, "foods: " + foods.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure" + throwable);
            }
        });

        return builder.create();
    }
    public static String TAG = "SearchFragment";
}