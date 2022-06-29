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

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";
    public static final int requestCode = 100;
    final int RequestCameraPermissionID = 1001;
    List<Food> foods;
    private LinearLayoutManager linearLayoutManager;
    private Handler handler = new Handler();
    private Runnable runnable;


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

        RecyclerView searchRv = view.findViewById(R.id.searchRv);
        foods = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());

        searchRv.setLayoutManager(linearLayoutManager);
        FoodAdapter foodAdapter = new FoodAdapter(getContext(), foods);
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
                    foods = queryFDC(searchNameQuery);
                    Log.i(TAG, foods.toString());
                    foodAdapter.notifyItemRangeChanged(0, foods.size());
                };
                handler.postDelayed(runnable, 750);

                return false;
            }
        });

//        RxSearchView.queryTextChanges(searchView)
//                .debounce(1, TimeUnit.SECONDS) // stream will go down after 1 second inactivity of user
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<CharSequence>() {
//                    @Override
//                    public void accept(CharSequence mQuery) throws Throwable {
//                        if (mQuery.length() > 0) {
//                            String query = (String) mQuery;
//                            foods = (ArrayList<Food>) Utils.queryFDC(query);
//                            foodAdapter.notifyItemRangeChanged(0, foods.size());
//                        }
//                    }
//                });

//        ImageButton submitSearchBtn = view.findViewById(R.id.submitSearchBtn);
//
//        EditText searchBarEt = view.findViewById(R.id.searchBarEt);

//        submitSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (searchBarEt.getText().toString().equals("")) {
//                    Toast.makeText(getActivity(),"Do not search an empty query!",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                newInstance(searchBarEt.getText().toString()).show(getChildFragmentManager(), SearchResultsFragment.TAG);
//            }
//        });
    }

    public List<Food> queryFDC(String mQuery) {
        String FOOD_URL = String.format("https://api.nal.usda.gov/fdc/v1/foods/search?api_key=%s" , getContext().getString(R.string.food_api_key) ) + "&query=" + mQuery + "&dataType=Branded&sortBy=dataType.keyword&sortOrder=asc";
        List foods = new ArrayList<>();
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
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure" + throwable);
            }
        });
        return foods;
    }

    public static SearchResultsFragment newInstance(String query) {
        SearchResultsFragment f = new SearchResultsFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("query", query);
        f.setArguments(args);

        return f;
    }
}