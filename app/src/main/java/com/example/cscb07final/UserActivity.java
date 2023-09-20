package com.example.cscb07final;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button1;
        Button button2;
        Button button3;
        if (Current.isOwner) {
            setContentView(R.layout.activity_owner);
            button1 = findViewById(R.id.man_st_item);
            button2 = findViewById(R.id.view_st_ord);

            button1.setOnClickListener(v -> {
                Current.currentStore = Current.currentUID;
                Intent intent = new Intent(UserActivity.this, StoreItemActivity.class);
                startActivity(intent);
            });
            button2.setOnClickListener(v -> {
                Intent intent = new Intent(UserActivity.this, ViewOrderActivity.class);
                startActivity(intent);
            });
            Button logout1 = findViewById(R.id.o_logout_bt);
            logout1.setOnClickListener(v -> {
                Intent intent = new Intent(UserActivity.this, LogoutActivity.class);
                startActivity(intent);
            });
        }
        else {
            setContentView(R.layout.activity_customer);
            button1 = findViewById(R.id.start_shopp);
            button2 = findViewById(R.id.view_cart);
            button3 = findViewById(R.id.cus_view_order);

            button1.setOnClickListener(v -> {
                Intent intent = new Intent(UserActivity.this, AllStoreActivity.class);
                startActivity(intent);
            });
            button2.setOnClickListener(v -> {
                Intent intent = new Intent(UserActivity.this, ViewCartActivity.class);
                startActivity(intent);
            });
            button3.setOnClickListener(v -> {
                Intent intent = new Intent(UserActivity.this, ViewOrderActivity.class);
                startActivity(intent);
            });

            Button logout2 = findViewById(R.id.u_logout_bt);
            logout2.setOnClickListener(v -> {
                Intent intent = new Intent(UserActivity.this, LogoutActivity.class);
                startActivity(intent);
            });
        }
    }
}