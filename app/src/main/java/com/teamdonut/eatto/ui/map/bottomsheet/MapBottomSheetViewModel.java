package com.teamdonut.eatto.ui.map.bottomsheet;

import com.teamdonut.eatto.ui.map.MapNavigator;

import org.jetbrains.annotations.NotNull;

import androidx.databinding.ObservableBoolean;

public class MapBottomSheetViewModel {

    @NotNull
    public final ObservableBoolean isSheetExpanded = new ObservableBoolean(false);

    private MapNavigator navigator;


    public MapBottomSheetViewModel(MapNavigator navigator) {
        this.navigator = navigator;
    }

    public void onScrollButtonClick() {
        if (!isSheetExpanded.get()) {
            navigator.setBottomSheetExpand(true);
            isSheetExpanded.set(true);
        } else {
            navigator.setBottomSheetExpand(false);
            isSheetExpanded.set(false);
        }
    }

}

