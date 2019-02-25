package com.teamdonut.eatto.ui.map.bottomsheet;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.MapBoardItemBinding;
import com.teamdonut.eatto.ui.map.MapViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MapBoardAdapter extends BaseRecyclerViewAdapter<Board, MapBoardAdapter.ViewHolder> {

    private MapViewModel viewModel;

    public MapBoardAdapter(List<Board> dataSet, MapViewModel viewModel) {
        super(dataSet);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MapBoardItemBinding binding = MapBoardItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        MapBoardActionListener listener = board -> {
            viewModel.getOpenBoardEvent().setValue(board);
        };
        binding.setListener(listener);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setSelect(getItem(position).isSelect());
        holder.binding.setBoard(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MapBoardItemBinding binding;

        ViewHolder(MapBoardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
