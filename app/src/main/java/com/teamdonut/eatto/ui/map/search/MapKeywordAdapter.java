package com.teamdonut.eatto.ui.map.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teamdonut.eatto.common.BaseRealmRecyclerViewAdapter;
import com.teamdonut.eatto.data.Keyword;
import com.teamdonut.eatto.databinding.MapKeywordItemBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.OrderedRealmCollection;

public class MapKeywordAdapter extends BaseRealmRecyclerViewAdapter<Keyword, MapKeywordAdapter.ViewHolder> {

    MapSearchViewModel viewModel;

    public MapKeywordAdapter(@Nullable OrderedRealmCollection<Keyword> data, boolean autoUpdate, MapSearchViewModel viewModel) {
        super(data, autoUpdate);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MapKeywordItemBinding binding = MapKeywordItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        MapKeywordActionListener listener = keyword -> {
            viewModel.onGoSearchClick(keyword.getContent());
        };

        binding.setListener(listener);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setKeyword(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MapKeywordItemBinding binding;

        ViewHolder(MapKeywordItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
