package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutActivity extends AppCompatActivity {

    // tag for logging
    private static final String TAG = "LogoutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Button logout = (Button) findViewById(R.id.o_logout_bt);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutUser();
            }
        });

        Button delu = (Button) findViewById(R.id.del_user);
        delu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletionDialog();
            }
        });
    }

    /**
     * Logs out the current user
     */
    public void LogoutUser() {
        // logout
        FirebaseAuth.getInstance().signOut();

        // tell user logout successful
        Toast.makeText(LogoutActivity.this, "Logout Success",
                Toast.LENGTH_SHORT).show();

        // change current status
        Current.isOwner = false;
        Current.currentUID = "";
        Current.currentStore = "";

        // go to start activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.i(TAG, "Starting StartActivity");
    }

    /**
     * Asks User if they want to delete account
     */
    public void DeletionDialog() {

        // new dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(LogoutActivity.this);

        // message and title
        builder.setMessage("Deletion of accounts cannot be undone.")
                .setTitle("Delete your account?");

        // add buttons

        // delete account press OK
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            Log.i(TAG, "Beginning to delete account");

            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            // create current user object
            User user;
            if (firebaseUser.getDisplayName().equals(OWNER)) {
                user = new Owner();
            } else {
                user = new Customer();
            }
            user.getFromDB(firebaseUser.getUid());
            user.deleteUser(firebaseUser.getUid());

            // tell user that account deletion is successful
            Toast.makeText(LogoutActivity.this, "Deletion Success",
                    Toast.LENGTH_SHORT).show();

            // go back to startup page
            Log.i(TAG, "Sending Intent to Startup Activity");
            Intent intent = new Intent(LogoutActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        });

        // delete account press Cancel
        builder.setNegativeButton("Cancel",
                (dialogInterface, i) -> Log.i(TAG, "Cancelled Account Deletion"));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
