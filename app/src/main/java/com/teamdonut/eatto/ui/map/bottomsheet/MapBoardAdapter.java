package com.teamdonut.eatto.ui.map.bottomsheet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardItemBinding;
import com.teamdonut.eatto.ui.map.MapViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MapBoardAdapter extends BaseRecyclerViewAdapter<Board, MapBoardAdapter.MapBoardViewHolder> {

    private MapViewModel mViewModel;

    public MapBoardAdapter(List<Board> dataSet, MapViewModel viewModel) {
        super(dataSet);
        this.mViewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MapBoardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item, parent, false));
    }

    @Override
    public void onBindView(MapBoardViewHolder holder, int position) {
        holder.binding.setBoard(getItem(position));
    }

    class MapBoardViewHolder extends RecyclerView.ViewHolder {

        BoardItemBinding binding;

        MapBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
