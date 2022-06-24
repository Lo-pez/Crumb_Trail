package com.example.crumbtrail.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crumbtrail.FoodReviewActivity;
import com.example.crumbtrail.MainActivity;
import com.example.crumbtrail.R;
import com.example.crumbtrail.data.model.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    Context context;
    List<Food> Foods;
    TextView brandNameTv;
    TextView foodCategoryTv;
    TextView foodDescriptionTv;
    ConstraintLayout clFood;

    public FoodAdapter(Context context, List<Food> Foods) {
        this.context = context;
        this.Foods = Foods;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("FoodAdapter", "onCreateViewHolder");
        View FoodView = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(FoodView);
    }

    // Involves populating data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("FoodAdapter", "onBindViewHolder" + position);
        // Get the Food at the passed in position
        Food Food = Foods.get(position);
        // Bind the Food data into the VH
        holder.bind(Food);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return Foods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            brandNameTv = itemView.findViewById(R.id.brandNameTextView);
            foodCategoryTv = itemView.findViewById(R.id.foodCategoryTextView);
            foodDescriptionTv = itemView.findViewById(R.id.foodDescriptionTextView);
            clFood = itemView.findViewById(R.id.clFood);

            // itemView's onclick listener
            itemView.setOnClickListener(this);

        }

        public void bind(Food Food) {
            brandNameTv.setText(Food.getBrandName());
            foodCategoryTv.setText(Food.getFoodCategory());
            foodDescriptionTv.setText(Food.getDescription());
            clFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FoodReviewActivity.class);
                    intent.putExtra("FDCID", Food.getFDCID());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
