package com.teamdonut.eatto.ui.home;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        mViewModel = new HomeViewModel();
        binding.setViewmodel(mViewModel);
        initRankRv();
        return binding.getRoot();
    }
    
    void initRankRv(){
        RecyclerView.LayoutManager rankingManager = new LinearLayoutManager(this.getContext()){
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.height = (getWidth()/9);
                return super.checkLayoutParams(lp);
            }
        };

        binding.rvRank.addItemDecoration(new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.ranking_divider)));
        binding.rvRank.setHasFixedSize(true);
        binding.rvRank.setLayoutManager(rankingManager);
    }

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
}
