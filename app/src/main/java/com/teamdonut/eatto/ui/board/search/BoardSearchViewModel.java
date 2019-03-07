package com.teamdonut.eatto.ui.board.search;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.data.model.kakao.KakaoRepository;
import com.teamdonut.eatto.ui.board.BoardNavigator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class BoardSearchViewModel extends ViewModel {

    private BoardNavigator navigator;

    private CompositeDisposable disposables = new CompositeDisposable();
    private KakaoRepository kakaoRepository = KakaoRepository.getInstance();

    private final MutableLiveData<List<Document>> documents = new MutableLiveData<>(); //search result
    private boolean isEndDocuments = false; //check document is end or not.

    private final MutableLiveData<String> etKeywordHint = new MutableLiveData<>(); //hint


    public void fetchEtKeywordHint(String kakaoKey, String longitude, String latitude, String defaultAddress) {
        disposables.add(kakaoRepository.getMyAddress(kakaoKey, longitude, latitude)
                .subscribe(data -> {
                    JsonArray jsonArray = data.getAsJsonArray("documents");
                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                    etKeywordHint.setValue(jsonObject.get("address_name").getAsString());
                }, e -> {
                    e.printStackTrace();
                    etKeywordHint.setValue(defaultAddress);
                }));
    }

    //카카오 REST API - 키워드로 장소검색
    public void fetchAddressResult(String authorization, String query, int page, int size) {
        disposables.add(kakaoRepository.getAddress(authorization, query, page, size)
                .subscribe(data -> {
                    boolean isEnd = data.getMeta().isEnd();

                    isEndDocuments = isEnd;

                    if (page == 1) {
                        documents.postValue(data.getDocuments());
                    } else {
                        documents.getValue().addAll(data.getDocuments());
                        documents.postValue(documents.getValue());
                    }
                }));
    }

    public void sendDocument(Document document) {
        navigator.sendDocument(document);
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }

    public CompositeDisposable getDisposables() {
        return disposables;
    }

    public void setDisposables(CompositeDisposable disposables) {
        this.disposables = disposables;
    }

    public MutableLiveData<String> getEtKeywordHint() {
        return etKeywordHint;
    }

    @NonNull
    public MutableLiveData<List<Document>> getDocuments() {
        return documents;
    }

    public boolean isEndDocuments() {
        return isEndDocuments;
    }

    public void setNavigator(BoardNavigator navigator) {
        this.navigator = navigator;
    }
}
