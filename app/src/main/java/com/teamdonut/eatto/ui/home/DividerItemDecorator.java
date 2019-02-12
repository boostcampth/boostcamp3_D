package com.teamdonut.eatto.ui.home;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecorator extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    public DividerItemDecorator(Drawable divider) {
        mDivider = divider;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int space = (int)(parent.getWidth() * 0.03);
        int dividerLeft = space + parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight() - space;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + params.bottomMargin;
            int dividerBottom = dividerTop + mDivider.getIntrinsicHeight();

            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            mDivider.draw(canvas);
        }
    }
}