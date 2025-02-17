package com.example.projectapp.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectapp.Model.Project;
import com.example.projectapp.Model.User;
import com.example.projectapp.Retrofit.ApiClient;
import com.example.projectapp.Retrofit.ApiInterface;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {

    private final ApiInterface apiInterface;
    private final Context context;

    // Modify the constructor to accept context
    public ProjectRepository(Context context) {
        // Use application context to avoid leaks
        this.context = context.getApplicationContext();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    // Callback interface for returning data
    public interface GetProjectsCallback {
        void onSuccess(List<Project> projectList);
        void onFailure(String errorMessage);
    }
    public interface GetAppliedUsersCallback {
        void onSuccess(List<User> users);
        void onFailure(String error);
    }
    public interface AcceptProjectApplicantCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }
    public void getProjectsByCategory(int categoryId, final GetProjectsCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<List<Project>> call = apiInterface.getProjectsByCategory("Bearer " + token, categoryId);
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if(response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch(IOException e) {
                            Log.e("API Error", "Error reading error body", e);
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public void acceptProjectApplicant(int projectId, int userId, final AcceptProjectApplicantCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<ResponseBody> call = apiInterface.acceptProjectApplicant("Bearer " + token, projectId, userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    try {
                        String msg = response.body().string();
                        callback.onSuccess(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response");
                    }
                } else {
                    String errorMsg = "Error: " + response.code();
                    if(response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    public interface DeclineProjectApplicantCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public void declineProjectApplicant(int projectId, int userId, final DeclineProjectApplicantCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<ResponseBody> call = apiInterface.declineProjectApplicant("Bearer " + token, projectId, userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    try {
                        String msg = response.body().string();
                        callback.onSuccess(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response");
                    }
                } else {
                    String errorMsg = "Error: " + response.code();
                    if(response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    public void getAppliedUsersForProject(int projectId, final GetAppliedUsersCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if(token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<List<User>> call = apiInterface.getAppliedUsersForProject("Bearer " + token, projectId);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if(response.errorBody() != null) {
                        try {
                            errorMsg = "Error: " + response.errorBody().string();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    // Fetch recent active projects
    public void getRecentActiveProjects(GetProjectsCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Make the API call, adding the token if it's available
        Call<List<Project>> call;
        if (token != null) {
            call = apiInterface.getRecentActiveProjects("Bearer " + token);
        } else {
            // You might want to handle the null token case differently,
            // but for now, we'll still call the API.
            call = apiInterface.getRecentActiveProjects("Bearer " + token);
        }

        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = "Error: empty response or server error.";
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", errorBody);
                            errorMessage = "Error: " + errorBody;
                        } catch (IOException e) {
                            Log.e("API Error", "Error reading error body", e);
                        }
                    }
                    Log.e("API Error", "Response Code: " + response.code());
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Log.e("Network Error", t.getMessage(), t);
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    // Fetch recent active projects
    public void getUserActiveProjects(GetProjectsCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Make the API call, adding the token if it's available
        Call<List<Project>> call;
        if (token != null) {
            call = apiInterface.getUserActiveProjects("Bearer " + token);
        } else {
            // You might want to handle the null token case differently,
            // but for now, we'll still call the API.
            call = apiInterface.getUserActiveProjects("Bearer " + token);
        }

        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = "Error: empty response or server error.";
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("API Error", errorBody);
                            errorMessage = "Error: " + errorBody;
                        } catch (IOException e) {
                            Log.e("API Error", "Error reading error body", e);
                        }
                    }
                    Log.e("API Error", "Response Code: " + response.code());
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Log.e("Network Error", t.getMessage(), t);
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    public void getUserAppliedProjects(final GetProjectsCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if(token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<List<Project>> call = apiInterface.getUserAppliedProjects("Bearer " + token);
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if(response.errorBody() != null) {
                        try {
                            errorMsg = "Error: " + response.errorBody().string();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    // Get Favorited Projects
    public void getFavoritedProjects(final GetProjectsCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if(token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<List<Project>> call = apiInterface.getFavoritedProjects("Bearer " + token);
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if(response.errorBody() != null) {
                        try {
                            errorMsg = "Error: " + response.errorBody().string();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    // Get Rejected Projects
    public void getRejectedProjects(final GetProjectsCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if(token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<List<Project>> call = apiInterface.getRejectedProjects("Bearer " + token);
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if(response.errorBody() != null) {
                        try {
                            errorMsg = "Error: " + response.errorBody().string();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }


    // Get Archived Projects
    public void getUserArchivedProjects(final GetProjectsCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if(token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<List<Project>> call = apiInterface.getUserArchivedProjects("Bearer " + token);
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if(response.errorBody() != null) {
                        try {
                            errorMsg = "Error: " + response.errorBody().string();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    public void getUserAcceptedProjects(final GetProjectsCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if(token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<List<Project>> call = apiInterface.getAcceptedProjects("Bearer " + token);
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if(response.errorBody() != null) {
                        try {
                            errorMsg = "Error: " + response.errorBody().string();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    public interface UnarchiveProjectCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public void unarchiveProject(int projectId, UnarchiveProjectCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<ResponseBody> call = apiInterface.unarchiveProject("Bearer " + token, projectId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        callback.onSuccess(responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response");
                    }
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if (response.errorBody() != null) {
                        try {
                            errorMsg = "Error: " + response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    public interface ArchiveProjectCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public void archiveProject(int projectId, ArchiveProjectCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<ResponseBody> call = apiInterface.archiveProject("Bearer " + token, projectId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        callback.onSuccess(responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response");
                    }
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if (response.errorBody() != null) {
                        try {
                            errorMsg = "Error: " + response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    // In UserRepository.java (or ProjectRepository.java if preferred)
    public interface AddToFavCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public void addProjectToFav(int projectId, final AddToFavCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        Call<ResponseBody> call = apiInterface.addProjectToFav("Bearer " + token, projectId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String msg = response.body().string();
                        callback.onSuccess(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response");
                    }
                } else {
                    String errorMsg = "Error: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    public interface RemFromFavCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public void remProjectFromFav(int projectId, final RemFromFavCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }
        // Call the DELETE endpoint for remove favorite
        Call<ResponseBody> call = apiInterface.remProjectFromFav("Bearer " + token, projectId);
        // If your route for remove favorite is different, change it accordingly:
        // For example: apiInterface.remProjectFromFav("Bearer " + token, projectId)
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String msg = response.body().string();
                        callback.onSuccess(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Error parsing response");
                    }
                } else {
                    String errorMsg = "Error: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }
    public void getFilteredProjects(String query, int minAmount, int maxAmount, int categoryId, final GetProjectsCallback callback) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token == null) {
            callback.onFailure("Token not found");
            return;
        }

        Call<List<Project>> call = apiInterface.getFilteredProjects("Bearer " + token, query, minAmount, maxAmount, categoryId);
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMsg = "Error: empty response or server error.";
                    if (response.errorBody() != null) {
                        try {
                            errorMsg = response.errorBody().string();
                        } catch (IOException e) {
                            Log.e("API Error", "Error reading error body", e);
                        }
                    }
                    callback.onFailure(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }






        // Fetch project by ID
    public void getProjectById(int projectId, GetProjectByIdCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token == null) {
            callback.onFailure("Token is missing. Please log in again.");
            return;
        }

        // Convert projectId to String before passing to the API
        String projectIdStr = String.valueOf(projectId);
        Call<Project> call = apiInterface.getProjectById("Bearer " + token, projectIdStr); // Pass the projectId as a String

        call.enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    String errorMessage = "Error: Unable to fetch project.";
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            Log.e("API Error", "Error Response Body: " + errorMessage);
                        }
                        Log.e("API Error", "Response Code: " + response.code());
                    } catch (IOException e) {
                        Log.e("API Error", "Error reading error body", e);
                    }
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                Log.e("Network Error", "Network failure: " + t.getMessage(), t);
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    // Callback interface for getProjectById
    public interface GetProjectByIdCallback {
        void onSuccess(Project project);
        void onFailure(String errorMessage);
    }








    public void addProject(Project project, AddProjectCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token == null) {
            callback.onFailure("Token is missing. Please log in again.");
            return;
        }

        Call<ResponseBody> call = apiInterface.addProject("Bearer " + token, project);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Success
                    callback.onSuccess("Project added successfully!");
                    Log.d("ProjectRepository", "Project added successfully.");
                } else {
                    // Log response code and body for debugging
                    String errorMessage = "Error: Something went wrong.";
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                            Log.e("API Error", "Error Response Body: " + errorMessage);
                        }
                        Log.e("API Error", "Response Code: " + response.code());
                    } catch (IOException e) {
                        Log.e("API Error", "Error reading error body", e);
                    }
                    callback.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log failure exception
                Log.e("Network Error", "Network failure: " + t.getMessage(), t);
                callback.onFailure("Network error: " + t.getMessage());
            }
        });
    }

    // Callback interface
    public interface AddProjectCallback {
        void onSuccess(String message);
        void onFailure(String errorMessage);
    }

}