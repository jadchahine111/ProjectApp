package com.example.projectapp.ViewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectapp.Model.Category;
import com.example.projectapp.Model.Notification;
import com.example.projectapp.Model.Project;
import com.example.projectapp.MyApplication.MyApplication;
import com.example.projectapp.Repository.CategoryRepository;
import com.example.projectapp.Repository.NotificationRepository;

import java.util.ArrayList;
import java.util.List;

public class NotificationViewModel extends ViewModel {

    private final NotificationRepository notificationRepository;
    private MutableLiveData<List<Notification>> notificationsLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Notification>> filteredNotificationsLiveData;

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<String> successMessage;

    public NotificationViewModel() {
        filteredNotificationsLiveData = new MutableLiveData<>();
        notificationRepository = new NotificationRepository(MyApplication.getAppContext());
        notificationsLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        successMessage = new MutableLiveData<>();
        loadNotifications();
    }

    public LiveData<List<Notification>> getNotificationsLiveData() {
        return notificationsLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public void loadNotifications() {
        notificationRepository.getNotifications(new NotificationRepository.GetNotificationsCallback() {
            @Override
            public void onSuccess(List<Notification> notifications) {
                notificationsLiveData.setValue(notifications);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.setValue(error);
            }
        });
    }

    public void filteredNotifications(String query) {
        List<Notification> originalList = notificationsLiveData.getValue();
        if (originalList == null) {
            return;
        }

        if (query == null || query.trim().isEmpty()) {
            // If query is empty, show all categories
            filteredNotificationsLiveData.setValue(originalList);
            return;
        }

        List<Notification> filteredList = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Notification notification : originalList) {
            if (notification.getCreatedAt().toLowerCase().contains(lowerQuery)) {
                filteredList.add(notification);
            }
        }
        notificationsLiveData.setValue(filteredList);
    }
    public void deleteNotification(int notificationId) {
        notificationRepository.deleteNotification(notificationId, new NotificationRepository.DeleteNotificationCallback() {
            @Override
            public void onSuccess(String message) {
                successMessage.setValue(message);
                // Refresh the notifications list after deletion
                loadNotifications();
            }
            @Override
            public void onFailure(String error) {
                errorMessage.setValue(error);
            }
        });
    }
}

