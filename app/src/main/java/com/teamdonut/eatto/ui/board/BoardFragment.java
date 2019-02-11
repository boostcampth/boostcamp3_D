package com.teamdonut.eatto.ui.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.BoardFragmentBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class BoardFragment extends Fragment implements BoardNavigator {
    private BoardFragmentBinding binding;
    private BoardViewModel mViewModel;

    public static BoardFragment newInstance() {
        return new BoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.board_fragment, container, false);
        mViewModel = new BoardViewModel(this);
        binding.setViewmodel(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        mViewModel.onFragmentDestroyed();
        super.onDestroy();
    }

    @Override
    public void addBoard() {
        Intent intent = new Intent(getContext(), BoardAddActivity.class);
        startActivity(intent);
    }
}
