package com.example.cscb07final;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ItemAdapter extends FirebaseRecyclerAdapter<Item, ItemAdapter.ItemViewHolder> {

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, price, des;

        public ItemViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.item_list_name);
            price = (TextView) view.findViewById(R.id.item_list_price);
            des = (TextView) view.findViewById(R.id.item_list_des);
        }

    }

    public ItemAdapter(FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storelist_items, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ItemViewHolder viewHolder,int position, final Item model) {
        viewHolder.name.setText(model.name);
        viewHolder.price.setText(model.price);
        viewHolder.des.setText(model.describe);
        viewHolder.itemView.setOnClickListener(v -> {
            Current.currentiID = model.iID;
            if (!Current.isOwner)
                v.getContext().startActivity(new Intent(v.getContext(), AddToCartActivity.class));
        });
    }
}
