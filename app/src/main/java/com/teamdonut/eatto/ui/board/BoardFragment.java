package com.teamdonut.eatto.ui.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.databinding.BoardFragmentBinding;
import com.teamdonut.eatto.ui.board.detail.BoardDetailActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BoardFragment extends Fragment implements BoardNavigator {

    private BoardFragmentBinding binding;
    private BoardViewModel mViewModel;

    private BoardOwnAdapter mOwnBoardAdapter;
    private BoardParticipateAdapter mParticipateBoardAdapter;

    private final int BOARD_ADD_REQUEST = 100;

    public static BoardFragment newInstance() {
        return new BoardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(BoardViewModel.class);
        mViewModel.setNavigator(this);

        initOpenBoardObserve();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.board_fragment, container, false);
        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        initRv(binding.rvOwn);
        initRv(binding.rvParticipate);
        mViewModel.fetchOwnBoard();
        mViewModel.fetchParticipateBoard();
    }

    private void initOpenBoardObserve() {
        mViewModel.getOpenBoardEvent().observe(this, data -> {
            Intent intent = new Intent(getContext(), BoardDetailActivity.class);
            RxBus.getInstance().sendBus(data);
            startActivity(intent);
        });
    }

    private void initRv(RecyclerView rv) {
        switch (rv.getId()) {
            case R.id.rv_own: {
                mOwnBoardAdapter = new BoardOwnAdapter(new ArrayList<>(0), mViewModel);
                rv.setAdapter(mOwnBoardAdapter);
                break;
            }
            case R.id.rv_participate: {
                mParticipateBoardAdapter = new BoardParticipateAdapter(new ArrayList<>(0), mViewModel);
                rv.setAdapter(mParticipateBoardAdapter);
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), 1);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.board_divider));

        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(itemDecoration);
        rv.setHasFixedSize(true);
    }

    @Override
    public void onAddBoardClick() {
        Intent intent = new Intent(getContext(), BoardAddActivity.class);
        getActivity().startActivityForResult(intent, BOARD_ADD_REQUEST);
    }
}
