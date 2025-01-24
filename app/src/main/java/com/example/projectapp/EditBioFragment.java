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
import com.example.projectapp.databinding.FragmentEditBioBinding;

public class EditBioFragment extends Fragment {

    private FragmentEditBioBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Use View Binding to inflate the layout
        binding = FragmentEditBioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide Bottom Navigation
        hideBottomNavigation();

        // Set up UI listeners
        setupListeners();
    }

    private void setupListeners() {
        binding.backButton.setOnClickListener(v -> navigateBack());
    }

    private void navigateBack() {
        // Use NavController to navigate back to the ProfileFragment
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.profileFragment);
    }

    private void hideBottomNavigation() {
        // Delegate bottom navigation visibility management to MainActivity
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clear binding reference to prevent memory leaks
        binding = null;
    }
}
