package com.example.projectapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectapp.ViewModels.SignUpViewModel;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    FrameLayout fragmentContainer;
    Button previousButton, nextButton;
    private int currentStep = 0;
    StepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignUpViewModel signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        fragmentContainer = findViewById(R.id.fragment_container);
        previousButton = findViewById(R.id.previous);
        nextButton = findViewById(R.id.next);
        stepView = findViewById(R.id.step_view);

        // Configure the StepView
        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .stepsNumber(3) // Adjust the number of steps as per your needs
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .steps(new ArrayList<String>() {{
                    add("Personal Details");
                    add("Profile Details");
                    add("Identity Verification");
                }})
                .commit();

        // Load the first fragment initially
        loadFragment(new Step1Fragment());

        // Set the StepView to the first step
        updateStepView();

        // Handle Previous Button Click
        previousButton.setOnClickListener(v -> {
            if (currentStep > 0) {
                stepView.done(false); // Mark the current step as not done
                currentStep--;
                updateStepView();
                loadFragment(getFragmentByStep(currentStep));
            }
        });

        // Handle Next Button Click
        nextButton.setOnClickListener(v -> {
            if (currentStep < 2) { // Assuming there are 3 steps (Step1, Step2, Step3)
                // Mark the current step as done before moving to the next step
                stepView.done(true);
                currentStep++;
                updateStepView();
                loadFragment(getFragmentByStep(currentStep));
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
            return new Step3Fragment();
        } else {
            return new Step1Fragment(); // Default to Step1Fragment if the step is invalid
        }
    }

    private void updateStepView() {

        // Update the StepView to reflect the current step
        stepView.go(currentStep, true);


    }
}
