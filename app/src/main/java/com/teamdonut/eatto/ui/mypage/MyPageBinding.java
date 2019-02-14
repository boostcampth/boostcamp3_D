package com.teamdonut.eatto.ui.mypage;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.teamdonut.eatto.common.util.GlideApp;

import androidx.databinding.BindingAdapter;

public class MyPageBinding {

    @BindingAdapter({"url", "error"})
    public static void setImageFromUrl(ImageView view, String imgUrl, Drawable errorDrawable) {
        GlideApp.with(view)
                .load(imgUrl)
                .error(errorDrawable)
                .into(view);
    }
}
