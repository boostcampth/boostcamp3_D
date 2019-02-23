package com.teamdonut.eatto.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.RecommendBoardItemBinding;

import java.util.List;

public class BoardRecommendAdapter extends BaseRecyclerViewAdapter<Board, BoardRecommendAdapter.ViewHolder> {

    private HomeViewModel viewModel;

    public BoardRecommendAdapter(List<Board> dataSet, HomeViewModel viewModel) {
        super(dataSet);
        this.viewModel = viewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_board_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setBoard(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecommendBoardItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}