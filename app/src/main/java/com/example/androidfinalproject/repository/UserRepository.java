package com.example.androidfinalproject.repository;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.androidfinalproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UserRepository {
    private final String TAG = "USER REPO";
    private final String COLLECTION_NAME = "users";
    private final FirebaseFirestore db;

    public MutableLiveData<String> signInStatus = new MutableLiveData<>();
    public MutableLiveData<String> loggedInUserID = new MutableLiveData<>();
    public MutableLiveData<User> userDetail = new MutableLiveData<User>();

    public UserRepository(){
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(User user){
        try{
            db.collection(COLLECTION_NAME)
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Adding user not successful" );
                        }
                    });
        }catch(Exception ex){
            Log.e(TAG, "addUser: " + ex.toString());
            Log.e(TAG, "addUser: " + ex.getLocalizedMessage());
        }
    }

    public void getUser(String email, String password){
        this.signInStatus.postValue("Loading");
        try{
            db.collection(COLLECTION_NAME)
                    .whereEqualTo("email", email).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                if(task.getResult().getDocuments().size() != 0){
                                    if(task.getResult().getDocuments().get(0).toObject(User.class).getPassword().equals(password)){
                                        signInStatus.postValue("Success");
                                        String loggedInId = task.getResult().getDocuments().get(0).getId();
                                        loggedInUserID.postValue(loggedInId);
                                        Log.d(TAG, "onComplete: " + loggedInId);
                                    }
                                    else{
                                        signInStatus.postValue("NO");
                                    }
                                }
                                else{
                                    signInStatus.postValue("NO");
                                }
                            }else{
                                signInStatus.postValue("NO");
                            }
                        }
                    });
        }catch(Exception ex){
            Log.e(TAG, "getUser: Not success, " + ex.getLocalizedMessage());
            signInStatus.postValue("Failed");
        }
    }

    public void deleteUser(){}
    public void updateUser(){}

    public void getUserDetail(String userID){
        try{
            db.collection(COLLECTION_NAME)
                    .document(userID)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        if(doc.exists()){
                            Log.d(TAG, "DOC DATA: " + doc.getData());
                        }else{
                            Log.d(TAG, "No such document");
                        }
                    }else{
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
//                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                            Log.d(TAG, "onEvent: " + value.toString());
//                            if(error != null){
//                                Log.e(TAG, "onEvent: Listening failed" + error.getLocalizedMessage() );
//                                return;
//                            }
//                            User userInfo = new User();
//                            if(value != null){
//                                Log.d(TAG, "onEvent: " + value.toString());
//                            }
//                        }
//                    });
        }catch(Exception ex){
            Log.e(TAG, "getUserDetail: Failed");
        }
    }
}
