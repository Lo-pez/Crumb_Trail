package com.example.crumbtrail.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crumbtrail.R;
import com.example.crumbtrail.ReviewFeedActivity;
import com.example.crumbtrail.data.model.Food;

import org.parceler.Parcels;

import java.util.List;

/**
 * Adapter for each food item displayed from a users search query or
 * camera scan.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    Context context;
    List<Food> Foods;
    TextView brandNameTv;
    TextView foodCategoryTv;
    TextView foodDescriptionTv;
    ConstraintLayout foodItemCl;

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
        Food food = Foods.get(position);
        // Bind the Food data into the VH
        holder.bind(food);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return Foods.size();
    }

    public void clear() {
        Foods.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            brandNameTv = itemView.findViewById(R.id.brandNameTv);
            foodCategoryTv = itemView.findViewById(R.id.foodCategoryTv);
            foodDescriptionTv = itemView.findViewById(R.id.foodDescriptionTv);
            foodItemCl = itemView.findViewById(R.id.foodItemCl);

            // itemView's onclick listener
            itemView.setOnClickListener(this);

        }

        public void bind(Food food) {
            brandNameTv.setText(food.getBrandName());
            foodCategoryTv.setText(food.getFoodCategory());
            foodDescriptionTv.setText(food.getDescription());
            foodItemCl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReviewFeedActivity.class);
                    intent.putExtra("Food", Parcels.wrap(food));
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
