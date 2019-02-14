package com.teamdonut.eatto.ui.board;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import com.appyvet.materialrangebar.RangeBar;

@BindingMethods({
        @BindingMethod(
                type = RangeBar.class,
                attribute = "onRangeBarChange",
                method = "setOnRangeBarChangeListener"
        )
})

public class BoardViewModel extends ViewModel {
    private BoardNavigator mNavigator;
    public ObservableField<String> time = new ObservableField<>("시간을 설정해 주세요");
    private int minAge;
    private int maxAge;

    public BoardViewModel() {

    }

    public BoardViewModel(BoardNavigator navigator) {
        mNavigator = navigator;
    }

    void onFragmentDestroyed() {
        mNavigator = null;
    }


    public void onClickBoardAdd() {
        mNavigator.onAddBoardClick();
    }

    //댓글 등록 리스너
    public void onClickCommentAdd() {

    }

    public void onTimePickerClicked() {
        mNavigator.onTimePickerClick();
    }

    //Board_Search 검색 이벤트
    public void onAddressSearchClicked() {
        mNavigator.onAddressSearchClick();
    }

    //Board_Search 액티비티 연결 이벤트
    public void onBoardSearchShowClick() {
        mNavigator.onBoardSearchShowClick();
    }


    public void setOnRangeBarChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
        setMinAge(Integer.parseInt(leftPinValue));
        setMaxAge(Integer.parseInt(rightPinValue));
    }

    public BoardNavigator getmNavigator() {
        return mNavigator;
    }

    public void setmNavigator(BoardNavigator mNavigator) {
        this.mNavigator = mNavigator;
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public void setTime(ObservableField<String> time) {
        this.time = time;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
