package com.teamdonut.eatto.ui.board;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.ViewModel;

public class BoardViewModel extends ViewModel {


    private BoardNavigator mNavigator;

    public BoardViewModel() {

    }

    public BoardViewModel(BoardNavigator navigator) {
        mNavigator = navigator;
    }

    void onFragmentDestroyed() {
        mNavigator = null;
    }


    public void onClickBoardAdd(View view) {
        if (mNavigator != null) {
            mNavigator.addBoard();
        }
    }

    //댓글 등록 리스너
    public void onClickCommentAdd(View view) {

    }
}
