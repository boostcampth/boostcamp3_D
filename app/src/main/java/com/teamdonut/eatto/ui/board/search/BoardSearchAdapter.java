package com.teamdonut.eatto.ui.board.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.databinding.BoardSearchItemBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BoardSearchAdapter extends BaseRecyclerViewAdapter<Document, BoardSearchAdapter.ViewHolder> {

    public BoardSearchAdapter(ArrayList<Document> dataSet) {
        super(dataSet);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BoardSearchItemBinding binding = BoardSearchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        final ViewHolder holder = new ViewHolder(binding);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().sendBus( holder.getAdapterPosition() );
            }
        });

        return holder;
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setDocument(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        BoardSearchItemBinding binding;

        ViewHolder(BoardSearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
