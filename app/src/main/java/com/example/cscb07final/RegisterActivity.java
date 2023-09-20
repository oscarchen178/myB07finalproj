package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    /**
     * Activity for Registering users
     */

    // tag for logging
    private static final String TAG = "RegisterActivity";

    // declare instance of FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // if user is logged in, log out
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseAuth.getInstance().signOut();
        }

        // initializing Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        Log.i(TAG, "RegisterActivity Created");
    }

    /**
     * Register user with Firebase Auth and place in DB
     */
    public void RegisterUser(View view){

        // get name
        EditText editText0 = (EditText) findViewById(R.id.editTextTextPersonName);
        String name = editText0.getText().toString();
        Log.i(TAG, "Name Retrieved: " + name);

        // get email
        EditText editText1 = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        String email = editText1.getText().toString();
        Log.i(TAG, "Email Retrieved: " + email);

        // get password
        EditText editText2 = (EditText) findViewById(R.id.editTextTextPassword2);
        String password = editText2.getText().toString();
        Log.i(TAG, "Password Retrieved");

        // get spinner value (owner or customer)
        Spinner spinner = (Spinner)findViewById(R.id.register_spinner);
        String accountType = String.valueOf(spinner.getSelectedItem());
        Log.i(TAG, "Account Type Retrieved: "+accountType);


        // reject passwords less than 6 characters
        if(password.length() >= 6) {

            // add user to Firebase Auth and get userID
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // successful registration

                            Log.i(TAG, "Created user with Firebase Auth successful");

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser != null) {

                                // change display name to either Owner or Customer
                                UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(accountType)
                                        .build();

                                firebaseUser.updateProfile(changeRequest).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.i(TAG, "Display Name changed to " + accountType);
                                    } else {
                                        Log.i(TAG, "Display Name failed to change");
                                    }
                                });

                                // get user ID
                                String userID = firebaseUser.getUid();
                                Log.i(TAG, "User ID Retrieved: " + userID);

                                // add to DB
                                User user;
                                if (accountType.equals(OWNER)) {
                                    // owner account
                                    user = new Owner(name, email);

                                } else {
                                    // customer account
                                    user = new Customer(name, email);
                                }

                                // add to DB
                                user.addNewUser(userID);

                                // if OWNER go to store setup, otherwise go to main page
                                if(accountType.equals(OWNER)) {

                                    // go to store setup
                                    Log.i(TAG, "Starting Store Setup Page");
                                    Intent intent = new Intent(this, OwnerInfoChange.class);

                                    //Oscar changed this
                                    Current.currentUID = userID;
                                    Current.isOwner = true;
                                    Current.currentStore = userID;
                                    startActivity(intent);
                                    finish();

                                } else{

                                    // tell user registration successful
                                    Toast.makeText(RegisterActivity.this, "Registration Success",
                                            Toast.LENGTH_SHORT).show();

                                    // go to logout activity
                                    // TODO: change to whatever page after registration

                                    //Oscar Changed this
                                    Log.i(TAG, "Starting UserActivity");
                                    Current.currentUID = userID;
                                    Current.isOwner = false;
                                    Intent intent = new Intent(this, UserActivity.class);

                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                // account creation unsuccessful
                                Log.i(TAG, "Registration failed");
                                Toast.makeText(RegisterActivity.this, "Registration Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // failed registration
                            Log.i(TAG, "Registration failed", task.getException());
                        }
                    });
        } else{
            // password less than 6 characters
            Log.i(TAG, "Registration failed: password less than 6 characters");
            Toast.makeText(RegisterActivity.this, "password has 6 character minimum",
                    Toast.LENGTH_SHORT).show();
        }
    }

}