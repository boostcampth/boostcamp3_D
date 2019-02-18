package com.teamdonut.eatto.data;

import com.teamdonut.eatto.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * 검색 조건에 관한 클래스.
 */
public class Filter extends BaseObservable {

    private String keyword;
    private int minTime;
    private int maxTime;
    private int minAge;
    private int maxAge;
    private int maxPeople;
    private int budget;

    public Filter(String keyword, int minTime, int maxTime, int minAge, int maxAge, int maxPeople, int budget) {
        this.keyword = keyword;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.maxPeople = maxPeople;
        this.budget = budget;
    }

    public String getKeyword() {
        return keyword;
    }

    @Bindable
    public int getMinTime() {
        return minTime;
    }

    @Bindable
    public int getMaxTime() {
        return maxTime;
    }

    @Bindable
    public int getMinAge() {
        return minAge;
    }

    @Bindable
    public int getMaxAge() {
        return maxAge;
    }

    @Bindable
    public int getMaxPeople() {
        return maxPeople;
    }

    @Bindable
    public int getBudget() {
        return budget;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
        notifyPropertyChanged(BR.minTime);
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        notifyPropertyChanged(BR.maxTime);
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
        notifyPropertyChanged(BR.minAge);
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
        notifyPropertyChanged(BR.maxAge);
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
        notifyPropertyChanged(BR.maxPeople);
    }

    public void setBudget(int budget) {
        this.budget = budget;
        notifyPropertyChanged(BR.budget);
    }
}
