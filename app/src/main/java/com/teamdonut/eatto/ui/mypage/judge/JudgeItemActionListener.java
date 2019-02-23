package com.teamdonut.eatto.ui.mypage.judge;

import com.teamdonut.eatto.data.Board;

public interface JudgeItemActionListener {

    void onGreatClick(Board board, int score);
    void onGoodClick(Board board, int score);
    void onNormalClick(Board board, int score);
}
