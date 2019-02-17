package com.teamdonut.eatto.ui.map.search;

import com.appyvet.materialrangebar.RangeBar;
import com.google.android.gms.common.util.Strings;
import com.teamdonut.eatto.common.helper.RealmDataHelper;
import com.teamdonut.eatto.data.Filter;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.realm.Realm;

@BindingMethods({
        @BindingMethod(
                type = RangeBar.class,
                attribute = "onRangeBarChange",
                method = "setOnRangeBarChangeListener"
        )
})

public class MapSearchViewModel extends ViewModel {

    private MapSearchNavigator mNavigator;

    private Realm realm = Realm.getDefaultInstance();

    private final ObservableInt minTime = new ObservableInt(0);
    private final ObservableInt maxTime = new ObservableInt(23);
    private final ObservableInt minAge = new ObservableInt(15);
    private final ObservableInt maxAge = new ObservableInt(80);
    private final ObservableInt maxPeople = new ObservableInt(10);
    private final ObservableInt budget = new ObservableInt(0);

    private MutableLiveData<Filter> searchCondition = new MutableLiveData<>();

    public void onGoSearchClick(String keyword) {
        saveRecentKeyword(keyword); //save recent keyword.

        searchCondition.setValue(
                new Filter(keyword, minTime.get(), maxTime.get(), minAge.get(), maxAge.get(), maxPeople.get(), budget.get()));
    }

    private void saveRecentKeyword(String keyword) {
        if (!Strings.isEmptyOrWhitespace(keyword)) { //insert keyword
            RealmDataHelper.insertKeyword(realm, keyword);
        }
    }

    public void onOpenFilterClick() {
        mNavigator.openNavigationView();
    }

    public void onCloseFilterClick() {
        mNavigator.closeNavigationView();
    }

    public void onClearFilterClick() {
        //
    }

    public void onAgeRangeBarChanged(RangeBar rangeBar, int leftPinIndex, int rightPinIndex,
                                     String leftPinValue, String rightPinValue) {
        minAge.set(Integer.parseInt(leftPinValue));
        maxAge.set(Integer.parseInt(rightPinValue));
    }

    public void onPeopleRangeBarChanged(RangeBar rangeBar, int leftPinIndex, int rightPinIndex,
                                        String leftPinValue, String rightPinValue) {
        maxPeople.set(Integer.parseInt(rightPinValue));
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

    public ObservableInt getMaxPeople() {
        return maxPeople;
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
}
