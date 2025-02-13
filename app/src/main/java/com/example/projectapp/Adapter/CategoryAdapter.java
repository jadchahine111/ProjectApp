package com.example.projectapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.Model.Category;
import com.example.projectapp.Model.Project;
import com.example.projectapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categoryList;

    // Constructor
    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item_view, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.getCategoryName().setText(category.getCategoryName());
        holder.getProjectCount().setText("");

        // Handle favorite (click listener)
        holder.getCategoryIcon().setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        TextView projectCount;
        ImageView categoryIcon;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            projectCount = itemView.findViewById(R.id.project_count);
            categoryIcon = itemView.findViewById(R.id.category_icon);
        }

        public TextView getProjectCount() {
            return projectCount;
        }

        public TextView getCategoryName() {
            return categoryName;
        }

        public ImageView getCategoryIcon() {
            return categoryIcon;
        }
    }

}