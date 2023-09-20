package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OwnerInfoChange extends AppCompatActivity {

    // tag for logging
    private static final String TAG = "Store Setup Activity";

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_info_change);
    }

    /**
     * Add store to the DB
     */
    public void CreateStore(View view) {

        // get name
        EditText nameText = (EditText) findViewById(R.id.editTextTextPersonName2);
        String name = nameText.getText().toString();
        Log.i(TAG, "Name Retrieved: " + name);


        // get address line 1
        EditText add1Text = (EditText) findViewById(R.id.editTextTextPersonName3);
        String address = add1Text.getText().toString();
        Log.i(TAG, "Address Line 1 Retrieved: " + address);


        // add to DB
        Store store = new Store(name, address);
        store.updateStore();

        // tell user registration successful
        Toast.makeText(OwnerInfoChange.this, "Registration Success",
                Toast.LENGTH_SHORT).show();

        // go to logout activity
        // TODO: change to whatever page after registration

        //Oscar Changed this
        Log.i(TAG, "Starting MainActivity");
        Intent intent = new Intent(this, UserActivity.class);

        startActivity(intent);
        finish();

    }
}