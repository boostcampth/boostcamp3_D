package com.teamdonut.eatto.ui.board;

import android.content.Context;
import android.view.View;

import com.appyvet.materialrangebar.RangeBar;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

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
    private int min_age;
    private int max_age;

    public BoardViewModel() {

    }

    public BoardViewModel(BoardNavigator navigator) {
        mNavigator = navigator;
    }

    void onFragmentDestroyed() {
        mNavigator = null;
    }


    public void onClickBoardAdd() {

        mNavigator.addBoard();

    }

    //댓글 등록 리스너
    public void onClickCommentAdd() {

    }

    public void onTimePickerClicked() {

        mNavigator.onTimePickerClicked();

    }

    //Board_Search 검색 이벤트
    public void onAddressSearchClicked() {

        mNavigator.onAddressSearchClicked();

    }

    public void setOnRangeBarChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
        setMin_age(Integer.parseInt(leftPinValue));
        setMax_age(Integer.parseInt(rightPinValue));
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }
}
