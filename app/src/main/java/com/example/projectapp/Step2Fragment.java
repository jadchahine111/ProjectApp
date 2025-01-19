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

import com.example.projectapp.ViewModels.SignUpViewModel;
import com.example.projectapp.databinding.FragmentStep2Binding;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class Step2Fragment extends Fragment {

    private FragmentStep2Binding binding;
    private SignUpViewModel signUpViewModel;

    // Example skills data (could come from a server, database, etc.)
    private final String[] SKILLS = {
            "HTML", "CSS", "JavaScript", "Node.js", "React",
            "Angular", "PHP", "Java", "Kotlin", "Swift",
            ".NET Core", "Python"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
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
        binding.firstName.setText(signUpViewModel.getFirstName().getValue());
        binding.lastName.setText(signUpViewModel.getLastName().getValue());
        binding.bio.setText(signUpViewModel.getBio().getValue());
        binding.linkedinUrl.setText(signUpViewModel.getLinkedinURL().getValue());

        // -----------------------
        // Saving data to ViewModel (TextWatchers)
        // -----------------------
        binding.firstName.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getFirstName().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.lastName.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getLastName().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.bio.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getBio().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.linkedinUrl.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getLinkedinURL().setValue(s.toString());
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

        return view;
    }

    /**
     * Dynamically create a Chip for the given skill name and add it to the ChipGroup.
     * Clicking the close icon removes the chip.
     */
    private void addChip(String skillName) {
        Chip chip = new Chip(requireContext());
        chip.setText(skillName);
        chip.setCloseIconVisible(true);

        chip.setOnCloseIconClickListener(view -> {
            binding.chipGroupSkills.removeView(chip);
        });

        binding.chipGroupSkills.addView(chip);
    }

    @Override
    public void onPause() {
        super.onPause();
        // When the user leaves this fragment (or the fragment is paused),
        // gather all chip texts and store them in the ViewModel.
        storeChipsInViewModel();
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
        // Optionally store it in your ViewModel (assuming you have a LiveData or a field for skills):
        signUpViewModel.getSkills().setValue(allSkills);;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
