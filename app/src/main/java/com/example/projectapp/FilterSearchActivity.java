package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityEmailVerificationBinding;
import com.example.projectapp.databinding.ActivityFilterSearchBinding;

public class FilterSearchActivity extends AppCompatActivity {

    private ActivityFilterSearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFilterSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}