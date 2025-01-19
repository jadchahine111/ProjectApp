package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityProjectDetailsBinding;
import com.example.projectapp.databinding.ActivitySuccessEmailVerificationBinding;

public class SuccessEmailVerificationActivity extends AppCompatActivity {

    private ActivitySuccessEmailVerificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySuccessEmailVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}