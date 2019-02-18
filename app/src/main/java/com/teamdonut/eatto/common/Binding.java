package com.teamdonut.eatto.common;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class Binding {
    @BindingAdapter("adapter")
    public static void bindAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("items")
    public static void bindItems(RecyclerView recyclerView, List<?> items) {
        BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) recyclerView.getAdapter();

        if (adapter != null && items !=null) {
            adapter.updateItems(items);
        }
    }
}