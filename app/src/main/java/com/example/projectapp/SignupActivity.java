package com.example.projectapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectapp.ViewModels.SignUpViewModel;
import com.example.projectapp.databinding.ActivitySignupBinding;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    // Use the generated binding class
    private ActivitySignupBinding binding;
    private int currentStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the binding
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SignUpViewModel signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        // Configure the StepView using binding
        binding.stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .stepsNumber(4) // Adjust the number of steps as per your needs
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .steps(new ArrayList<String>() {{
                    add("Personal Details");
                    add("Profile Details");
                    add("Identity Verification");
                    add("Confirmation");
                }})
                .doneTextColor(getResources().getColor(R.color.gray)) // or ContextCompat.getColor(this, R.color.gray)
                .commit();

        // Load the first fragment initially
        loadFragment(new Step1Fragment());

        // Set the StepView to the first step
        updateStepView();

        // Handle Previous Button Click using binding
        binding.previous.setOnClickListener(v -> {
            if (currentStep > 0) {
                currentStep--; // Move to the previous step
                binding.stepView.go(currentStep, true); // Update the StepView to the previous step
                loadFragment(getFragmentByStep(currentStep)); // Load the fragment for the previous step
            }
        });

        // Handle Next Button Click using binding
        binding.next.setOnClickListener(v -> {
            if (currentStep < 3) { // Assuming there are 4 steps (Step1, Step2, Step3, Step4)
                // Mark the current step as done before moving to the next step
                binding.stepView.done(false); // Mark the current step as not done
                currentStep++; // Move to the next step
                binding.stepView.go(currentStep, true); // Move StepView to the next step
                loadFragment(getFragmentByStep(currentStep)); // Load the fragment for the new step
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // Begin a fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private Fragment getFragmentByStep(int step) {
        if (step == 0) {
            return new Step1Fragment();
        } else if (step == 1) {
            return new Step2Fragment();
        } else if (step == 2) {
            // Assuming you have a Step3Fragment similar to others
            return new Step3Fragment();
        } else if (step == 3) {
            return new Step4Fragment();
        } else {
            return new Step1Fragment(); // Default to Step1Fragment if the step is invalid
        }
    }

    private void updateStepView() {
        // Update the StepView to reflect the current step
        binding.stepView.go(currentStep, true);
    }
}
