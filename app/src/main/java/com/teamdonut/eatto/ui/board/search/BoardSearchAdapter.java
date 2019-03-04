package com.teamdonut.eatto.ui.board.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.databinding.BoardSearchItemBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BoardSearchAdapter extends BaseRecyclerViewAdapter<Document, BoardSearchAdapter.ViewHolder> {

    private BoardSearchViewModel viewModel;
    private SearchItemActonListener listener;

    public BoardSearchAdapter(ArrayList<Document> dataSet, BoardSearchViewModel viewModel) {
        super(dataSet);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BoardSearchItemBinding binding = BoardSearchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        listener = new SearchItemActonListener() {
            @Override
            public void onDocumentClick(Document document) {
                viewModel.sendDocument(document);
            }
        };

        binding.setListener(listener);
        return new ViewHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setDocument(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        BoardSearchItemBinding binding;

        ViewHolder(BoardSearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
