package com.teamdonut.eatto.ui.board.search;

import com.teamdonut.eatto.data.kakao.Document;

public class BoardSearchViewModel {

    private Document document;

    public BoardSearchViewModel(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }
}
