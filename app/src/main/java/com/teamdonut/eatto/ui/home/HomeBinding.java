package com.teamdonut.eatto.ui.home;

import android.content.Context;
import android.util.TypedValue;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.HorizontalDividerItemDecorator;

public class HomeBinding {
    @BindingAdapter("home:setVerticalAdapter")
    public static void bindVerticalRVAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        Context context = recyclerView.getContext();
        RecyclerView.LayoutManager rankingManager = new LinearLayoutManager(context){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.height = (getWidth()/9);
                return super.checkLayoutParams(lp);
            }
        };
        recyclerView.addItemDecoration(new HorizontalDividerItemDecorator(ContextCompat.getDrawable(context, R.drawable.ranking_divider), 0.03));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(rankingManager);;
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("home:setHorizontalAdapter")
    public static void bindHomeHorizontalRV(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        Context context = recyclerView.getContext();
        RecyclerView.LayoutManager recommendBoardManager = new LinearLayoutManager(context) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                int dp = (int) (context.getResources().getDimension(R.dimen.space_medium_margin) / context.getResources().getDisplayMetrics().density);
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp * 3, context.getResources().getDisplayMetrics());
                lp.width = (getWidth() - (int) px) / 2;
                return super.checkLayoutParams(lp);
            }
        };
        ((LinearLayoutManager) recommendBoardManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(recommendBoardManager);
        recyclerView.setAdapter(adapter);
    }
}
