package com.example.cscb07final;

import static com.example.cscb07final.Constants.STORE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllStoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_store);

        Button back = findViewById(R.id.back_to_user_bt);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(AllStoreActivity.this, UserActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.store_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference storeListRef = FirebaseDatabase.getInstance().getReference()
                .child(STORE);
        FirebaseRecyclerOptions<Store> options =
                new FirebaseRecyclerOptions.Builder<Store>().setQuery(storeListRef, Store.class).build();
        StoreAdapter adapter = new StoreAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}