package com.example.crumbtrail.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.crumbtrail.ComposeActivity;
import com.example.crumbtrail.R;
import com.example.crumbtrail.data.model.Food;
import com.example.crumbtrail.data.model.Review;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
// ...

public class ComposeReviewDialogFragment extends DialogFragment {

    private static final String TAG = "ComposeReviewDialogFragment";
    public static final int MAX_REVIEW_LENGTH = 100;

    public ComposeReviewDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ComposeReviewDialogFragment newInstance(String title) {
        ComposeReviewDialogFragment frag = new ComposeReviewDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        EditText etComposeScr = view.findViewById(R.id.etComposeScr);
        Button btnReview = view.findViewById(R.id.btnReview);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ProgressBar progressBar = view.findViewById(R.id.load);
        Food food = Parcels.unwrap(getArguments().getParcelable("food"));

//        Objects.requireNonNull(getDialog()).setTitle(title);
        // Show soft keyboard automatically and request focus to field
        etComposeScr.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewContent = etComposeScr.getText().toString();
                if (reviewContent.isEmpty()) {
                    Toasty.success(requireContext(), "Sorry, your review cannot be empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (reviewContent.length() > MAX_REVIEW_LENGTH) {
                    Toasty.error(requireContext(), "Sorry, your review is too long.",Toast.LENGTH_LONG).show();
                    return;
                }

                Float rating =  ratingBar.getRating();
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
                        Toasty.error(requireContext(), "Error while saving!", Toasty.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "Post save was successful!");
                });
                progressBar.setVisibility(View.GONE);
                dismiss();
            }
        });
    }
}