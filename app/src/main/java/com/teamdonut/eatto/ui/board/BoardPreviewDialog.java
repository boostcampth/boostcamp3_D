package com.teamdonut.eatto.ui.board;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import androidx.lifecycle.Observer;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardPreviewDialogBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class BoardPreviewDialog extends DialogFragment{

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

        joinObserver = isSubmitted -> {
            dismiss();
        };
        mViewModel = ViewModelProviders.of(this).get(BoardPreviewViewModel.class);
        mViewModel.getIsSubmitted().observe(this, joinObserver);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        mViewModel.getIsSubmitted().removeObserver(joinObserver);
        super.onDismiss(dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(this.getContext(), R.style.DialogTheme);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PreviewDialogAnimation;
//        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.board_preview_dialog, container, false);
        binding.setViewmodel(mViewModel);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.setBoardValue(getArguments().getParcelable(BOARD_ARGUMENT));
    }
}
