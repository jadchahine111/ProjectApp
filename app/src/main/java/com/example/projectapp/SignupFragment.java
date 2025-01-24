package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapp.databinding.FragmentSignupBinding;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;
    private int currentStep = 0; // This will track the current step
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Configure StepView
        binding.stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .stepsNumber(4) // 4 steps in total
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .steps(new ArrayList<String>() {{
                    add("Personal Details");
                    add("Profile Details");
                    add("Identity Verification");
                    add("Confirmation");
                }})
                .doneTextColor(ContextCompat.getColor(getContext(), R.color.gray))
                .commit();

        // Load the first step fragment inside the FrameLayout
        loadStepFragment(currentStep);

        // Handle the "Previous" button click
        binding.previous.setOnClickListener(v -> {
            if (currentStep > 0) {
                currentStep--; // Move to the previous step
                binding.stepView.go(currentStep, true); // Update StepView
                loadStepFragment(currentStep); // Load previous step fragment
            }
        });

        // Handle the "Next" button click
        binding.next.setOnClickListener(v -> {
            if (currentStep < 3) { // We have 4 steps (0-3)
                currentStep++; // Move to the next step
                binding.stepView.go(currentStep, true); // Update StepView
                loadStepFragment(currentStep); // Load next step fragment
            }
        });

        // Handle the "Go Back" button click
        binding.backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigateUp();
        });
    }

    // This method dynamically loads the fragment based on the current step
    private void loadStepFragment(int step) {
        Fragment fragment;
        switch (step) {
            case 0:
                fragment = new Step1Fragment(); // Load Step 1 Fragment
                break;
            case 1:
                fragment = new Step2Fragment(); // Load Step 2 Fragment
                break;
            case 2:
                fragment = new Step3Fragment(); // Load Step 3 Fragment
                break;
            case 3:
                fragment = new Step4Fragment(); // Load Step 4 Fragment
                break;
            default:
                fragment = new Step1Fragment(); // Default to Step 1 Fragment
        }

        // Replace the existing fragment in the FrameLayout with the new fragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
