package com.teamdonut.eatto.ui.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardFragmentBinding;
import com.teamdonut.eatto.ui.board.detail.BoardDetailActivity;

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

    //보드 상세보기 연결
    @Override
    public void onShowMyBoardDetail(int position) {
        Board board = mViewModel.getBoardOwnAdapter().getItem(position);
        Intent intent = new Intent(getContext(), BoardDetailActivity.class);
        RxBus.getInstance().sendBus(board);
        startActivity(intent);
    }

    @Override
    public void onShowJoinBoardDetail(int position) {
        Board board = mViewModel.getBoardJoinAdapter().getItem(position);
        Intent intent = new Intent(getContext(), BoardDetailActivity.class);
        RxBus.getInstance().sendBus(board);
        startActivity(intent);
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
