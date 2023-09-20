package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Item {

    public String name;
    public String price;
    public String describe;
    public String iID;

    public Item() {

    }

    public Item(String name, String price, String describe, String iID) {
        this.name = name;
        this.price = price;
        this.describe = describe;
        this.iID = iID;
    }

    public Item(String name, String price, String describe) {
        this.name = name;
        this.price = price;
        this.describe = describe;
    }

    public void storeItem(String store) {
        DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference().child(ITEM).child(store);
        DatabaseReference pRef = storeRef.push();
        iID = pRef.getKey();
        pRef.setValue(new Item(name, price, describe, iID));
    }

    public static void addToCart(String amount, String iID) {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child(CART)
                .child(Current.currentUID);
        cartRef.child(iID).setValue(new CartItem(Current.currentStore, iID, amount));
    }

}
