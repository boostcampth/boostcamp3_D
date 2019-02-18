package com.teamdonut.eatto.ui.board;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.RxBus;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.databinding.BoardPreviewDialogBinding;

public class BoardPreviewDialog extends DialogFragment {

    private BoardPreviewDialogBinding binding;
    private Board board;

    private final String BOARD_ARGUMENT = "Board";

    public static BoardPreviewDialog newInstance() {
        return new BoardPreviewDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(900, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);

        checkBundle();
    }

    private void checkBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            board = bundle.getParcelable(BOARD_ARGUMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.board_preview_dialog, null, false);
        binding.setBoard(board);
        return binding.getRoot();
    }

}
