package com.teamdonut.eatto.ui.map;

import android.content.Context;
import android.view.View;

public class MapViewModel {
  
    private MapNavigator navigator;
    public MapViewModel(MapNavigator navigator) {
        this.navigator = navigator;
    }

    void onFragmentDestroyed() {
        mNavigator = null;
    }

    //검색 버튼 리스너
    public void onClickSearchButton(View view) {

    }

    //게시물 추가 리스너
    public void onClickBoardAdd(View view) {
        if (mNavigator != null) {
            mNavigator.addBoard();
        }
    }

    public void onClickSetMyPosition(View view){
        navigator.startLocationUpdates();
    }
}
