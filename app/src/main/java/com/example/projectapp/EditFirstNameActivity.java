package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityEditBioBinding;
import com.example.projectapp.databinding.ActivityEditFirstNameBinding;

public class EditFirstNameActivity extends AppCompatActivity {

    private ActivityEditFirstNameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditFirstNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}