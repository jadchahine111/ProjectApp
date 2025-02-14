package com.example.projectapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectapp.Model.Project;
import com.example.projectapp.MyApplication.MyApplication;
import com.example.projectapp.Repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewModel extends ViewModel {

    private final ProjectRepository projectRepository;

    private final MutableLiveData<List<Project>> allProjectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> filteredProjectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> successMessageLiveData = new MutableLiveData<>();

    // User Active Projects
    private final MutableLiveData<List<Project>> allUserActiveProjectsLiveData = new MutableLiveData<>();

    public ProjectViewModel() {
        projectRepository = new ProjectRepository(MyApplication.getAppContext());
        loadProjects();
        loadUserActiveProjects();
    }

    public LiveData<List<Project>> getFilteredProjects() {
        return filteredProjectsLiveData;
    }

    public LiveData<List<Project>> getAllUserActiveProjects() {
        return allUserActiveProjectsLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessageLiveData;
    }


    public void loadProjects() {
        projectRepository.getRecentActiveProjects(new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                allProjectsLiveData.setValue(projectList);
                // By default, filtered = all
                filteredProjectsLiveData.setValue(projectList);
            }

            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }

    public void loadUserActiveProjects() {
        projectRepository.getUserActiveProjects(new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                allUserActiveProjectsLiveData.setValue(projectList);
            }

            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }

    // Filter method by title, amount range, and category
    public void filterProjects(String query, int minAmount, int maxAmount, int categoryId) {
        List<Project> originalList = allProjectsLiveData.getValue();
        if (originalList == null) return;

        List<Project> newFilteredList = new ArrayList<>();
        for (Project p : originalList) {
            boolean matches = true;

            // 1. Search by title
            if (query != null && !query.isEmpty()) {
                if (!p.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    matches = false;
                }
            }

            // 2. Amount range
            int amount = p.getAmount();
            if (amount < minAmount || amount > maxAmount) {
                matches = false;
            }

            // 3. Category
            // If categoryId == 0, treat it as "All categories"
            if (categoryId != 0 && p.getCategoryId() != categoryId) {
                matches = false;
            }

            if (matches) {
                newFilteredList.add(p);
            }
        }
        filteredProjectsLiveData.setValue(newFilteredList);
    }

    // Method to add a project
    public void addProject(Project project) {
        projectRepository.addProject(project, new ProjectRepository.AddProjectCallback() {
            @Override
            public void onSuccess(String message) {
                successMessageLiveData.setValue("Project Added Successfully");

                // Reload the filtered list by adding the new project to the list
                List<Project> currentList = filteredProjectsLiveData.getValue();
                if (currentList != null) {
                    currentList.add(project); // Add the new project to the current list
                    filteredProjectsLiveData.setValue(currentList); // Notify observers
                }

                // Reset the success message after it's shown to avoid triggering the toast again
                resetSuccessMessage();
            }

            @Override
            public void onFailure(String errorMessage) {
                errorMessageLiveData.setValue(errorMessage);
            }
        });
    }


    public void resetSuccessMessage() {
        successMessageLiveData.postValue(null);
    }
}
