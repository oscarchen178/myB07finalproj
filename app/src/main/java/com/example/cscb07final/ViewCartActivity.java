package com.example.cscb07final;

import static com.example.cscb07final.Constants.CART;
import static com.example.cscb07final.Constants.ODITEM;
import static com.example.cscb07final.Constants.ORDER;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewCartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Button place_order = (Button) findViewById(R.id.place_order_bt);
        place_order.setOnClickListener(v -> {
            placeOrder();
            DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference()
                    .child(CART).child(Current.currentUID);
            cartListRef.removeValue();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference()
                .child(CART).child(Current.currentUID);
        FirebaseRecyclerOptions<CartItem> options =
                new FirebaseRecyclerOptions.Builder<CartItem>().setQuery(cartListRef, CartItem.class).build();
        CartItemAdapter adapter = new CartItemAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public void placeOrder() {
        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference()
                .child(CART).child(Current.currentUID);

        cartListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ArrayList<String> stores = new ArrayList<>();
                ArrayList<String> oids = new ArrayList<>();
                String OID;
                for(DataSnapshot snap: snapshot.getChildren()) {
                    CartItem cartItem = snap.getValue(CartItem.class);
                    if (stores.contains(cartItem.sID)) {
                        OID = oids.get(stores.indexOf(cartItem.sID));
                        Map<String, Object> update = new HashMap<>();
                        update.put(cartItem.iID, cartItem);
                        ref.child(ODITEM).child(OID).updateChildren(update);
                    } else {
                        stores.add(cartItem.sID);
                        DatabaseReference orderRef = ref.child(ORDER).push();
                        OID = orderRef.getKey();
                        oids.add(OID);
                        orderRef.setValue(new Order(OID, Current.currentUID, cartItem.sID));
                        Map<String, Object> update = new HashMap<>();
                        update.put(cartItem.iID, cartItem);
                        ref.child(ODITEM).child(OID).updateChildren(update);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}