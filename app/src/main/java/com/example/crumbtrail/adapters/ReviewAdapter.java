package com.example.crumbtrail.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crumbtrail.FoodReviewActivity;
import com.example.crumbtrail.R;
import com.example.crumbtrail.data.model.Food;
import com.example.crumbtrail.data.model.Review;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.parceler.Parcels;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    Context context;
    List<Review> Reviews;
    TextView userNameTv;
    TextView reviewBodyTv;
    FloatingActionButton commentBtn;
    FloatingActionButton favoriteBtn;
    FloatingActionButton shareBtn;
    EditText composeEt;
    TextView favoriteCountTv;

    public void clear() {
        Reviews.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Review> list) {
        Reviews.addAll(list);
        notifyDataSetChanged();
    }

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.Reviews = reviews;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("ReviewAdapter", "onCreateViewHolder");
        View FoodView = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(FoodView);
    }

    // Involves populating data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("ReviewAdapter", "onBindViewHolder" + position);
        // Get the Food at the passed in position
        Review review = Reviews.get(position);
        // Bind the Food data into the VH
        holder.bind(review);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return Reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            userNameTv = itemView.findViewById(R.id.userNameTv);
            reviewBodyTv = itemView.findViewById(R.id.reviewBodyTv);
            commentBtn = itemView.findViewById(R.id.commentBtn);
            composeEt = itemView.findViewById(R.id.composeEt);
            favoriteBtn = itemView.findViewById(R.id.favoriteBtn);
            shareBtn = itemView.findViewById(R.id.shareBtn);
            favoriteCountTv = itemView.findViewById(R.id.favoriteCountTv);

            // itemView's onclick listener
            itemView.setOnClickListener(this);

        }

        public void bind(Review review) {
            userNameTv.setText(review.getUser().getUsername());
            reviewBodyTv.setText(review.getBody());
            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            favoriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
