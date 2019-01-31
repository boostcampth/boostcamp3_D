package com.teamdonut.eatto.ui.map;

import android.util.Log;
import android.view.View;
import androidx.databinding.BindingMethod;

public class MapViewModel {

    public MapViewModel() {


    }


    //검색 버튼 리스너
    public void onClickSearchButton(View view){


        Log.d("searchButton","hihihi");
    }


    //게시물 추가 리스너
    public void onClickBoardAdd(View view) {
        Log.d("boardAdd","hihii");

    }
}
