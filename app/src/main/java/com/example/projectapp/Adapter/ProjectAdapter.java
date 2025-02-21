package com.example.projectapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectapp.Model.Project;
import com.example.projectapp.R;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private Context context;
    private List<Project> projectList;

    // Interface for favorite button click
    public interface OnFavoriteClickListener {
        void onFavoriteClicked(Project project);
    }

    private OnFavoriteClickListener favoriteClickListener;

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.favoriteClickListener = listener;
    }

    // Constructor
    public ProjectAdapter(Context context, List<Project> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_item_view, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Project project = projectList.get(position);

        // Set the title
        holder.projectTitle.setText(project.getTitle());

        // Set owner placeholder (using userId as an example)
        holder.projectOwnerUsername.setText("Owner ID: " + project.getUserId());

        // Set skills text
        if (project.getSkillsNeeded() != null) {
            holder.skill.setText(project.getSkillsNeeded());
        } else {
            holder.skill.setText("No skills specified");
        }


        // Handle card click to navigate to project details
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("projectId", project.getId());  // Passing the projectId
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_global_projectDetailsFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView projectTitle;
        TextView projectOwnerUsername;
        TextView skill;
        ImageButton favoriteButton;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectTitle = itemView.findViewById(R.id.project_title);
            projectOwnerUsername = itemView.findViewById(R.id.project_owner_username);
            skill = itemView.findViewById(R.id.skill);
        }
    }

    public void setProjects(List<Project> newProjects) {
        projectList.clear();
        projectList.addAll(newProjects);
        notifyDataSetChanged();
    }
}
