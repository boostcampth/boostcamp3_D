package com.teamdonut.eatto.common;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> dataSet;

    public BaseRecyclerViewAdapter(List<T> dataSet) {
        this.dataSet = dataSet;
    }

    public T getItem(int position) {
        return dataSet == null ? null : dataSet.get(position);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindView((VH) holder, position);
    }

    abstract public void onBindView(VH holder, int position);

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    /**
     * Add items.
     *
     * @param items list.
     */
    public void addItems(List<T> items) {
        if (dataSet == null) {
            dataSet = new ArrayList<>();
        }

        int currentSize = getItemCount();
        dataSet.addAll(items);

        notifyItemRangeInserted(currentSize, items.size());
    }

    /**
     * clear and add items.
     * @param items list.
     */
    public void updateItems(List<T> items) {
        if (dataSet == null) {
            dataSet = new ArrayList<>();
        }
        dataSet.clear();
        notifyDataSetChanged();

        dataSet.addAll(items);

        notifyItemRangeChanged(0, items.size());
    }


    /**
     * RemoveAll items.
     */
    public void removeAllItems() {
        if (dataSet != null) {
            dataSet.clear();
            notifyDataSetChanged();
        }
    }
}
