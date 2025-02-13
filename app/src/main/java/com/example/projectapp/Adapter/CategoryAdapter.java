package com.example.projectapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectapp.Model.Category;
import com.example.projectapp.R;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categoryList;

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
        holder.categoryName.setText(category.getCategoryName());
        // Optionally, update project count or other details
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setCategories(List<Category> newCategories) {
        categoryList.clear();
        categoryList.addAll(newCategories);
        notifyDataSetChanged();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        // Other views if needed

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
