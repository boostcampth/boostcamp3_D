package com.teamdonut.eatto.ui.home;

import com.teamdonut.eatto.data.Board;

public class BoardRecommendViewModel {
    private Board board;
    public BoardRecommendViewModel(Board board){
        this.board = board;
    }

    public String getBoardTitle(){
        return board.getTitle();
    }

    public String getBoardAddress(){
        return board.getAddress();
    }

    public String getBoardAppointedTime(){
        return board.getAppointedTime();
    }
}
