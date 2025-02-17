package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.projectapp.MyApplication.MyApplication;
import com.example.projectapp.Repository.UserRepository;
import com.example.projectapp.ViewModels.UserViewModel;
import com.example.projectapp.databinding.FragmentEmailVerificationBinding;

public class EmailVerificationFragment extends Fragment {

    private FragmentEmailVerificationBinding binding;
    private UserViewModel userViewModel;
    private UserRepository userRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using View Binding
        binding = FragmentEmailVerificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the ViewModel
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // Initialize the UserRepository
        userRepository = new UserRepository(MyApplication.getAppContext());

        // Get the email from the ViewModel
        String email = userViewModel.getUserLiveData().getValue().getEmail();
        if (email == null || email.isEmpty()) {
            Toast.makeText(requireContext(), "Email not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set the email in the TextView
        binding.tvEmailAddress.setText(email);

        // Observe verification status from ViewModel
        userViewModel.getVerificationStatus().observe(getViewLifecycleOwner(), status -> {
            if (status != null) {
                if (status.equals("verified")) {
                    // Navigate to the next fragment
                    navigateToNextFragment();
                } else if (status.equals("notVerified")) {
                    // Show a toast indicating the user is not verified
                    Toast.makeText(requireContext(), "Email not verified. Please check your inbox.", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unexpected status
                    Toast.makeText(requireContext(), "Unexpected response from server.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for the "Verify" button
        binding.btnVerify.setOnClickListener(v -> userViewModel.checkEmailVerification(email));
    }

    private void navigateToNextFragment() {
        // Use Navigation Component to navigate to the next fragment
        Navigation.findNavController(requireView()).navigate(R.id.action_emailVerificationFragment_to_successEmailVerificationFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Avoid memory leaks by nullifying the binding
        binding = null;
    }
}