package com.example.projectapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectapp.Model.User;
import com.example.projectapp.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppliedUsersAdapter extends RecyclerView.Adapter<AppliedUsersAdapter.AppliedUserViewHolder> {

    private Context context;
    private List<User> appliedUsers;

    // This map stores the action result for each user (e.g., "Accepted" or "Declined")
    private Map<Integer, String> statusMap = new HashMap<>();

    // Interface for applied user actions
    public interface OnUserActionListener {
        void onAcceptClicked(User user);
        void onDeclineClicked(User user);
        void onViewProfileClicked(User user);
    }

    private OnUserActionListener actionListener;

    public void setOnUserActionListener(OnUserActionListener listener) {
        this.actionListener = listener;
    }

    public AppliedUsersAdapter(Context context, List<User> appliedUsers) {
        this.context = context;
        this.appliedUsers = appliedUsers;
    }

    @NonNull
    @Override
    public AppliedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.applied_user_item_view, parent, false);
        return new AppliedUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppliedUserViewHolder holder, int position) {
        User user = appliedUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());

        // Check if we have a stored status for this user
        String currentStatus = statusMap.get(user.getId());
        if (currentStatus != null) {
            // Hide the action buttons and show the status
            holder.buttonsLayout.setVisibility(View.GONE);
            holder.statusText.setVisibility(View.VISIBLE);
            holder.statusText.setText(currentStatus);
        } else {
            // No action taken yet: show buttons and hide status
            holder.buttonsLayout.setVisibility(View.VISIBLE);
            holder.statusText.setVisibility(View.GONE);
        }

        // Accept button click listener
        holder.acceptButton.setOnClickListener(v -> {
            Log.d("AppliedUsersAdapter", "Accept clicked for user: " + user.getEmail());
            if (actionListener != null) {
                actionListener.onAcceptClicked(user);
            }
        });

        // Decline button click listener
        holder.declineButton.setOnClickListener(v -> {
            Log.d("AppliedUsersAdapter", "Decline clicked for user: " + user.getId());
            if (actionListener != null) {
                actionListener.onDeclineClicked(user);
            }
        });

        // View Profile button click listener
        holder.viewProfileButton.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onViewProfileClicked(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appliedUsers.size();
    }

    public void setAppliedUsers(List<User> users) {
        appliedUsers.clear();
        appliedUsers.addAll(users);
        // Clear the status map when refreshing the list (or update as needed)
        statusMap.clear();
        notifyDataSetChanged();
    }

    // Method to update the status for a specific user and refresh that item
    public void setStatusForUser(int userId, String status) {
        statusMap.put(userId, status);
        notifyDataSetChanged();
    }

    public static class AppliedUserViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView email;
        TextView acceptButton;
        TextView declineButton;
        TextView viewProfileButton;
        TextView statusText;
        LinearLayout buttonsLayout;

        public AppliedUserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.email);
            acceptButton = itemView.findViewById(R.id.accept_button);
            declineButton = itemView.findViewById(R.id.decline_button);
            viewProfileButton = itemView.findViewById(R.id.view_profile_button);
            statusText = itemView.findViewById(R.id.status_text);
            buttonsLayout = itemView.findViewById(R.id.buttonsLayout);
        }
    }
}
