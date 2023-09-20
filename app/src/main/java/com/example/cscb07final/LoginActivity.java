package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView{

    // presenter for MVP
    private LoginContract.LoginPresenter presenter;

    // tag for logging
    private static final String TAG = "LoginActivity";

    // declare instance of FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this, new LoginModel());

        // initializing Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        Log.i(TAG, "LoginActivity Created");
    }

    /**
     * Gets email and password
     */
    @Override
    public String getEmail(){
        // get email
        EditText editText1 = findViewById(R.id.editTextTextEmailAddress);
        String email = editText1.getText().toString();
        Log.i(TAG, "Email Retrieved");

        return email;
    }

    @Override
    public String getPassword(){
        // get password
        EditText editText2 = findViewById(R.id.editTextTextPassword);
        String password = editText2.getText().toString();
        Log.i(TAG, "Password Retrieved");

        return password;
    }

    @Override
    public void displayMessage(String message){
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeActivity(){
        Current.currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Current.isOwner = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().equals(OWNER);

        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
        startActivity(intent);
        Log.i(TAG, "Starting UserActivity");
    }

    public void handleLoginClick(View view) throws InterruptedException {
        presenter.loginUser();
    }
}