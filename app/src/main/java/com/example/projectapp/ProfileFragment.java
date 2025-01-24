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

import com.example.projectapp.databinding.FragmentOwnerProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentOwnerProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOwnerProfileBinding.inflate(inflater, container, false);

        setupClickListeners();

        // Ensure the bottom navigation visibility is handled correctly
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(true);  // Show Bottom Navigation for this fragment
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Additional view setup logic (if needed) can go here.
    }

    private void setupClickListeners() {
        // Set up click listeners for all navigation actions
        binding.editUsername.setOnClickListener(v -> navigateTo(R.id.action_profileFragment_to_editUsernameFragment));
        binding.editFirstname.setOnClickListener(v -> navigateTo(R.id.action_profileFragment_to_editFirstNameFragment));
        binding.editLastname.setOnClickListener(v -> navigateTo(R.id.action_profileFragment_to_editLastNameFragment));
        binding.editAboutMe.setOnClickListener(v -> navigateTo(R.id.action_profileFragment_to_editAboutMeFragment));
        binding.editCv.setOnClickListener(v -> navigateTo(R.id.action_profileFragment_to_editCV));
    }

    private void navigateTo(int actionId) {
        // Unified navigation method to avoid repetitive code
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(actionId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // Avoid memory leaks
    }
}
