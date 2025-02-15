package com.example.projectapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectapp.Model.Notification;
import com.example.projectapp.R;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private List<Notification> notificationList;

    // Callback interface for delete action
    public interface OnNotificationActionListener {
        void onDeleteClicked(Notification notification);
    }

    private OnNotificationActionListener actionListener;

    public void setOnNotificationActionListener(OnNotificationActionListener listener) {
        this.actionListener = listener;
    }

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item_view, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.notificationMessage.setText(notification.getMessage());
        if (notification.getCreatedAt() != null && !notification.getCreatedAt().isEmpty()) {
            holder.createdAt.setText(notification.getCreatedAt());
        } else {
            holder.createdAt.setText("No Date");
        }
        // Set delete click listener if available
        if (actionListener != null) {
            holder.delete.setOnClickListener(v -> actionListener.onDeleteClicked(notification));
        }
        Log.d("NotificationAdapter", "CreatedAt: " + notification.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void setNotifications(List<Notification> newNotifications) {
        notificationList.clear();
        notificationList.addAll(newNotifications);
        notifyDataSetChanged();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notificationMessage;
        TextView createdAt;
        TextView delete; // The "Delete" TextView

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationMessage = itemView.findViewById(R.id.notification_message);
            createdAt = itemView.findViewById(R.id.created_at);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
