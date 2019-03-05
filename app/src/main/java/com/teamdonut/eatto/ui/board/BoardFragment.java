package com.teamdonut.eatto.ui.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.databinding.BoardFragmentBinding;
import com.teamdonut.eatto.ui.board.add.BoardAddActivity;
import com.teamdonut.eatto.ui.board.detail.BoardDetailActivity;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class BoardFragment extends Fragment implements BoardNavigator {

    private BoardFragmentBinding binding;
    private BoardViewModel viewModel;

    private BoardOwnAdapter boardOwnAdapter;
    private BoardParticipateAdapter boardParticipateAdapter;

    private final int BOARD_ADD_REQUEST = 100;

    public static BoardFragment newInstance() {
        return new BoardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(BoardViewModel.class);
        viewModel.setNavigator(this);

        initOpenBoardObserve();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.board_fragment, container, false);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        initRv(binding.rvOwn);
        initRv(binding.rvParticipate);
        viewModel.fetchOwnBoard();
        viewModel.fetchParticipateBoard();
    }

    private void initOpenBoardObserve() {
        viewModel.getOpenBoardEvent().observe(this, data -> {
            Intent intent = new Intent(getContext(), BoardDetailActivity.class);
            RxBus.getInstance().sendBus(data);
            startActivity(intent);
        });
    }

    private void initRv(RecyclerView rv) {
        switch (rv.getId()) {
            case R.id.rv_own: {
                boardOwnAdapter = new BoardOwnAdapter(new ArrayList<>(0), viewModel);
                rv.setAdapter(boardOwnAdapter);
                setAnimation(rv);
                break;
            }
            case R.id.rv_participate: {
                boardParticipateAdapter = new BoardParticipateAdapter(new ArrayList<>(0), viewModel);
                rv.setAdapter(boardParticipateAdapter);
                setAnimation(rv);
                break;
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(rv.getContext(), 1);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.board_divider));

        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(itemDecoration);
        rv.setHasFixedSize(true);
    }

    public void setAnimation(RecyclerView rv) {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_DRAGGING) {
                    binding.fabBoardAdd.hide();
                } else if (newState == SCROLL_STATE_IDLE) {

                    Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fabscrollanim);
                    binding.fabBoardAdd.setAnimation(anim);
                    binding.fabBoardAdd.show();
                }
            }
        });
    }

    @Override
    public void onAddBoardClick() {
        Intent intent = new Intent(getContext(), BoardAddActivity.class);
        getActivity().startActivityForResult(intent, BOARD_ADD_REQUEST);
    }
}
