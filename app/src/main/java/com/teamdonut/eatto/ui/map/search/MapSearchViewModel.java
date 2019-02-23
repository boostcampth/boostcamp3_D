package com.teamdonut.eatto.ui.map.search;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.common.util.Strings;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Filter;
import com.teamdonut.eatto.data.Keyword;
import com.teamdonut.eatto.data.model.kakao.KakaoRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MapSearchViewModel extends ViewModel {

    private MapSearchNavigator mNavigator;
    private CompositeDisposable disposables = new CompositeDisposable();

    private Realm realm = Realm.getDefaultInstance();

    private final MutableLiveData<String> etKeywordHint = new MutableLiveData<>();
    private final MutableLiveData<Boolean> resetFilter = new MutableLiveData<>();

    private final ObservableInt minTime = new ObservableInt(0);
    private final ObservableInt maxTime = new ObservableInt(23);
    private final ObservableInt minAge = new ObservableInt(15);
    private final ObservableInt maxAge = new ObservableInt(80);
    private final ObservableField<String> budget = new ObservableField<>("");
    private final ObservableInt people = new ObservableInt(10);

    private KakaoRepository kakaoRepository = KakaoRepository.getInstance();

    private MutableLiveData<Filter> searchCondition = new MutableLiveData<>();

    public RealmResults<Keyword> fetchKeywords() {
        return realm.where(Keyword.class).sort("searchDate", Sort.DESCENDING).limit(17).findAll();
    }

    public void fetchEtKeywordHint(String kakaoKey, String longtitude, String latitude, String defaultAddress) {
        disposables.add(kakaoRepository.getMyAddress(kakaoKey, longtitude, latitude)
                .subscribe(data -> {
                    JsonArray jsonArray = data.getAsJsonArray("documents");
                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                    etKeywordHint.setValue(jsonObject.get("address_name").getAsString());
                }, e -> {
                    e.printStackTrace();
                    etKeywordHint.setValue(defaultAddress);
                }));
    }

    public void onRecentKeywordRemoveClick() {
        RealmDataHelper.removeAllKeyword(realm);
    }

    public void onGoSearchClick(String keyword) {
        saveRecentKeyword(keyword); //save recent keyword.
        setSearchCondition(keyword);
    }

    private void setSearchCondition(String keyword) {
        if (Strings.isEmptyOrWhitespace(budget.get())) {
            budget.set("0");
        }
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
        resetFilter.setValue(true);

        minTime.set(0);
        maxTime.set(23);
        people.set(10);
        budget.set("");
    }

    public void onSubmitFilterClick(String minTimeText, String maxTimeText, String budgetText) {
        minTime.set(Integer.parseInt(minTimeText));
        maxTime.set(Integer.parseInt(maxTimeText));
        budget.set(Strings.isEmptyOrWhitespace(budgetText) ? "0" : budgetText);

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

    public ObservableInt getMinTime() {
        return minTime;
    }

    public ObservableInt getMaxTime() {
        return maxTime;
    }

    public MutableLiveData<Filter> getSearchCondition() {
        return searchCondition;
    }

    public MutableLiveData<String> getEtKeywordHint() {
        return etKeywordHint;
    }

    public ObservableInt getPeople() {
        return people;
    }

    public MutableLiveData<Boolean> getResetFilter() {
        return resetFilter;
    }

    public ObservableField<String> getBudget() {
        return budget;
    }
}
