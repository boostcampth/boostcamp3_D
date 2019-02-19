package com.teamdonut.eatto.ui.board;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardItemBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BoardOwnAdapter extends BaseRecyclerViewAdapter<Board, BoardOwnAdapter.ViewHolder> {

    private BoardViewModel mViewModel;

    public BoardOwnAdapter(ArrayList<Board> dataSet, BoardViewModel boardViewModel) {
        super(dataSet);
        mViewModel = boardViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BoardItemBinding binding = BoardItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        final ViewHolder holder = new ViewHolder(binding);
        holder.binding.clBoardItem.setOnClickListener((v) -> {
           mViewModel.getmNavigator().onShowMyBoardDetail(holder.getAdapterPosition());
        });
        return holder;
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setBoard(getItem(position));
        Log.d("checktitle",getItem(position).getTitle());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        BoardItemBinding binding;

        ViewHolder(BoardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
