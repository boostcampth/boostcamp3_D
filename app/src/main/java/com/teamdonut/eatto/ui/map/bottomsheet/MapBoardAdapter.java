package com.teamdonut.eatto.ui.map.bottomsheet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.MapBoardItemBinding;
import com.teamdonut.eatto.ui.map.MapViewModel;

import java.util.List;

public class MapBoardAdapter extends BaseRecyclerViewAdapter<Board, MapBoardAdapter.ViewHolder> {

    private MapViewModel viewModel;

    public MapBoardAdapter(List<Board> dataSet, MapViewModel viewModel) {
        super(dataSet);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.map_board_item, parent, false));

        MapBoardActionListener listener = board -> {
            viewModel.getOpenBoardEvent().setValue(board);
        };

        holder.binding.setListener(listener); //set listener
        return holder;
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        holder.binding.setSelect(getItem(position).isSelect());
        holder.binding.setBoard(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MapBoardItemBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
