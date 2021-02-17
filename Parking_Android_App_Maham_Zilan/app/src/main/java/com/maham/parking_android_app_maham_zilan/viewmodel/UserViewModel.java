//Group 10
//101328732 Saiyeda Maham Shamail
//101226318 Zilan Ouyang
package com.maham.parking_android_app_maham_zilan.viewmodel;

import androidx.lifecycle.ViewModel;

import com.maham.parking_android_app_maham_zilan.model.User;
import com.maham.parking_android_app_maham_zilan.repostory.UserRepository;

public class UserViewModel extends ViewModel {
    private static final UserViewModel ourInstance = new UserViewModel();
    private final UserRepository userRepository = new UserRepository();

    public static UserViewModel getInstance(){
        return ourInstance;
    }

    private UserViewModel(){
    }

    public void addUser(User user){
        this.userRepository.addUser(user);
    }

    public UserRepository getUserRepository(){
        return userRepository;
    }

    public void validateUser(String email, String password){
        if (!email.isEmpty()){
            if (!password.isEmpty()){
                this.userRepository.getUser(email, password);
            }
        }
    }
    public void updateUser(String userId, String name, String email, String contact, String carPlate, String password){
        this.userRepository.updateUserName(userId,name);
        this.userRepository.updateUserCarPlateNumber(userId, carPlate);
        this.userRepository.updateUserContactNumber(userId, contact);
        this.userRepository.updateUserEmail(userId, email);
    }
    public void updateUserName(String userId, String name){
        this.userRepository.updateUserName(userId,name);
    }
    public void updateUserEmail(String userId, String email){
        this.userRepository.updateUserEmail(userId,email);
    }
    public void updateUserContactNumber(String userId, String contact){
        this.userRepository.updateUserContactNumber(userId, contact);
    }
    public void updateUserPlate(String userId, String plate){
        this.userRepository.updateUserCarPlateNumber(userId, plate);
    }
    public void deleteUser(String userId){
        this.userRepository.deleteUser(userId);
    }
}