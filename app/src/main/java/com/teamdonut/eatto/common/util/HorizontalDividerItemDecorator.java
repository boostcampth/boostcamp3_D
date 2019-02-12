package com.teamdonut.eatto.common.util;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalDividerItemDecorator extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private Double mWidthPercent;
    public HorizontalDividerItemDecorator(Drawable divider, Double widthPercent) {
        mDivider = divider;
        mWidthPercent = widthPercent;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int space = (int)(parent.getWidth() * mWidthPercent);
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