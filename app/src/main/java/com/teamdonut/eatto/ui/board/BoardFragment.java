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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BoardFragment extends Fragment implements BoardNavigator {
    private BoardFragmentBinding binding;
    private BoardViewModel mViewModel;
    private final int BOARD_ADD_REQUEST = 100;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initOwnBoardResultRv();
        initJoinBoardResultRv();
    }

    private void initOwnBoardResultRv() {
        LinearLayoutManager mBoardManager = new LinearLayoutManager(getContext());
        mBoardManager.setOrientation(RecyclerView.VERTICAL);

        binding.rvMyBoard.setLayoutManager(mBoardManager);
        binding.rvMyBoard.setAdapter(mViewModel.getBoardOwnAdapter());

        mViewModel.fetchOwnBoardResult();

    }

    private void initJoinBoardResultRv() {
        LinearLayoutManager mBoardManager = new LinearLayoutManager(getContext());
        mBoardManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvOtherBoard.setLayoutManager(mBoardManager);
        binding.rvOtherBoard.setAdapter(mViewModel.getBoardJoinAdapter());

        mViewModel.fetchJoinBoardResult();

    }


    @Override
    public void onDestroy() {
        mViewModel.onFragmentDestroyed();
        super.onDestroy();
    }

    @Override
    public void onAddBoardClick() {
        Intent intent = new Intent(getContext(), BoardAddActivity.class);
        getActivity().startActivityForResult(intent, BOARD_ADD_REQUEST);
    }
}
