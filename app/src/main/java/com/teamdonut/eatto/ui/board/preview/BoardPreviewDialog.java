package com.teamdonut.eatto.ui.board.preview;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardPreviewDialogBinding;
import com.teamdonut.eatto.ui.board.detail.BoardDetailActivity;

public class BoardPreviewDialog extends DialogFragment {

    private BoardPreviewDialogBinding binding;

    private BoardPreviewViewModel mViewModel;
    private Observer<Boolean> joinObserver;

    private static final String BOARD_ARGUMENT = "Board";

    public static BoardPreviewDialog newInstance(Board board) {
        Bundle argument = new Bundle();
        argument.putParcelable(BOARD_ARGUMENT, board);

        BoardPreviewDialog fragment = new BoardPreviewDialog();
        fragment.setArguments(argument);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);

        initJoinObserver();
        mViewModel = ViewModelProviders.of(this).get(BoardPreviewViewModel.class);
        mViewModel.getIsSubmitted().observe(this, joinObserver);
    }

    private void initJoinObserver() {
        joinObserver = isSubmitted -> {
            dismiss();
            if (isSubmitted) {
                Intent intent = new Intent(getActivity(), BoardDetailActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        mViewModel.getIsSubmitted().removeObserver(joinObserver);
        super.onDismiss(dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.board_preview_dialog, container, false);
        binding.setViewmodel(mViewModel);

        binding.cl.setOnClickListener(v -> {
            dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.setBoardValue(getArguments().getParcelable(BOARD_ARGUMENT));
    }
}
