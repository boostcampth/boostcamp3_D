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

public class BoardOwnAdapter extends BaseRecyclerViewAdapter<Board, BoardOwnAdapter.MyViewHolder> {

    public BoardOwnAdapter(ArrayList<Board> dataSet) {
        super(dataSet);;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BoardItemBinding binding = BoardItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindView(MyViewHolder holder, int position) {
        holder.binding.setBoard(getItem(position));
        Log.d("checktitle",getItem(position).getTitle());
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        BoardItemBinding binding;

        MyViewHolder(BoardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
