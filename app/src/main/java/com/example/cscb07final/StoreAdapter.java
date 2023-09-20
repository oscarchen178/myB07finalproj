package com.example.cscb07final;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class StoreAdapter extends FirebaseRecyclerAdapter<Store, StoreAdapter.StoreViewHolder> {

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, location;

        public StoreViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.store_name);
            location = (TextView) view.findViewById(R.id.store_location);
        }
    }

    public StoreAdapter(FirebaseRecyclerOptions<Store> options) {
        super(options);
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storelist, viewGroup, false);

        return new StoreViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(StoreViewHolder viewHolder, int position, final Store model) {
        viewHolder.name.setText(model.Name);
        viewHolder.location.setText(model.Address);
        viewHolder.itemView.setOnClickListener(v -> {
            Current.currentStore = model.sid;
            v.getContext().startActivity(new Intent(v.getContext(), StoreItemActivity.class));
        });
    }

}
