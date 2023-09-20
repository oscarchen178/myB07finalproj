package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Store {
    public String Name;
    public String Address;
    public String sid;

    public Store() {

    }

    public Store(String name) {
        this.Name = name;
        this.Address = "";
        this.sid = Current.currentUID;
    }

    public Store(String name, String location) {
        this.Name = name;
        this.Address = location;
        this.sid = Current.currentUID;
    }

    public void updateStore(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> info = new HashMap<>();
        info.put(NAME, Name);
        info.put(ADDRESS, Address);
        info.put("sid", sid);

        databaseReference.child(STORE).child(sid).setValue(info);
    }
}
