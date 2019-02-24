package com.teamdonut.eatto.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardRecommendItemBinding;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class BoardRecommendAdapter extends BaseRecyclerViewAdapter<Board, BoardRecommendAdapter.ViewHolder> {

    private HomeViewModel viewModel;

    public BoardRecommendAdapter(List<Board> dataSet, HomeViewModel viewModel) {
        super(dataSet);
        this.viewModel = viewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoardRecommendItemBinding binding = BoardRecommendItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.clMain.setOnClickListener(v -> {
            viewModel.onPreviewClick(binding.getBoard());
        });
        return new ViewHolder(binding);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setBoard(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BoardRecommendItemBinding binding;

        ViewHolder(BoardRecommendItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}