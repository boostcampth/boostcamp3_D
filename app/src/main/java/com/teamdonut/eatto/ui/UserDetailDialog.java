package com.teamdonut.eatto.ui;

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
import com.teamdonut.eatto.databinding.UserDetailDialogBinding;

public class UserDetailDialog extends DialogFragment {
    UserDetailDialogBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(matchParent, matchParent);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.user_detail_dialog, null, false);
        return binding.getRoot();
    }
}
