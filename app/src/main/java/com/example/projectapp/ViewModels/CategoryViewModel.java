package com.example.projectapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectapp.Model.Category;
import com.example.projectapp.Repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository categoryRepository;
    private MutableLiveData<List<Category>> categoriesLiveData;
    private MutableLiveData<String> errorMessage;

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository();
        categoriesLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        loadCategories();
    }

    public LiveData<List<Category>> getCategories() {
        return categoriesLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadCategories() {
        categoryRepository.getCategories(new CategoryRepository.GetCategoriesCallback() {
            @Override
            public void onSuccess(List<Category> categories) {
                categoriesLiveData.setValue(categories);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.setValue(error);
            }
        });
    }
}
