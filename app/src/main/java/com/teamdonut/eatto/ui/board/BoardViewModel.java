package com.teamdonut.eatto.ui.board;

import android.content.Context;
import android.util.Log;

import com.appyvet.materialrangebar.RangeBar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.common.util.ActivityUtils;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.model.BoardAPI;
import com.teamdonut.eatto.model.BoardSearchAPI;
import com.teamdonut.eatto.model.ServiceGenerator;
import com.teamdonut.eatto.ui.board.search.BoardSearchAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Single;
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

public class BoardViewModel {
    private BoardNavigator mNavigator;
    public ObservableField<String> time = new ObservableField<>();
    public MutableLiveData<String> etKeywordHint = new MutableLiveData<>();
    private CompositeDisposable disposables = new CompositeDisposable();
    private BoardSearchAPI service = ServiceGenerator.createService(BoardSearchAPI.class, ServiceGenerator.KAKAO);
    private int mMinAge;
    private int mMaxAge;
    private int mHourOfDay;
    private int mMinute;
    //use BoardSearch
    @NonNull
    private ObservableArrayList<Document> documents = new ObservableArrayList<>();
    private BoardSearchAdapter mAdapter = new BoardSearchAdapter(documents);

    public ObservableField<String> mAddress = new ObservableField<>();
    private String mPlaceName;
    private String mAddressName;
    private String mLongitude;
    private String mLatitude;

    public BoardViewModel() {

    }

    public BoardViewModel(BoardNavigator navigator) {
        mNavigator = navigator;
    }

    public void getEtKeywordHint(Context context) {
        disposables.add(
                service.getMyAddress(
                        context.getResources().getString(R.string.kakao_rest_api_key)
                        , ActivityUtils.getStrValueSharedPreferences(context, "gps", "longitude")
                        , ActivityUtils.getStrValueSharedPreferences(context, "gps", "latitude"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(jsonElements -> {
                                    JsonArray jsonArray = jsonElements.getAsJsonArray("documents");
                                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                                    etKeywordHint.setValue(jsonObject.get("address_name").getAsString());
                                }, e -> {
                                    e.printStackTrace();
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

    //Board_Search 액티비티 연결 이벤트
    public void onBoardSearchShowClick() {
        mNavigator.onBoardSearchShowClick();
    }

    public void setOnRangeBarChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
        mMinAge = Integer.parseInt(leftPinValue);
        mMaxAge = Integer.parseInt(rightPinValue);
    }

    public void fetchAddressResult(String authorization, String query, int page, int size) {
        BoardAPI service = ServiceGenerator.createService(BoardAPI.class, ServiceGenerator.KAKAO);
        Log.d("headercheck", authorization);

        disposables.add(
                service.getAddress(authorization, query, page, size)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((data) -> {


                                    //결과가 없으면
                                    if (data.getDocuments().size() == 0) {
                                        Log.d("resulttest", "cannotfind");
                                        mNavigator.onShowSnackBar();
                                    } else {
                                        //결과가 있을 때
                                        if ((double) (data.getMeta().getPageableCount() / 10) >= page - 1) {
                                            mAdapter.addItems(data.getDocuments());
                                        }
                                    }

                                }, (e) -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    public Board makeBoard(String title, int maxPerson) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String appointedTime = df.format(currentTime);
        appointedTime += " " + Integer.toString(mHourOfDay) + ":" + Integer.toString(mMinute) + ":00";

        Board board = new Board(title,
                mAddressName,
                appointedTime,
                mPlaceName,
                maxPerson,
                mMinAge,
                mMaxAge,
                Float.parseFloat(mLongitude),
                Float.parseFloat(mLatitude),
                RealmDataHelper.getAccessId()
        );

        return board;
    }

    public void addBoard(Board board) {
        BoardAPI service = ServiceGenerator.createService(BoardAPI.class, ServiceGenerator.BASE);
        Single<Board> result = service.addBoard(board);

        disposables.add(
                result.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((data) -> {
                                    if(mNavigator != null)
                                        mNavigator.onBoardAddFinish();
                                }, (e) -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    public void onDestroyBoardViewModel() {
        disposables.dispose();
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public void setmAddress(ObservableField<String> mAddress) {
        this.mAddress = mAddress;
    }

    @NonNull
    public ObservableArrayList<Document> getDocuments() {
        return documents;
    }

    public BoardSearchAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(BoardSearchAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public ObservableField<String> getmAddress() {
        return mAddress;
    }

    public String getmPlaceName() {
        return mPlaceName;
    }

    public void setmPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public String getmAddressName() {
        return mAddressName;
    }

    public void setmAddressName(String mAddressName) {
        this.mAddressName = mAddressName;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public void onDestroyViewModel() {
        disposables.dispose();
    }

    public int getmMinAge() {
        return mMinAge;
    }

    public void setmMinAge(int mMinAge) {
        this.mMinAge = mMinAge;
    }

    public int getmMaxAge() {
        return mMaxAge;
    }

    public void setmMaxAge(int mMaxAge) {
        this.mMaxAge = mMaxAge;
    }

    public int getmHourOfDay() {
        return mHourOfDay;
    }

    public void setmHourOfDay(int mHourOfDay) {
        this.mHourOfDay = mHourOfDay;
    }

    public int getmMinute() {
        return mMinute;
    }

    public void setmMinute(int mMinute) {
        this.mMinute = mMinute;
    }
}
