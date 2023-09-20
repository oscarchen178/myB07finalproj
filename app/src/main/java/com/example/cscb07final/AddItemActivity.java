package com.example.cscb07final;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        EditText itemName = (EditText) findViewById(R.id.editItemName);
        EditText itemDes = (EditText) findViewById(R.id.editItemDes);
        EditText itemPrice = (EditText) findViewById(R.id.editItemPrice);

        Button addItem = findViewById(R.id.AddItem);
        addItem.setOnClickListener(v -> {
            Item i = new Item(itemName.getText().toString(),
                              itemPrice.getText().toString(),
                              itemDes.getText().toString());
            i.storeItem(Current.currentStore);
            Intent intent = new Intent(AddItemActivity.this, StoreItemActivity.class);
            startActivity(intent);
        });
    }
}