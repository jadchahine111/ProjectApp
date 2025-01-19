package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityEditUsernameBinding;
import com.example.projectapp.databinding.ActivityEmailVerificationBinding;

public class EmailVerificationActivity extends AppCompatActivity {

    private ActivityEmailVerificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEmailVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}