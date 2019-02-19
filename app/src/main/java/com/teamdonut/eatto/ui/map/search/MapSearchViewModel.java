package com.teamdonut.eatto.ui.map.search;

import com.appyvet.materialrangebar.RangeBar;
import com.google.android.gms.common.util.Strings;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.data.Keyword;
import com.teamdonut.eatto.model.BoardAPI;
import com.teamdonut.eatto.model.ServiceGenerator;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

@BindingMethods({
        @BindingMethod(
                type = RangeBar.class,
                attribute = "onRangeBarChange",
                method = "setOnRangeBarChangeListener"
        )
})

public class MapSearchViewModel extends ViewModel {

    private MapSearchNavigator mNavigator;
    private CompositeDisposable disposables = new CompositeDisposable();
    private BoardAPI kakaoService = ServiceGenerator.createService(BoardAPI.class, ServiceGenerator.KAKAO);

    private Realm realm = Realm.getDefaultInstance();

    private final MutableLiveData<String> etKeywordHint = new MutableLiveData<>();

    private final ObservableInt minTime = new ObservableInt(0);
    private final ObservableInt maxTime = new ObservableInt(23);
    private final ObservableInt minAge = new ObservableInt(15);
    private final ObservableInt maxAge = new ObservableInt(80);
    private final ObservableInt budget = new ObservableInt(0);
    private final ObservableInt people = new ObservableInt(2);

    private MutableLiveData<Filter> searchCondition = new MutableLiveData<>();
    private Filter filter = new Filter("", 0, 23, 15, 80, 10, 0);

    public RealmResults<Keyword> fetchKeywords() {
        return realm.where(Keyword.class).sort("searchDate", Sort.DESCENDING).limit(17).findAll();
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

    public void onRecentKeywordRemoveClick() {
        RealmDataHelper.removeAllKeyword(realm);
    }

    public void onGoSearchClick(String keyword) {
        saveRecentKeyword(keyword); //save recent keyword.

        searchCondition.setValue(
                new Filter(keyword, minTime.get(), maxTime.get(), minAge.get(), maxAge.get(), people.get(), budget.get()));
    }

    private void saveRecentKeyword(String keyword) {
        if (!Strings.isEmptyOrWhitespace(keyword)) { //insert keyword
            RealmDataHelper.insertKeyword(realm, keyword);
        }
    }

    public void onPeoplePlusClick() {
        if (people.get() < 10) {
            people.set(people.get() + 1);
        }
    }

    public void onPeopleMinusClick() {
        if (people.get() > 2) {
            people.set(people.get() - 1);
        }
    }

    public void onOpenFilterClick() {
        mNavigator.openNavigationView();
    }

    public void onCloseFilterClick() {
        mNavigator.closeNavigationView();
    }

    public void onClearFilterClick() {
        filter.setMinTime(0);
        filter.setMaxTime(23);
        filter.setMinAge(15);
        filter.setMaxAge(80);
        filter.setMaxPeople(10);
        filter.setBudget(0);
    }

    public void onAgeRangeBarChanged(RangeBar rangeBar, int leftPinIndex, int rightPinIndex,
                                     String leftPinValue, String rightPinValue) {
        minAge.set(Integer.parseInt(leftPinValue));
        maxAge.set(Integer.parseInt(rightPinValue));
    }

    public void onSubmitFilterClick(String minTimeText, String maxTimeText) {
        minTime.set(Integer.parseInt(minTimeText));
        maxTime.set(Integer.parseInt(maxTimeText));

        mNavigator.closeNavigationView();
    }

    @Override
    protected void onCleared() {
        realm.close();
        super.onCleared();
    }

    public void setNavigator(MapSearchNavigator mNavigator) {
        this.mNavigator = mNavigator;
    }

    public ObservableInt getMinAge() {
        return minAge;
    }

    public ObservableInt getMaxAge() {
        return maxAge;
    }

    public ObservableInt getBudget() {
        return budget;
    }

    public ObservableInt getMinTime() {
        return minTime;
    }

    public ObservableInt getMaxTime() {
        return maxTime;
    }

    public MutableLiveData<Filter> getSearchCondition() {
        return searchCondition;
    }

    public Filter getFilter() {
        return filter;
    }

    public MutableLiveData<String> getEtKeywordHint() {
        return etKeywordHint;
    }

    public ObservableInt getPeople() {
        return people;
    }
}
