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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapp.Model.User;
import com.example.projectapp.ViewModels.UserViewModel;
import com.example.projectapp.databinding.FragmentOwnerProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentOwnerProfileBinding binding;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOwnerProfileBinding.inflate(inflater, container, false);
        setupClickListeners();

        // Show Bottom Navigation if needed
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain UserViewModel (extending AndroidViewModel to avoid a factory)
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observe user data and update UI
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // Set welcome text and subtext (username & email)
                binding.welcomeText.setText("Welcome, " + user.getUsername());
                binding.subText.setText(user.getEmail());

                // Set first name, last name, about me, and cv details:
                binding.ownerFirstname.setText(user.getFirstName());
                binding.ownerLastname.setText(user.getLastName());
                binding.ownerBio.setText(user.getBio());
            }
        });

        // Observe error messages
        userViewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupClickListeners() {
        // Navigation for editing user details
        binding.editUsername.setOnClickListener(v ->
                navigateTo(R.id.action_profileFragment_to_editUsernameFragment)
        );
        binding.editFirstname.setOnClickListener(v ->
                navigateTo(R.id.action_profileFragment_to_editFirstNameFragment)
        );
        binding.editLastname.setOnClickListener(v ->
                navigateTo(R.id.action_profileFragment_to_editLastNameFragment)
        );
        binding.editAboutMe.setOnClickListener(v ->
                navigateTo(R.id.action_profileFragment_to_editAboutMeFragment)
        );
        binding.editCv.setOnClickListener(v ->
                navigateTo(R.id.action_profileFragment_to_editCV)
        );
    }

    private void navigateTo(int actionId) {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(actionId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
