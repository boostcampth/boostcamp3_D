package com.teamdonut.eatto.ui.search;

import com.appyvet.materialrangebar.RangeBar;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.ObservableInt;

@BindingMethods({
        @BindingMethod(
                type = RangeBar.class,
                attribute = "onRangeBarChange",
                method = "setOnRangeBarChangeListener"
        )
})

public class SearchViewModel {

    private SearchNavigator mNavigator;

    private final ObservableInt minAge = new ObservableInt(15);
    private final ObservableInt maxAge = new ObservableInt(80);

    private final ObservableInt minPeople = new ObservableInt(2);
    private final ObservableInt maxPeople = new ObservableInt(10);


    public SearchViewModel(SearchNavigator mNavigator) {
        this.mNavigator = mNavigator;
    }


    public void onGoSearchClick() {
        mNavigator.goSearch();
    }

    public void onOpenFilterClick() {
        mNavigator.openNavigationView();
    }

    public void onCloseFilterClick() {
        mNavigator.closeNavigationView();
    }

    public void onClearFilterClick() {
        //clear filter.
    }

    public void onAgeRangeBarChanged(RangeBar rangeBar, int leftPinIndex, int rightPinIndex,
                                     String leftPinValue, String rightPinValue) {
        minAge.set(Integer.parseInt(leftPinValue));
        maxAge.set(Integer.parseInt(rightPinValue));
    }

    public void onPeopleRangeBarChanged(RangeBar rangeBar, int leftPinIndex, int rightPinIndex,
                                        String leftPinValue, String rightPinValue) {
        minPeople.set(Integer.parseInt(leftPinValue));
        maxPeople.set(Integer.parseInt(rightPinValue));
    }

    public void onSubmitFilterClick() {
        //submit.
    }

    public ObservableInt getMinAge() {
        return minAge;
    }

    public ObservableInt getMaxAge() {
        return maxAge;
    }

    public ObservableInt getMinPeople() {
        return minPeople;
    }

    public ObservableInt getMaxPeople() {
        return maxPeople;
    }
}
