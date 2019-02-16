package com.teamdonut.eatto.common.util;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtil {

    public static void showSnackBar(View parentLayout, int resId) {
        Snackbar snack = Snackbar.make(parentLayout, parentLayout.getResources().getText(resId).toString(), Snackbar.LENGTH_SHORT);
        snack.show();
    }

    public static void showSnackBartoTop(View parentLayout, int resId) {
        Snackbar snack = Snackbar.make(parentLayout, parentLayout.getResources().getText(resId).toString(), Snackbar.LENGTH_SHORT);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }
}
