package com.example.projectapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.projectapp.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

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
}
