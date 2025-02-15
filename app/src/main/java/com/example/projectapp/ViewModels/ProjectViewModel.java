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

    // LiveData for recent active projects (all projects) and filtered projects
    private final MutableLiveData<List<Project>> allProjectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> filteredProjectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> successMessageLiveData = new MutableLiveData<>();

    // LiveData for different user project categories
    private final MutableLiveData<List<Project>> activeProjectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> appliedProjectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> favoritedProjectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> rejectedProjectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> acceptedProjectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> archivedProjectsLiveData = new MutableLiveData<>();

    public ProjectViewModel() {
        projectRepository = new ProjectRepository(MyApplication.getAppContext());
        // Load projects for the default section (e.g., recent active projects)
        loadProjects();

        // Load different categories for the user
        loadUserActiveProjects();
        loadUserAppliedProjects();
        loadUserFavoritedProjects();
        loadUserRejectedProjects();
        loadUserArchivedProjects();
    }

    // Getter methods for LiveData
    public LiveData<List<Project>> getFilteredProjects() {
        return filteredProjectsLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessageLiveData;
    }

    public LiveData<List<Project>> getActiveProjectsLiveData() {
        return activeProjectsLiveData;
    }

    public LiveData<List<Project>> getAppliedProjectsLiveData() {
        return appliedProjectsLiveData;
    }
    public LiveData<List<Project>> getAcceptedProjectsLiveData() {
        return acceptedProjectsLiveData;
    }

    public LiveData<List<Project>> getFavoritedProjectsLiveData() {
        return favoritedProjectsLiveData;
    }

    public LiveData<List<Project>> getRejectedProjectsLiveData() {
        return rejectedProjectsLiveData;
    }

    public LiveData<List<Project>> getArchivedProjectsLiveData() {
        return archivedProjectsLiveData;
    }

    // Load all recent active projects (for default listing and filtering)
    public void loadProjects() {
        projectRepository.getRecentActiveProjects(new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                allProjectsLiveData.setValue(projectList);
                // By default, filtered projects = all recent active projects
                filteredProjectsLiveData.setValue(projectList);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }

    // Load user active projects
    public void loadUserActiveProjects() {
        projectRepository.getUserActiveProjects(new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                activeProjectsLiveData.setValue(projectList);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }

    // Load user applied projects
    public void loadUserAppliedProjects() {
        projectRepository.getUserAppliedProjects(new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                appliedProjectsLiveData.setValue(projectList);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }

    // Load user favorited projects
    public void loadUserFavoritedProjects() {
        projectRepository.getFavoritedProjects(new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                favoritedProjectsLiveData.setValue(projectList);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }

    // Load user rejected projects
    public void loadUserRejectedProjects() {
        projectRepository.getRejectedProjects(new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                rejectedProjectsLiveData.setValue(projectList);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }


    // Load user archived projects
    public void loadUserArchivedProjects() {
        projectRepository.getUserArchivedProjects(new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                archivedProjectsLiveData.setValue(projectList);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }
    public void loadUserAcceptedProjects() {
        projectRepository.getUserAcceptedProjects(new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                acceptedProjectsLiveData.setValue(projectList);
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

            // Search by title
            if (query != null && !query.isEmpty()) {
                if (!p.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    matches = false;
                }
            }

            // Amount range filtering
            int amount = p.getAmount();
            if (amount < minAmount || amount > maxAmount) {
                matches = false;
            }

            // Category filtering (if categoryId==0, then it's "all")
            if (categoryId != 0 && p.getCategoryId() != categoryId) {
                matches = false;
            }

            if (matches) {
                newFilteredList.add(p);
            }
        }
        filteredProjectsLiveData.setValue(newFilteredList);
    }
    public void unarchiveProject(int projectId) {
        projectRepository.unarchiveProject(projectId, new ProjectRepository.UnarchiveProjectCallback() {
            @Override
            public void onSuccess(String message) {
                // Optionally update success message LiveData or refresh the archived projects list.
                successMessageLiveData.setValue(message);
                // Refresh archived projects list so the unarchived project is removed:
                loadUserArchivedProjects();
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }
    public void archiveProject(int projectId) {
        projectRepository.archiveProject(projectId, new ProjectRepository.ArchiveProjectCallback() {
            @Override
            public void onSuccess(String message) {
                // Optionally set success message LiveData
                successMessageLiveData.setValue(message);
                // Refresh posted projects to remove the archived project from the list
                loadUserActiveProjects();
            }

            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }
    public void addProjectToFav(Project project) {
        projectRepository.addProjectToFav(project.getId(), new ProjectRepository.AddToFavCallback() {
            @Override
            public void onSuccess(String message) {
                // Optionally update LiveData to show success message or refresh list
                successMessageLiveData.setValue(message);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }
    public void remProjectFromFav(int projectId) {
        projectRepository.remProjectFromFav(projectId, new ProjectRepository.RemFromFavCallback() {
            @Override
            public void onSuccess(String message) {
                successMessageLiveData.setValue(message);
                // Refresh the favorites list so the removed project disappears
                loadUserFavoritedProjects();
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }




    // Method to add a project
    public void addProject(Project project) {
        projectRepository.addProject(project, new ProjectRepository.AddProjectCallback() {
            @Override
            public void onSuccess(String message) {
                successMessageLiveData.setValue("Project Added Successfully");
                // Optionally add the project to the current filtered list
                List<Project> currentList = filteredProjectsLiveData.getValue();
                if (currentList != null) {
                    currentList.add(project);
                    filteredProjectsLiveData.setValue(currentList);
                }
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
