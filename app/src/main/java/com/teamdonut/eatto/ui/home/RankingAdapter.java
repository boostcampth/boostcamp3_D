package com.teamdonut.eatto.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.databinding.RankingItemBinding;

import java.util.List;

public class RankingAdapter extends BaseRecyclerViewAdapter<User, RankingAdapter.ViewHolder> {
    Context mContext;
    List<User> items;

    public RankingAdapter(Context context, List<User> dataSet) {
        super(dataSet);
        mContext = context;
        items = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(mContext).inflate(R.layout.ranking_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.binding.setUser(items.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RankingItemBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}