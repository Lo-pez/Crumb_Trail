package com.example.crumbtrail.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crumbtrail.R;
import com.example.crumbtrail.data.model.Review;
import com.parse.ParseUser;

import java.util.List;

/**
 * Adapter for each review displayed when a user clicks on a queried
 * food item.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private static final String TAG = "ReviewAdapter";
    final Context context;
    final List<Review> Reviews;
    TextView userNameTv;
    TextView reviewBodyTv;
    RatingBar displayRatingBar;
    FrameLayout commentFl;
    FrameLayout favoriteFl;
    FrameLayout shareFl;
    EditText composeEt;
    TextView favoriteCountTv;
    ConstraintLayout composeCl;
    ImageView favoriteIv;

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
            commentFl = itemView.findViewById(R.id.commentFl);
            composeEt = itemView.findViewById(R.id.composeEt);
            favoriteFl = itemView.findViewById(R.id.favoriteFl);
            favoriteIv = itemView.findViewById(R.id.favoriteIv);
            shareFl = itemView.findViewById(R.id.shareFl);
            favoriteCountTv = itemView.findViewById(R.id.favoriteCountTv);
            composeCl = itemView.findViewById(R.id.composeCl);
            displayRatingBar = itemView.findViewById(R.id.displayRatingBar);

            // itemView's onclick listener
            itemView.setOnClickListener(this);

        }

        public void bind(Review review) {
            userNameTv.setText(review.getUser().getUsername());
            reviewBodyTv.setText(review.getBody());
            favoriteCountTv.setText(review.getLikesCount());
            displayRatingBar.setRating(review.getRating().floatValue());
            displayRatingBar.setIsIndicator(true);
            commentFl.setOnClickListener(v -> {
                    if (composeCl.getVisibility() == View.VISIBLE) {
                        composeCl.setVisibility(View.GONE);
                    }
                    else { composeCl.setVisibility(View.VISIBLE); }
            });
            Drawable unliked = ContextCompat.getDrawable(context, R.drawable.ic_star_unliked);
            Drawable liked = ContextCompat.getDrawable(context, R.drawable.ic_star_liked);
            if (review.isLikedBy(ParseUser.getCurrentUser())){
                Log.i(TAG, "Changing favoriteIv to liked");
                favoriteIv.setImageDrawable(liked);
            }
            else {
                Log.i(TAG, "Changing favoriteIv to unliked");
                favoriteIv.setImageDrawable(unliked);
            }
            favoriteIv.setOnClickListener(v -> {
                if (((ImageView)v).getDrawable()==unliked) {
                    Log.i(TAG, "Changing favoriteIv to liked");
                    ((ImageView)v).setImageDrawable(liked);
                    likePost(true, review);
                }
                else {
                    Log.i(TAG, "Changing favoriteIv to unliked");
                    ((ImageView)v).setImageDrawable(unliked);
                    likePost(false, review);
                }
                review.saveInBackground(); // uploads new value back to parse
            });
            shareFl.setOnClickListener(v -> {

            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    private void likePost(boolean like, Review review) {
        List<ParseUser> likes = review.getLikedBy();
        if (like) {
            Log.i(TAG, "Adding user " + ParseUser.getCurrentUser().getUsername() + " To review " + review.getUser().getUsername());
            likes.add(ParseUser.getCurrentUser());
        }
        else {
            Log.i(TAG, "Removing user " + ParseUser.getCurrentUser().getUsername() + " To review " + review.getUser().getUsername());
            likes.remove(ParseUser.getCurrentUser());
        }
        review.setLikedBy(likes);
        favoriteCountTv.setText(review.getLikesCount());
    }
}
