package com.example.projectapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.Model.Project;
import com.example.projectapp.R;

import java.util.List;

public class UserProjectsAdapter extends RecyclerView.Adapter<UserProjectsAdapter.UserProjectViewHolder> {

    private Context context;
    private List<Project> projectList;

    // Constructor
    public UserProjectsAdapter(Context context, List<Project> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public UserProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_posted_projects_item_view, parent, false);
        return new UserProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProjectViewHolder holder, int position) {
        Project project = projectList.get(position);

        // Set the title
        holder.projectTitle.setText(project.getTitle());


        holder.skill.setText(project.getSkillsNeeded());


        if (project.getSkillsNeeded() != null) {
            holder.skill.setText(project.getSkillsNeeded());
        } else {
            holder.skill.setText("No skills specified");
        }

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public static class UserProjectViewHolder extends RecyclerView.ViewHolder {
        TextView projectTitle;
        TextView skill;

        public UserProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectTitle = itemView.findViewById(R.id.title);
            skill = itemView.findViewById(R.id.skill);
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
