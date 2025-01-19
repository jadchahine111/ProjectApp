package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivitySuccessEmailVerificationBinding;
import com.example.projectapp.databinding.ActivityUserAcceptedProjectsBinding;

public class UserAcceptedProjectsActivity extends AppCompatActivity {

    private ActivityUserAcceptedProjectsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserAcceptedProjectsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}