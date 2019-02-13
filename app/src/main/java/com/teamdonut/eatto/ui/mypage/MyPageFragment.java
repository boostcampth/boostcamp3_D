package com.teamdonut.eatto.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.databinding.MypageFragmentBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class MyPageFragment extends Fragment implements MyPageNavigator {

    private MyPageViewModel mViewModel;
    private MypageFragmentBinding binding;

    public static MyPageFragment newInstance() {
        return new MyPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.mypage_fragment, container, false);
        mViewModel = new MyPageViewModel(this);
        binding.setViewmodel(mViewModel);

        return binding.getRoot();
    }

    @Override
    public void goToProfileEdit() {
        Intent intent = new Intent(getActivity(), MyPageEditActivity.class);
        startActivity(intent);
    }

    @Override
    public void makeUserLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                if (getActivity() instanceof AppCompatActivity) {
                    ActivityUtils.redirectLoginActivity(getActivity());
                }
            }
        });
    }
}
