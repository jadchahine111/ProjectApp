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

        // We don’t have 'owner' in your Project model, but let's assume we have a userId
        // For now, let's just show "Owner ID: " + userId or set some placeholder
        holder.projectOwnerUsername.setText("Owner ID: " + project.getUserId());

        // Skills
        // If 'skillsNeeded' is a string, you could parse it or just display it
        if (project.getSkillsNeeded() != null) {
            holder.skill.setText(project.getSkillsNeeded());
        } else {
            holder.skill.setText("No skills specified");
        }


        // Handle card click to make an API request
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("projectId", project.getId());  // Passing only the projectId
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_homeFragment_to_projectDetailsFragment, bundle);
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
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }

    }
    public void setProjects(List<Project> newProjects) {
        // Clear or replace the old data
        this.projectList.clear();
        this.projectList.addAll(newProjects);

        // Notify the RecyclerView that the data changed
        notifyDataSetChanged();
    }

}
