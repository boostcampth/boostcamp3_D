package com.teamdonut.eatto.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoardAddInformation {

    @SerializedName("board_id")
    @Expose
    private int boardId;

    public BoardAddInformation(int boardId) {
        this.boardId = boardId;
    }
}
