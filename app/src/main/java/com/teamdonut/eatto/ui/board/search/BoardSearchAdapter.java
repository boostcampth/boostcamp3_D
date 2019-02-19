package com.teamdonut.eatto.ui.board.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.databinding.BoardSearchItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

public class BoardSearchAdapter extends BaseRecyclerViewAdapter<Document, BoardSearchAdapter.ViewHolder> {

    private List<Document> documentList;

    public BoardSearchAdapter(ArrayList<Document> dataSet) {
        super(dataSet);
        documentList = dataSet;
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
        Document document = documentList.get(position);
        holder.bind(document);
    }

    @BindingAdapter("app:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<Document> documents) {
        BoardSearchAdapter adapter = (BoardSearchAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(documents);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        BoardSearchItemBinding binding;

        ViewHolder(BoardSearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Document document) {
            binding.setVariable(BR.document, document);
        }
    }

}
