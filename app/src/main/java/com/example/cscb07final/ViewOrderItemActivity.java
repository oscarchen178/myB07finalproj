package com.example.cscb07final;

import static com.example.cscb07final.Constants.ISCOM;
import static com.example.cscb07final.Constants.ODITEM;
import static com.example.cscb07final.Constants.ORDER;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewOrderItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String OID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_item);
        recyclerView = findViewById(R.id.o_item_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        OID = intent.getStringExtra("OID");
        Button cmplt_bt = (Button) findViewById(R.id.cmplt_bt);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(ORDER).child(OID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("isComplete").getValue(Boolean.class)) cmplt_bt.setText("Order Completed");
                else cmplt_bt.setText("Order Not Completed");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        cmplt_bt.setOnClickListener(v -> {
            if (Current.isOwner) {
                if (cmplt_bt.getText().equals("Order Completed"))
                    ref.child(ISCOM).setValue(false);
                else
                    ref.child(ISCOM).setValue(true);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference itemListRef = FirebaseDatabase.getInstance().getReference()
                .child(ODITEM).child(OID);
        FirebaseRecyclerOptions<CartItem> options =
                new FirebaseRecyclerOptions.Builder<CartItem>().setQuery(itemListRef, CartItem.class).build();
        CartItemAdapter adapter = new CartItemAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}