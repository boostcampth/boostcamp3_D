package com.teamdonut.eatto.ui.board;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardItemBinding;

import java.util.ArrayList;

public class BoardParticipateAdapter extends BaseRecyclerViewAdapter<Board, BoardParticipateAdapter.ViewHolder> {

    private BoardViewModel viewModel;
    private BoardItemActionListener boardItemActionListener;

    public BoardParticipateAdapter(ArrayList<Board> dataSet, BoardViewModel boardViewModel) {
        super(dataSet);
        viewModel = boardViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BoardItemBinding binding = BoardItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        final ViewHolder holder = new ViewHolder(binding);

        boardItemActionListener = board -> viewModel.getOpenBoardEvent().setValue(board);

        holder.binding.setListener(boardItemActionListener);
        return holder;
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setBoard(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        BoardItemBinding binding;

        ViewHolder(BoardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
