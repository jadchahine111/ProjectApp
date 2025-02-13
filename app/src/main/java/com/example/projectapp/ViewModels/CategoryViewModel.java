package com.example.projectapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectapp.Model.Category;
import com.example.projectapp.Repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {

    private final CategoryRepository categoryRepository;
    private final MutableLiveData<List<Category>> categoriesLiveData;
    private final MutableLiveData<List<Category>> filteredCategoriesLiveData;
    private final MutableLiveData<String> errorMessage;

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository();
        categoriesLiveData = new MutableLiveData<>();
        filteredCategoriesLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        loadCategories();
    }

    public LiveData<List<Category>> getCategories() {
        return filteredCategoriesLiveData; // We'll expose the filtered list to the UI.
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadCategories() {
        categoryRepository.getCategories(new CategoryRepository.GetCategoriesCallback() {
            @Override
            public void onSuccess(List<Category> categories) {
                categoriesLiveData.setValue(categories);
                // Initially, no filtering—show all categories
                filteredCategoriesLiveData.setValue(categories);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.setValue(error);
            }
        });
    }

    // Filter categories by name (case-insensitive)
    public void filterCategories(String query) {
        List<Category> originalList = categoriesLiveData.getValue();
        if (originalList == null) {
            return;
        }

        if (query == null || query.trim().isEmpty()) {
            // If query is empty, show all categories
            filteredCategoriesLiveData.setValue(originalList);
            return;
        }

        List<Category> filteredList = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Category category : originalList) {
            if (category.getCategoryName().toLowerCase().contains(lowerQuery)) {
                filteredList.add(category);
            }
        }
        filteredCategoriesLiveData.setValue(filteredList);
    }
}
