package com.example.projectapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectapp.Adapter.NotificationAdapter;
import com.example.projectapp.Adapter.ProjectAdapter;
import com.example.projectapp.ViewModels.NotificationViewModel;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private NotificationViewModel notificationViewModel;

    private NotificationAdapter notificationAdapter;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using ViewBinding
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide Bottom Navigation
        hideBottomNavigation();

        // Initialize ViewModel
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        // Setup the RecyclerView and Adapter
        setupRecyclerView();

        // Observe data from ViewModel
        observeViewModel();


        // Set up back button navigation
        setupListeners();
    }

    /**
     * Hides the Bottom Navigation View.
     */
    private void hideBottomNavigation() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }
    }

    /**
     * Sets up the back button to navigate to the Home Fragment.
     */
    private void setupListeners() {
        binding.backButton.setOnClickListener(v -> navigateToHomeFragment());
    }

    /**
     * Navigates back to the Home Fragment.
     */
    private void navigateToHomeFragment() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.homeFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Prevent memory leaks by nullifying the binding reference
        binding = null;
    }

    private void setupRecyclerView() {
        // 1. Initialize the adapter with an empty list (or pass a list if you have one)
        notificationAdapter = new NotificationAdapter(requireContext(), new ArrayList<>());

        // 2. Attach a layout manager
        binding.notificationRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 3. Set the adapter
        binding.notificationRecyclerView.setAdapter(notificationAdapter);
    }

    private void observeViewModel() {
        // Observe the list of projects
        notificationViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), notifications -> {
            if (notifications != null) {
                notificationAdapter.setNotifications(notifications);
            }
        });



        // Observe any error message
        notificationViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                // Show a toast, or set an error text, etc.
                // Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
                binding.specializationTitle.setText(errorMsg);
            }
        });
    }
}
