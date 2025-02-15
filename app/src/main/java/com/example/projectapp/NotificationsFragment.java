package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.projectapp.Adapter.NotificationAdapter;
import com.example.projectapp.ViewModels.NotificationViewModel;
import com.example.projectapp.databinding.FragmentNotificationsBinding;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private NotificationViewModel notificationViewModel;
    private NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hideBottomNavigation();
        setupRecyclerView();
        setupListeners();

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        observeViewModel();
    }

    private void hideBottomNavigation() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }
    }

    private void setupListeners() {
        binding.backButton.setOnClickListener(v -> navigateToHomeFragment());
    }

    private void navigateToHomeFragment() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.homeFragment);
    }

    private void setupRecyclerView() {
        notificationAdapter = new NotificationAdapter(requireContext(), new ArrayList<>());
        binding.notificationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.notificationRecyclerView.setAdapter(notificationAdapter);
        // Set the delete action listener
        notificationAdapter.setOnNotificationActionListener(notification -> {
            // Call the ViewModel to delete this notification
            notificationViewModel.deleteNotification(notification.getId());
        });
    }

    private void observeViewModel() {
        notificationViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), notifications -> {
            if (notifications != null) {
                notificationAdapter.setNotifications(notifications);
            }
        });
        notificationViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                binding.specializationTitle.setText(errorMsg);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
