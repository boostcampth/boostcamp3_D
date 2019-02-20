package com.teamdonut.eatto.ui.map.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRealmRecyclerViewAdapter;
import com.teamdonut.eatto.data.Keyword;
import com.teamdonut.eatto.databinding.MapKeywordItemBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.OrderedRealmCollection;

public class MapKeywordAdapter extends BaseRealmRecyclerViewAdapter<Keyword, MapKeywordAdapter.ViewHolder> {

    MapSearchViewModel mViewModel;

    public MapKeywordAdapter(@Nullable OrderedRealmCollection<Keyword> data, boolean autoUpdate, MapSearchViewModel viewModel) {
        super(data, autoUpdate);
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.map_keyword_item, parent, false));

        MapKeywordActionListener listener = new MapKeywordActionListener() {
            @Override
            public void onKeywordClick(Keyword keyword) {
                mViewModel.onGoSearchClick(keyword.getContent());
            }
        };

        holder.binding.setListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setKeyword(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MapKeywordItemBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
