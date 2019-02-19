package com.teamdonut.eatto.ui.board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.data.Board;
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

    //보드 상세보기 연결

    @Override
    public void onShowMyBoardDetail(int position) {
        Board board = mViewModel.getBoardOwnAdapter().getItem(position);
        Log.d("send", board.getMaxPerson() + "");
        Intent intent = new Intent(getContext(), BoardDetailActivity.class);
        intent.putExtra("addressName", board.getAddress());
        intent.putExtra("title", board.getTitle());
        intent.putExtra("restaurantName", board.getRestaurantName());
        intent.putExtra("minAge", board.getMinAge());
        intent.putExtra("maxAge", board.getMaxAge());
        intent.putExtra("budget", board.getBudget());
        intent.putExtra("appointedTime", board.getAppointedTime().toString());
        intent.putExtra("content", board.getContent());
        intent.putExtra("currentPerson", board.getCurrentPerson());
        intent.putExtra("maxPerson", board.getMaxPerson());
        intent.putExtra("longitude", board.getLongitude());
        intent.putExtra("latitude", board.getLatitude());
        intent.putExtra("writerPhoto", board.getWriterPhoto());
        intent.putExtra("writerName", board.getWriterName());
        intent.putExtra("writerId", board.getWriterId());

        startActivity(intent);
    }

    @Override
    public void onShowJoinBoardDetail(int position) {
        Board board = mViewModel.getBoardJoinAdapter().getItem(position);
        Log.d("send", board.getMaxPerson() + "");
        Intent intent = new Intent(getContext(), BoardDetailActivity.class);
        intent.putExtra("addressName", board.getAddress());
        intent.putExtra("title", board.getTitle());
        intent.putExtra("restaurantName", board.getRestaurantName());
        intent.putExtra("minAge", board.getMinAge());
        intent.putExtra("maxAge", board.getMaxAge());
        intent.putExtra("budget", board.getBudget());
        intent.putExtra("appointedTime", board.getAppointedTime().toString());
        intent.putExtra("content", board.getContent());
        intent.putExtra("currentPerson", board.getCurrentPerson());
        intent.putExtra("maxPerson", board.getMaxPerson());
        intent.putExtra("longitude", board.getLongitude());
        intent.putExtra("latitude", board.getLatitude());
        intent.putExtra("writerPhoto", board.getWriterPhoto());
        intent.putExtra("writerName", board.getWriterName());
        intent.putExtra("writerId", board.getWriterId());

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
