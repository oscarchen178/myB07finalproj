package com.example.cscb07final;

import static com.example.cscb07final.Constants.*;

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

public class CartItemAdapter extends FirebaseRecyclerAdapter<CartItem, CartItemAdapter.CartItemViewHolder> {

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, price, des, amount;

        public CartItemViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.item_list_name);
            price = (TextView) view.findViewById(R.id.item_list_price);
            des = (TextView) view.findViewById(R.id.item_list_des);
            amount = (TextView) view.findViewById(R.id.item_cart_amount);
        }

    }

    public CartItemAdapter(FirebaseRecyclerOptions<CartItem> options) {
        super(options);
    }


    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storelist_items, viewGroup, false);

        return new CartItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull CartItemViewHolder viewHolder, int position, final CartItem model) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(ITEM)
                .child(model.sID).child(model.iID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Item i = snapshot.getValue(Item.class);
                assert i != null;
                viewHolder.name.setText(i.name);
                viewHolder.price.setText(i.price);
                viewHolder.des.setText(i.describe);
                viewHolder.amount.setText(model.amount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Current.currentiID = model.iID;
//                if (!Current.isOwner)
//                    v.getContext().startActivity(new Intent(v.getContext(), AddToCartActivity.class));
//            }
//        });
    }
}
