package com.teamdonut.eatto.ui.map;

import android.view.View;

public class MapViewModel {
    private MapNavigator navigator;
    public MapViewModel(MapNavigator navigator) {
        this.navigator = navigator;
    }


    //검색 버튼 리스너
    public void onClickSearchButton(View view) {

    }

    //게시물 추가 리스너
    public void onClickBoardAdd(View view) {

    }

    public void onClickSetMyPosition(View view){
        navigator.startLocationUpdates();
    }
}
