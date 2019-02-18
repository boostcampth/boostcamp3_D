package com.teamdonut.eatto.ui.board;

import android.util.Log;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import com.appyvet.materialrangebar.RangeBar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.model.BoardAPI;
import com.teamdonut.eatto.model.ServiceGenerator;
import com.teamdonut.eatto.ui.board.search.BoardSearchAdapter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.databinding.BindingAdapter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    private BoardAPI kakaoService = ServiceGenerator.createService(BoardAPI.class, ServiceGenerator.KAKAO);
    private BoardAPI service = ServiceGenerator.createService(BoardAPI.class,ServiceGenerator.BASE);
    private int mMinAge;
    private int mMaxAge;
    private int mHourOfDay;
    private int mMinute;
    private String mAddressName;
    private String mPlaceName;
    private String mLongitude;
    private String mLatitude;

    //use BoardSearch
    @NonNull
    private ObservableArrayList<Document> documents = new ObservableArrayList<>();
    private BoardSearchAdapter boardSearchAdapter = new BoardSearchAdapter(documents);

    //Board Fragment
    private ObservableArrayList<Board> joinBoards = new ObservableArrayList<>();
    private ObservableArrayList<Board> ownBoards = new ObservableArrayList<>();
    private BoardOwnAdapter boardOwnAdapter = new BoardOwnAdapter(ownBoards);
    private BoardJoinAdapter boardJoinAdapter = new BoardJoinAdapter(joinBoards);

    private Realm realm = Realm.getDefaultInstance();

    public ObservableField<String> mAddress = new ObservableField<>();


    public BoardViewModel() {

    }

    public BoardViewModel(BoardNavigator navigator) {
        mNavigator = navigator;
    }

    public void fetchEtKeywordHint(String kakaoKey, String longtitude, String latitude, String defaultAddress) {
        disposables.add(
                kakaoService.getMyAddress(kakaoKey, longtitude, latitude)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(jsonElements -> {
                                    JsonArray jsonArray = jsonElements.getAsJsonArray("documents");
                                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                                    etKeywordHint.setValue(jsonObject.get("address_name").getAsString());
                                }, e -> {
                                    e.printStackTrace();
                                    etKeywordHint.setValue(defaultAddress);
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

    //카카오 REST API - 키워드로 장소검색
    public void fetchAddressResult(String authorization, String query, int page, int size) {
        disposables.add(
                kakaoService.getAddress(authorization, query, page, size)
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
                                            boardSearchAdapter.addItems(data.getDocuments());
                                        }
                                    }

                                }, (e) -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    //사용자가 생성한 게시글 불러오기
    public void fetchOwnBoardResult() {
        disposables.add(
                service.getUserCreatedBoard(RealmDataHelper.getAccessId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((data) -> {
                                boardOwnAdapter.addItems(data);
                                }, (e) -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    //사용자가 참여중인 게시글 불러오기
    public void fetchJoinBoardResult() {
        disposables.add(
                service.getUserParticipatedBoard(RealmDataHelper.getAccessId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((data) -> {
                                    boardJoinAdapter.addItems(data);
                                }, (e) -> {
                                    e.printStackTrace();
                                }
                        )
        );
    }

    @BindingAdapter("transdate")
    public static void setText(TextView view, String date) {
        String sub = date.substring(11,16);
        view.setText(sub);
    }

    public void onDestroyBoardViewModel() {
        disposables.dispose();
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
                RealmDataHelper.getUser().getKakaoId(),
                RealmDataHelper.getUser().getPhoto()
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

    public ObservableField<String> getTime() {
        return time;
    }

    public ObservableField<String> getmAddress() {
        return mAddress;
    }

    public void setmAddress(ObservableField<String> mAddress) {
        this.mAddress = mAddress;
    }

    @NonNull
    public ObservableArrayList<Document> getDocuments() {
        return documents;
    }

    public BoardSearchAdapter getBoardSearchAdapter() {
        return boardSearchAdapter;
    }

    public void setBoardSearchAdapter(BoardSearchAdapter boardSearchAdapter) {
        this.boardSearchAdapter = boardSearchAdapter;
    }

    public BoardOwnAdapter getBoardOwnAdapter() {
        return boardOwnAdapter;
    }

    public void setBoardOwnAdapter(BoardOwnAdapter boardOwnAdapter) {
        this.boardOwnAdapter = boardOwnAdapter;
    }

    public BoardJoinAdapter getBoardJoinAdapter() {
        return boardJoinAdapter;
    }

    public void setBoardJoinAdapter(BoardJoinAdapter boardJoinAdapter) {
        this.boardJoinAdapter = boardJoinAdapter;
    }

    public ObservableArrayList<Board> getJoinBoards() {
        return joinBoards;
    }

    public void setJoinBoards(ObservableArrayList<Board> joinBoards) {
        this.joinBoards = joinBoards;
    }

    public ObservableArrayList<Board> getOwnBoards() {
        return ownBoards;
    }

    public void setOwnBoards(ObservableArrayList<Board> ownBoards) {
        this.ownBoards = ownBoards;
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

    public String getmAddressName() {
        return mAddressName;
    }

    public void setmAddressName(String mAddressName) {
        this.mAddressName = mAddressName;
    }

    public String getmPlaceName() {
        return mPlaceName;
    }

    public void setmPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
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
        realm.close();
    }
}
