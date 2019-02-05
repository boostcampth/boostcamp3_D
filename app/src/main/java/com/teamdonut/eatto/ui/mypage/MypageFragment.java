package com.teamdonut.eatto.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.MypageFragmentBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class MypageFragment extends Fragment {

    private MypageViewModel mViewModel;
    private MypageFragmentBinding binding;
    public static MypageFragment newInstance() {
        return new MypageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.mypage_fragment, container, false);
        mViewModel = new MypageViewModel();
        binding.setViewmodel(mViewModel);
        return binding.getRoot();
    }
}
