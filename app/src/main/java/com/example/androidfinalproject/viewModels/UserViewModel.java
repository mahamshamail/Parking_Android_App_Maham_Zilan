package com.example.androidfinalproject.viewModels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.androidfinalproject.model.User;
import com.example.androidfinalproject.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private final String TAG = "USER VIEW MODEL";
    private static final UserViewModel instance = new UserViewModel();
    private final UserRepository userRepository = new UserRepository();
    public static UserViewModel getInstance(){
        return instance;
    }
    public UserRepository getUserRepository(){
        return userRepository;
    }
    public UserViewModel(){}

    public Boolean addNewUser(User user){
        Log.d(TAG, "addNewUser: " + user.getFullName());
        this.userRepository.addUser(user);
        if(this.userRepository.loggedInUserID != null){
            return true;
        }
        return false;
    }
    public void userLogIn(String email, String password){
        if(!email.isEmpty()){
            if(!password.isEmpty()){
                this.userRepository.getUser(email, password);
            }
        }
    }
    public void deleteUser(){}
    public void updateUser(){}
    public void getUserInfo(String userID){
        Log.d(TAG, "getUserInfo: " + userID);
        this.userRepository.getUserDetail(userID);
    }
}
