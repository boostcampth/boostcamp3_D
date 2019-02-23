package com.teamdonut.eatto.ui.board;

import androidx.annotation.NonNull;
import androidx.databinding.*;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appyvet.materialrangebar.RangeBar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.data.User;
import com.teamdonut.eatto.data.kakao.Document;
import com.teamdonut.eatto.data.model.board.BoardRepository;
import com.teamdonut.eatto.data.model.kakao.KakaoRepository;
import com.teamdonut.eatto.ui.board.search.BoardSearchAdapter;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@BindingMethods({
        @BindingMethod(
                type = RangeBar.class,
                attribute = "onRangeBarChange",
                method = "setOnRangeBarChangeListener"
        )
})

public class BoardViewModel extends ViewModel {

    private BoardNavigator mNavigator;
    public ObservableField<String> time = new ObservableField<>();
    public MutableLiveData<String> etKeywordHint = new MutableLiveData<>();
    private CompositeDisposable disposables = new CompositeDisposable();

    private int mMinAge = 15;
    private int mMaxAge = 80;
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
    private MutableLiveData<List<Board>> mOwnBoards = new MutableLiveData<>(); //own
    private MutableLiveData<List<Board>> mParticipateBoards = new MutableLiveData<>(); //participate
    private MutableLiveData<Board> openBoardEvent = new MutableLiveData<>(); //open board event.

    private Realm realm = Realm.getDefaultInstance();
    public ObservableField<String> mAddress = new ObservableField<>();

    //Board Add
    private ObservableInt boardAddMaxPerson = new ObservableInt(2);
    private ObservableInt boardAddBudget = new ObservableInt(5000);

    private KakaoRepository kakaoRepository = KakaoRepository.getInstance();
    private BoardRepository boardRepository = BoardRepository.getInstance();

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

    public void onClickBoardAdd() {
        mNavigator.onAddBoardClick();
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
        disposables.add(kakaoRepository.getAddress(authorization, query, page, size)
                .subscribe(data -> {
                    if (data.getDocuments().size() == 0) {
                        mNavigator.onShowSnackBar();
                    } else {
                        if ((double) (data.getMeta().getPageableCount() / 10) >= page - 1) {
                            boardSearchAdapter.addItems(data.getDocuments());
                        }
                    }
                }));
    }

    //사용자가 생성한 게시글 불러오기
    public void fetchOwnBoard() {
        disposables.add(boardRepository.getOwnBoard()
                .subscribe(data -> {
                    mOwnBoards.postValue(data);
                }, e -> {
                    e.printStackTrace();
                }));
    }

    //사용자가 참여중인 게시글 불러오기
    public void fetchParticipateBoard() {
        disposables.add(boardRepository.getUserParticipatedBoard()
                .subscribe(data -> {
                    mParticipateBoards.postValue(data);
                }, Throwable::printStackTrace));
    }

    public Board makeBoard(String title) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String appointedTime = df.format(currentTime);
        appointedTime += " " + Integer.toString(mHourOfDay) + ":" + Integer.toString(mMinute) + ":00";

        User user = RealmDataHelper.getUser();

        Board board = new Board(title,
                mAddressName,
                appointedTime,
                mPlaceName,
                boardAddMaxPerson.get(),
                mMinAge,
                mMaxAge,
                Float.parseFloat(mLongitude),
                Float.parseFloat(mLatitude),
                user.getKakaoId(),
                user.getPhoto(),
                user.getNickName()
        );

        board.setBudget(Integer.toString(boardAddBudget.get()));

        return board;
    }

    public void addBoard(Board board) {
        disposables.add(boardRepository.postBoard(board)
                .subscribe(data -> {
                    if (mNavigator != null) {
                        mNavigator.onBoardAddFinish();
                    }
                }, e -> {
                }));
    }

    //인원
    public void onPersonUpClick() {
        if (boardAddMaxPerson.get() + 1 <= 10)
            boardAddMaxPerson.set(boardAddMaxPerson.get() + 1);
    }

    public void onPersonDownClick() {
        if (boardAddMaxPerson.get() - 1 >= 2)
            boardAddMaxPerson.set(boardAddMaxPerson.get() - 1);
    }

    //금액
    public void onBudgetUpClick() {
        if (boardAddBudget.get() + 5000 <= 100000)
            boardAddBudget.set(boardAddBudget.get() + 5000);
    }

    public void onBudgetDownClick() {
        if (boardAddBudget.get() - 5000 >= 0)
            boardAddBudget.set(boardAddBudget.get() - 5000);
    }

    public ObservableInt getBoardAddMaxPerson() {
        return boardAddMaxPerson;
    }

    public void setBoardAddMaxPerson(ObservableInt boardAddMaxPerson) {
        this.boardAddMaxPerson = boardAddMaxPerson;
    }

    public ObservableInt getBoardAddBudget() {
        return boardAddBudget;
    }

    public void setBoardAddBudget(ObservableInt boardAddBudget) {
        this.boardAddBudget = boardAddBudget;
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public ObservableField<String> getAddress() {
        return mAddress;
    }

    public void setAddress(ObservableField<String> mAddress) {
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


    public String getAddressName() {
        return mAddressName;
    }

    public void setAddressName(String mAddressName) {
        this.mAddressName = mAddressName;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public void setPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public void setNavigator(BoardNavigator mNavigator) {
        this.mNavigator = mNavigator;
    }

    public int getHourOfDay() {
        return mHourOfDay;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setHourOfDay(int mHourOfDay) {
        this.mHourOfDay = mHourOfDay;
    }

    public void setMinute(int mMinute) {
        this.mMinute = mMinute;
    }

    public MutableLiveData<List<Board>> getParticipateBoards() {
        return mParticipateBoards;
    }

    public MutableLiveData<List<Board>> getOwnBoards() {
        return mOwnBoards;
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        realm.close();
        super.onCleared();
    }

    public MutableLiveData<Board> getOpenBoardEvent() {
        return openBoardEvent;
    }
}
