package com.teamdonut.eatto.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.databinding.RankingItemBinding;

import java.util.List;

public class UserRankingAdapter extends BaseRecyclerViewAdapter<User, UserRankingAdapter.ViewHolder> {

    private HomeViewModel viewModel;

    public UserRankingAdapter(List<User> dataSet, HomeViewModel viewMdoel) {
        super(dataSet);
        viewModel = viewMdoel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RankingItemBinding binding = RankingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setRank(String.valueOf(position + 1));
        holder.binding.setUser(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RankingItemBinding binding;

        ViewHolder(RankingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}