package com.example.projectapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.projectapp.databinding.ActivityAuthAppBinding; // Import the generated binding class
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AuthAppActivity extends AppCompatActivity {

    // Create a binding field
    private ActivityAuthAppBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the binding instead of using setContentView with layout resource
        binding = ActivityAuthAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load the default fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        // BottomNavigationView setup using binding (instead of findViewById)
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (item.getItemId() == R.id.nav_my_profile) {
                selectedFragment = new ProfileFragment();
            } else if (item.getItemId() == R.id.nav_my_section) {
                selectedFragment = new ProjectFragment();
            }

            if (selectedFragment != null) {
                // Replace the fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true; // Indicate that the item selection was handled
        });
    }
}
