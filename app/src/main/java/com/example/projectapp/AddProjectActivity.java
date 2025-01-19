package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityAddProjectBinding;

public class AddProjectActivity extends AppCompatActivity {

    private ActivityAddProjectBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}