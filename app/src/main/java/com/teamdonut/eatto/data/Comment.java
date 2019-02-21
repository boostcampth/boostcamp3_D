package com.teamdonut.eatto.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    @Expose(serialize = false)
    private int id;

    @SerializedName("board_id")
    @Expose
    private int boardId;

    @SerializedName("writer_name")
    @Expose
    private String writerName;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("write_time")
    @Expose(serialize = false)
    private String writeTime;

    public Comment(int boardId, String writerName, String content) {
        this.boardId = boardId;
        this.writerName = writerName;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }
}
