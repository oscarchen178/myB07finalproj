package com.example.cscb07final;

import static com.example.cscb07final.Constants.ITEM;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StoreItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_item);
        recyclerView = findViewById(R.id.item_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Button add_item = findViewById(R.id.add_item);
        add_item.setOnClickListener(v -> {
            if(Current.isOwner) {
                Intent intent = new Intent(StoreItemActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        Button back = findViewById(R.id.back_to_store_bt);
        back.setOnClickListener(v -> {
            Intent intent;
            if (Current.isOwner) {
                intent = new Intent(StoreItemActivity.this, UserActivity.class);
            } else {
                intent = new Intent(StoreItemActivity.this, AllStoreActivity.class);
            }
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference()
                .child(ITEM).child(Current.currentStore);
        FirebaseRecyclerOptions<Item> options =
                new FirebaseRecyclerOptions.Builder<Item>().setQuery(storeRef, Item.class).build();
        ItemAdapter adapter = new ItemAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}