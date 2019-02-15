package com.teamdonut.eatto.common;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class Binding {
    @BindingAdapter("setAdapter")
    public static void bindAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }
}
