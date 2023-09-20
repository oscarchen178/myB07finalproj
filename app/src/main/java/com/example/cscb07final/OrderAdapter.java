package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderAdapter extends FirebaseRecyclerAdapter<Order, OrderAdapter.OrderViewHolder> {
    String name;
    String des;

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, des;

        public OrderViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.item_list_name);
            des = (TextView) view.findViewById(R.id.item_list_des);
        }

    }

    public OrderAdapter(FirebaseRecyclerOptions<Order> options) {
        super(options);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storelist_items, viewGroup, false);

        return new OrderViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder viewHolder, int position, @NonNull final Order model) {
        DatabaseReference ref;
        if (Current.isOwner) {
            ref = FirebaseDatabase.getInstance().getReference().child(CUSTOMER).child(model.UID);
        } else {
            ref = FirebaseDatabase.getInstance().getReference().child(STORE).child(model.SID);
        }

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child(NAME).getValue(String.class);
                if (!Current.isOwner) des = snapshot.child(ADDRESS).child(ADD1).getValue(String.class);
                else des = snapshot.child(EMAIL).getValue(String.class);
                viewHolder.name.setText(name);
                viewHolder.des.setText(des);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ViewOrderItemActivity.class);
            intent.putExtra("OID", model.OID);
            v.getContext().startActivity(intent);
        });
    }


}
