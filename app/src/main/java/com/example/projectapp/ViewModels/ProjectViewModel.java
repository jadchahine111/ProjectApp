package com.example.projectapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectapp.Model.Project;
import com.example.projectapp.Model.User;
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
    private final MutableLiveData<List<User>> appliedUsersLiveData = new MutableLiveData<>();

    private final MutableLiveData<Project> projectDetails = new MutableLiveData<>();


    // User Active Projects
    private final MutableLiveData<List<Project>> allUserActiveProjectsLiveData = new MutableLiveData<>();

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
        loadUserAcceptedProjects();
    }

    // Getter methods for LiveData
    public LiveData<List<Project>> getFilteredProjects() {
        return filteredProjectsLiveData;
    }
    public LiveData<List<User>> getAppliedUsersLiveData() { return appliedUsersLiveData; }

    public MutableLiveData<List<Project>> getAllUserActiveProjectsLiveData() {
        return allUserActiveProjectsLiveData;
    }

    public MutableLiveData<List<Project>> getRejectedProjectsLiveData() {
        return rejectedProjectsLiveData;
    }

    public MutableLiveData<List<Project>> getFavoritedProjectsLiveData() {
        return favoritedProjectsLiveData;
    }

    public MutableLiveData<List<Project>> getAppliedProjectsLiveData() {
        return appliedProjectsLiveData;
    }

    public MutableLiveData<List<Project>> getActiveProjectsLiveData() {
        return activeProjectsLiveData;
    }

    public MutableLiveData<String> getSuccessMessageLiveData() {
        return successMessageLiveData;
    }

    public MutableLiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public MutableLiveData<List<Project>> getFilteredProjectsLiveData() {
        return filteredProjectsLiveData;
    }

    public MutableLiveData<List<Project>> getAllProjectsLiveData() {
        return allProjectsLiveData;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public LiveData<List<Project>> getAllUserActiveProjects() {
        return allUserActiveProjectsLiveData;
    }

    public LiveData<Project> getProjectDetails() {
        return projectDetails;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessageLiveData;
    }


    public LiveData<List<Project>> getArchivedProjectsLiveData() {
        return archivedProjectsLiveData;
    }
    public LiveData<List<Project>> getAcceptedProjectsLiveData() {
        return acceptedProjectsLiveData;
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
    public void loadAppliedUsersForProject(int projectId) {
        projectRepository.getAppliedUsersForProject(projectId, new ProjectRepository.GetAppliedUsersCallback() {
            @Override
            public void onSuccess(List<User> users) {
                appliedUsersLiveData.setValue(users);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }
    public void loadFilteredProjects(String query, int minAmount, int maxAmount, int categoryId) {
        projectRepository.getFilteredProjects(query, minAmount, maxAmount, categoryId, new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                filteredProjectsLiveData.setValue(projectList);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }
    public void acceptProjectApplicant(int projectId, int userId, final AcceptCallback successCallback, final FailureCallback failureCallback) {
        projectRepository.acceptProjectApplicant(projectId, userId, new ProjectRepository.AcceptProjectApplicantCallback() {
            @Override
            public void onSuccess(String message) {
                successCallback.onSuccess(message);
            }
            @Override
            public void onFailure(String error) {
                failureCallback.onFailure(error);
            }
        });
    }

    // New: Decline project applicant
    public void declineProjectApplicant(int projectId, int userId, final AcceptCallback successCallback, final FailureCallback failureCallback) {
        projectRepository.declineProjectApplicant(projectId, userId, new ProjectRepository.DeclineProjectApplicantCallback() {
            @Override
            public void onSuccess(String message) {
                successCallback.onSuccess(message);
            }
            @Override
            public void onFailure(String error) {
                failureCallback.onFailure(error);
            }
        });
    }
    public interface AcceptCallback {
        void onSuccess(String message);
    }
    public interface FailureCallback {
        void onFailure(String error);
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
    public void loadProjectsByCategory(int categoryId) {
        projectRepository.getProjectsByCategory(categoryId, new ProjectRepository.GetProjectsCallback() {
            @Override
            public void onSuccess(List<Project> projectList) {
                filteredProjectsLiveData.setValue(projectList);
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


    public void fetchProjectDetails(int projectId) {
        projectRepository.getProjectById(projectId, new ProjectRepository.GetProjectByIdCallback() {
            @Override
            public void onSuccess(Project project) {
                projectDetails.postValue(project);
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
