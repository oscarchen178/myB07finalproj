package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ViewOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        recyclerView = findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference allOrderRef = FirebaseDatabase.getInstance().getReference().child(ORDER);
        Query orderListRef;
        if (Current.isOwner) {
            orderListRef = allOrderRef.orderByChild("SID").equalTo(Current.currentStore);
        } else {
            orderListRef = allOrderRef.orderByChild("UID").equalTo(Current.currentUID);
        }
        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>().setQuery(orderListRef, Order.class).build();
        OrderAdapter adapter = new OrderAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}