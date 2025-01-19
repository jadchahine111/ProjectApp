package com.example.projectapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectapp.databinding.ActivityEditCvBinding;

public class EditCVActivity extends AppCompatActivity {

    private ActivityEditCvBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditCvBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}