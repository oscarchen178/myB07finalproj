package com.example.cscb07final;

import static java.lang.Thread.sleep;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel implements LoginContract.LoginModel{

    // tag for logging
    private static final String TAG = "LoginModel";

    // declare instance of FirebaseAuth
    private FirebaseAuth firebaseAuth;

    public LoginModel(){
        // initializing Firebase Auth and login presenter
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public String loginSuccessful() throws InterruptedException {

        sleep(3000);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null) {
            return "Welcome";
        }
        return "";
    }

    @Override
    public void loginAuth(String email, String password){

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        Log.i(TAG, "Login Successful");

                    } else {
                        Log.i(TAG, "Login Failed");

                    }
                });
    }

}
