package com.example.crumbtrail.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crumbtrail.R;

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";
    public static final int requestCode = 100;
    final int RequestCameraPermissionID = 1001;


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

        ImageButton submitSearchBtn = view.findViewById(R.id.submitSearchBtn);

        EditText searchBarEt = view.findViewById(R.id.searchBarEt);

        submitSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchBarEt.getText().toString().equals("")) {
                    Toast.makeText(getActivity(),"Do not search an empty query!",Toast.LENGTH_SHORT).show();
                    return;
                }
                newInstance(searchBarEt.getText().toString()).show(getChildFragmentManager(), SearchResultsFragment.TAG);
            }
        });
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