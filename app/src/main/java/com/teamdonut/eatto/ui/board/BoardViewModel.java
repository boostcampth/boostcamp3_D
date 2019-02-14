package com.teamdonut.eatto.ui.board;

import android.content.Context;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.appyvet.materialrangebar.RangeBar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.model.BoardSearchAPI;
import com.teamdonut.eatto.model.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
    public MutableLiveData<String> etKeywordHint = new MutableLiveData<>();
    private CompositeDisposable disposables = new CompositeDisposable();
    private BoardSearchAPI service = ServiceGenerator.createService(BoardSearchAPI.class, ServiceGenerator.KAKAO);
    private int minAge;
    private int maxAge;

    public BoardViewModel() {

    }

    public BoardViewModel(BoardNavigator navigator) {
        mNavigator = navigator;
    }

    public void getEtKeywordHint(Context context){
        disposables.add(
                service.getMyAddress(
                        context.getResources().getString(R.string.kakao_rest_api_key)
                        , ActivityUtils.getStrValueSharedPreferences(context,"gps","longitude")
                        , ActivityUtils.getStrValueSharedPreferences(context,"gps","latitude"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(jsonElements -> {
                                    JsonArray jsonArray = jsonElements.getAsJsonArray("documents");
                                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                                    etKeywordHint.setValue(jsonObject.get("address_name").getAsString());
                                },throwable ->{
                                    throwable.printStackTrace();
                                    etKeywordHint.setValue(context.getString(R.string.all_default_address));
                                }
                        )
        );
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

    public void onDestroyViewModel(){
        disposables.dispose();
    }
}
