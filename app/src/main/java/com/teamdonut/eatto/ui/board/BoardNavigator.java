package com.teamdonut.eatto.ui.board;

import com.teamdonut.eatto.data.kakao.Document;

public interface BoardNavigator {

    default void onAddBoardClick(){};
    default void onTimePickerClick() {};
    default void onBoardSearchShowClick() {};
    default void onShowSnackBar() {};
    default void onBoardAddFinish() {};
    default void sendDocument(Document document) {}
}
