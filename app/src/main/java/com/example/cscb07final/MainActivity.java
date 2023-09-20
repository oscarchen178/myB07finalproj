package com.example.cscb07final;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.login_button);
        Button register = (Button) findViewById(R.id.register_button);
        login.setOnClickListener(v -> SignInPage());

        register.setOnClickListener(v -> RegisterPage());
    }

    /**
     * Called when user taps Sign In Button.
     * Go to Login page
     */
    public void SignInPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Log.i(TAG, "Starting LoginActivity");
    }

    /**
     * Called when user taps Register Button.
     * Go to Register page
     */
    public void RegisterPage(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        Log.i(TAG, "Starting RegisterActivity");
    }
}