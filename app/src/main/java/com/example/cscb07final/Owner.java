package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Owner implements User {
    /*
    com.example.cscb07final.User that represents a store owner
     */

    private DatabaseReference databaseReference;
    private static final String TAG = "Owner Object";

    private String name;
    private String email;

    public Owner(){
    }

    public Owner(String name, String email){
        this.name = name;
        this.email = email;
    }

    // add new user to Firebase DB
    @Override
    public void addNewUser(String userID){
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> info = new HashMap<>();
        info.put(NAME, name);
        info.put(EMAIL, email);

        databaseReference.child(OWNER).child(userID).setValue(info);
    }

    // delete user from Firebase Auth and DB
    @Override
    public void deleteUser(String userID){

        // remove from DB
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(OWNER).child(userID).removeValue();
        databaseReference.child(STORE).child(userID).removeValue();

        // remove from Auth
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.i(TAG, "Account Deletion Success");
                    } else{
                        Log.i(TAG, "Account Deletion Failure");
                    }
                });
    }

    // update values of user
    @Override
    public void updateName(String userID, String name){

        // change object
        this.name = name;

        // change DB
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(OWNER).child(userID).child(NAME).setValue(name);
    }

    @Override
    public void updateEmail(String userID, String email){

        // change object
        this.email = email;

        // change DB
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(OWNER).child(userID).child(EMAIL).setValue(email);

        // change in Auth
        FirebaseAuth.getInstance().getCurrentUser().updateEmail(email);
    }

    @Override
    public void getFromDB(String userID){

        // get current DB
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(OWNER).child(userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                // get values from DB and then update current object
                HashMap<String, String> values = (HashMap<String, String>) task.getResult().getValue();
                this.name = values.get(NAME);
                this.email = values.get(EMAIL);

                Log.i(TAG, "owner update from DB SUCCESS");

            } else{
                Log.i(TAG, "owner update from DB FAILURE");
            }
        });

    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getEmail(){
        return email;
    }

}
