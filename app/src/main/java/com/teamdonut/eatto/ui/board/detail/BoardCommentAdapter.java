package com.teamdonut.eatto.ui.board.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Comment;
import com.teamdonut.eatto.databinding.BoardCommentItemBinding;

import java.util.List;

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
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.board_comment_item, parent, false));

        return holder;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        BoardCommentItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
