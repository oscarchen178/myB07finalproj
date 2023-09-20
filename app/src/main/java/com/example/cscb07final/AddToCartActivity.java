package com.example.cscb07final;

import static com.example.cscb07final.Constants.ITEM;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddToCartActivity extends AppCompatActivity {

    private String amount;
    private TextView name, de, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        name = (TextView) findViewById(R.id.atc_item_name);
        de = (TextView) findViewById(R.id.atc_item_des);
        price = (TextView) findViewById(R.id.atc_item_price);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(ITEM)
                .child(Current.currentStore).child(Current.currentiID);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Item i = snapshot.getValue(Item.class);
                name.setText(i.name);
                de.setText(i.describe);
                price.setText(i.price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        EditText amountTxt = (EditText) findViewById(R.id.atc_set_amount);


        Button add_to_cart = (Button) findViewById(R.id.atc_bt);
        add_to_cart.setOnClickListener(v -> {
            amount = amountTxt.getText().toString();
            Item.addToCart(amount, Current.currentiID);
            Intent intent = new Intent(AddToCartActivity.this, StoreItemActivity.class);
            startActivity(intent);
        });
    }
}