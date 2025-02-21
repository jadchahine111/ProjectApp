package com.example.projectapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.projectapp.Model.User;
import com.example.projectapp.ViewModels.UserViewModel;
import com.example.projectapp.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private UserViewModel userViewModel;
    private int userId; // The ID of the user whose details we want to display

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the ViewModel (shared with the activity)
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // Retrieve the userId from the arguments if passed, or set a default
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", -1);
        }

        // If we have a valid userId, load the other user's details
        if (userId != -1) {
            userViewModel.loadOtherUserDetails(userId);
        }

        // Observe the LiveData for other user details
        userViewModel.getOtherUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // Fill UI elements with user details
                if (!TextUtils.isEmpty(user.getEmail()))
                    binding.userEmail.setText(user.getEmail());
                if (!TextUtils.isEmpty(user.getUsername()))
                    binding.userUsername.setText(user.getUsername());
                if (!TextUtils.isEmpty(user.getFirstName()))
                    binding.userFirstname.setText(user.getFirstName());
                if (!TextUtils.isEmpty(user.getLastName()))
                    binding.userLastname.setText(user.getLastName());
                if (!TextUtils.isEmpty(user.getBio()))
                    binding.userBio.setText(user.getBio());
                // ... update additional UI fields as necessary
            }
        });

        // Set up back button listener
        binding.backButton.setOnClickListener(v -> navigateBack());
        hideBottomNavigation();
    }

    private void navigateBack() {
        NavController navController = Navigation.findNavController(requireView());
        navController.popBackStack();
    }

    private void hideBottomNavigation() {
        if(getActivity() instanceof MainActivity) {
            ((MainActivity)getActivity()).setBottomNavigationVisibility(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
