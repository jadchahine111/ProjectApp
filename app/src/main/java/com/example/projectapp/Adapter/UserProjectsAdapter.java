package com.example.projectapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectapp.Model.Project;
import com.example.projectapp.R;
import java.util.List;

public class UserProjectsAdapter extends RecyclerView.Adapter<UserProjectsAdapter.UserProjectViewHolder> {

    private Context context;
    private List<Project> projectList;
    private int layoutResourceId; // Layout resource for the item view

    // Interface for project actions
    public interface OnProjectActionListener {
        void onArchiveClicked(Project project);
        void onUnarchiveClicked(Project project);
        void onUnfavoriteClicked(Project project);
    }

    private OnProjectActionListener actionListener;

    public void setOnProjectActionListener(OnProjectActionListener listener) {
        this.actionListener = listener;
    }

    // Constructor accepts a layout resource ID
    public UserProjectsAdapter(Context context, List<Project> projectList, int layoutResourceId) {
        this.context = context;
        this.projectList = projectList;
        this.layoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public UserProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutResourceId, parent, false);
        return new UserProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProjectViewHolder holder, int position) {
        Project project = projectList.get(position);

        // Set the project title and skills
        holder.projectTitle.setText(project.getTitle() != null ? project.getTitle() : "");
        if (project.getSkillsNeeded() != null) {
            holder.skill.setText(project.getSkillsNeeded());
        } else {
            holder.skill.setText("No skills specified");
        }

        // Handle navigation to project details
        holder.viewDetails.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("projectId", project.getId());  // Pass project ID to details screen

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_global_projectDetailsFragment, bundle);
        });

        // If an archive button is present, set its click listener
        if (actionListener != null && holder.archiveButton != null) {
            holder.archiveButton.setOnClickListener(v -> actionListener.onArchiveClicked(project));
        }
        // If an unarchive button is present, set its click listener
        if (actionListener != null && holder.unarchiveButton != null) {
            holder.unarchiveButton.setOnClickListener(v -> actionListener.onUnarchiveClicked(project));
        }
        // If an unfavorite button is present, set its click listener
        if (actionListener != null && holder.unfavoriteButton != null) {
            holder.unfavoriteButton.setOnClickListener(v -> actionListener.onUnfavoriteClicked(project));
        }
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    // Update the list of projects
    public void setProjects(List<Project> newProjects) {
        this.projectList.clear();
        this.projectList.addAll(newProjects);
        notifyDataSetChanged();
    }

    // ViewHolder definition
    public static class UserProjectViewHolder extends RecyclerView.ViewHolder {
        TextView projectTitle;

        TextView viewDetails;
        TextView skill;
        // Optional buttons – they might be present in some layouts but not others.
        TextView archiveButton;    // e.g. for archiving in posted projects
        TextView unarchiveButton;  // e.g. for unarchiving in archived projects
        TextView unfavoriteButton; // e.g. for removing favorites in favorited projects

        public UserProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectTitle = itemView.findViewById(R.id.project_title);
            skill = itemView.findViewById(R.id.skill);
            // Try to find these views; if the current layout doesn’t include them, they’ll be null.
            archiveButton = itemView.findViewById(R.id.archive_button);
            unarchiveButton = itemView.findViewById(R.id.unarchive_button);
            unfavoriteButton = itemView.findViewById(R.id.unfavorite_button);
            viewDetails = itemView.findViewById(R.id.view_details);
        }
    }
}
