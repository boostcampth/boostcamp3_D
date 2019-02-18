package com.teamdonut.eatto.common;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import java.util.List;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.common.util.GlideApp;

public class Binding {
    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"imageUrl", "imageError"})
    public static void setImageFromUrl(ImageView view, String imageUrl, Drawable imageError) {
        GlideApp.with(view)
                .load(imageUrl)
                .error(imageError)
                .into(view);
    }
}

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<?> items) {
        BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) recyclerView.getAdapter();

        if (adapter != null && items !=null) {
            adapter.updateItems(items);
        }
    }
}