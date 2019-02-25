package com.teamdonut.eatto.ui.board.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Comment;
import com.teamdonut.eatto.databinding.BoardCommentItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BoardCommentAdapter extends BaseRecyclerViewAdapter<Comment, BoardCommentAdapter.ViewHolder> {

    private BoardDetailViewModel viewModel;

    public BoardCommentAdapter(List<Comment> dataSet, BoardDetailViewModel viewModel) {
        super(dataSet);
        this.viewModel = viewModel;
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setComment(getItem(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BoardCommentItemBinding binding = BoardCommentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        BoardCommentItemBinding binding;

        public ViewHolder(BoardCommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
