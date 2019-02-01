package com.teamdonut.eatto.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.HomeFragmentBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private HomeViewModel mViewModel;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        mViewModel = new HomeViewModel();
        binding.setViewmodel(mViewModel);
        View view = binding.getRoot();

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<mitem> foodInfoArrayList = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            foodInfoArrayList.add(new mitem(R.drawable.kakaostory_icon, i+"3000"));
        }

        mAdapter myAdapter = new mAdapter(foodInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
        return view;
    }
}