package com.example.projectapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectapp.ViewModels.UserViewModel;
import com.example.projectapp.databinding.FragmentStep2Binding;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class Step2Fragment extends Fragment {

    private FragmentStep2Binding binding;
    private UserViewModel userViewModel;

    // Example skills data (could come from a server, database, etc.)
    private final String[] SKILLS = {
            "HTML", "CSS", "JavaScript", "Node.js", "React",
            "Angular", "PHP", "Java", "Kotlin", "Swift",
            ".NET Core", "Python"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate using view binding
        binding = FragmentStep2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // -----------------------
        // Restoring data from ViewModel
        // -----------------------
        binding.firstName.setText(userViewModel.getUserLiveData().getValue().getFirstName());
        binding.lastName.setText(userViewModel.getUserLiveData().getValue().getLastName());
        binding.bio.setText(userViewModel.getUserLiveData().getValue().getBio());
        binding.linkedinUrl.setText(userViewModel.getUserLiveData().getValue().getLinkedinURL());

        // -----------------------
        // Saving data to ViewModel (TextWatchers)
        // -----------------------
        binding.firstName.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                userViewModel.getUserLiveData().getValue().setFirstName(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.lastName.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                userViewModel.getUserLiveData().getValue().setLastName(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.bio.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                userViewModel.getUserLiveData().getValue().setBio(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.linkedinUrl.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                userViewModel.getUserLiveData().getValue().setLinkedinURL(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // -------------------------------------------------
        // AUTO-COMPLETE & CHIPGROUP LOGIC FOR SKILLS
        // -------------------------------------------------

        // Create an ArrayAdapter for the AutoCompleteTextView
        ArrayAdapter<String> skillsAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,  // or a custom layout
                SKILLS
        );
        binding.skillsAutoComplete.setAdapter(skillsAdapter);

        // When the user selects an item from suggestions
        binding.skillsAutoComplete.setOnItemClickListener((parent, itemView, position, id) -> {
            String selectedSkill = skillsAdapter.getItem(position);
            if (selectedSkill != null) {
                addChip(selectedSkill);
                binding.skillsAutoComplete.setText(""); // clear after selection
            }
        });

        // Restore skills from ViewModel if they exist
        String storedSkills = userViewModel.getSkillsLiveData().getValue();
        if (storedSkills != null && !storedSkills.isEmpty()) {
            String[] skillArray = storedSkills.split(",\\s*");
            for (String skill : skillArray) {
                addChip(skill.trim());
            }
        }

        return view;
    }

    private void addChip(String skillName) {
        Chip chip = new Chip(requireContext());
        chip.setText(skillName);
        chip.setCloseIconVisible(true);

        chip.setOnCloseIconClickListener(view -> {
            binding.chipGroupSkills.removeView(chip);
            storeChipsInViewModel(); // Update ViewModel when a chip is removed
        });

        binding.chipGroupSkills.addView(chip);
        storeChipsInViewModel(); // Update ViewModel whenever a new chip is added
    }

    private void storeChipsInViewModel() {
        List<String> skillList = new ArrayList<>();
        int childCount = binding.chipGroupSkills.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View chipView = binding.chipGroupSkills.getChildAt(i);
            if (chipView instanceof Chip) {
                Chip chip = (Chip) chipView;
                skillList.add(chip.getText().toString());
            }
        }
        String allSkills = TextUtils.join(", ", skillList);
        userViewModel.getSkillsLiveData().setValue(allSkills); // Update ViewModel with the skills list
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
