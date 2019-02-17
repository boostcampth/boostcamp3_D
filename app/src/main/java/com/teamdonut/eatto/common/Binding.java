package com.teamdonut.eatto.common;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.common.util.GlideApp;

public class Binding {
    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"glideImage", "glideError"})
    public static void setImageFromUrl(ImageView view, String imgUrl, Drawable errorDrawable) {
        GlideApp.with(view)
                .load(imgUrl)
                .error(errorDrawable)
                .into(view);
    }
}
