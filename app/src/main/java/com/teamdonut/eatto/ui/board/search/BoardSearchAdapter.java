package com.teamdonut.eatto.ui.board.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.databinding.BoardSearchItemBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BoardSearchAdapter extends BaseRecyclerViewAdapter<Document, BoardSearchAdapter.MyViewHolder> {

    public BoardSearchAdapter(ArrayList<Document> dataSet) {
        super(dataSet);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_search_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindView(MyViewHolder holder, int position) {
        holder.binding.setViewmodel(new BoardSearchViewModel(getItem(position)));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        BoardSearchItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
