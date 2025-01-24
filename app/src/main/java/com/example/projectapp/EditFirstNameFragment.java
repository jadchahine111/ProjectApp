package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapp.databinding.FragmentEditFirstNameBinding;

public class EditFirstNameFragment extends Fragment {

    private FragmentEditFirstNameBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Use ViewBinding to inflate the layout
        binding = FragmentEditFirstNameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide Bottom Navigation
        hideBottomNavigation();

        // Set up event listeners
        setupListeners();
    }

    /**
     * Sets up UI event listeners.
     */
    private void setupListeners() {
        binding.backButton.setOnClickListener(v -> navigateToProfileFragment());
    }

    /**
     * Navigates back to the Profile Fragment.
     */
    private void navigateToProfileFragment() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.profileFragment);
    }

    /**
     * Hides the Bottom Navigation View by delegating the behavior to MainActivity.
     */
    private void hideBottomNavigation() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Prevent memory leaks by nullifying the binding reference
        binding = null;
    }
}
