package com.example.simpletodoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    List<String> items;

    public interface OnLongClickListener{
        void onLongClick(int position);
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    OnLongClickListener longClickListener;
    OnClickListener clickListener;
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Create the actual list View
        View todoItem = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        return new ViewHolder(todoItem);
    }

    @Override
    public void onBindViewHolder(@NonNull  ItemsAdapter.ViewHolder holder, int position) {
        String item = this.items.get(position);

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    // Views container
    class ViewHolder extends RecyclerView.ViewHolder {

        //Actual text on list item
        TextView textItem;
        public ViewHolder(@NonNull View viewItem) {

            //Construct parent class
            super(viewItem);
            textItem = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item) {
            textItem.setText(item);

            textItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(getAdapterPosition());
                }
            });

            textItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Register position of item that was long pressed
                    longClickListener.onLongClick(getAdapterPosition()) ;
                    return true;
                }
            });
        }
    }
}
