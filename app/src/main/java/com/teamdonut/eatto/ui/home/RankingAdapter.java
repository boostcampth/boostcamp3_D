package com.teamdonut.eatto.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.databinding.RankingItemBinding;

import java.util.List;

public class RankingAdapter extends BaseRecyclerViewAdapter<User, RankingAdapter.ViewHolder> {
    public RankingAdapter(List<User> dataSet) {
        super(dataSet);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setUser(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RankingItemBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}