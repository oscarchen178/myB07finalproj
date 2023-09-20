package com.example.cscb07final;

public interface User {
    /*
    Represents the application user to be implemented by com.example.cscb07final.Customer and com.example.cscb07final.Owner
     */

    // add new user to Firebase DB
    void addNewUser(String userID);

    // delete user from Firebase DB and Auth
    void deleteUser(String userID);

    // update user name
    void updateName(String userID, String name);

    // update user email
    void updateEmail(String userID, String email);

    // get user info from DB and update current user
    void getFromDB(String userID);

    String getName();

    String getEmail();

}
