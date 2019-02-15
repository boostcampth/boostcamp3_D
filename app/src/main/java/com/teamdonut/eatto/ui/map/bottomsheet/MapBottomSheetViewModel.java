package com.teamdonut.eatto.ui.map.bottomsheet;

import androidx.databinding.ObservableBoolean;
import com.teamdonut.eatto.ui.map.MapNavigator;

public class MapBottomSheetViewModel {

    private MapNavigator mNavigator;
    public final ObservableBoolean isSheetExpanded = new ObservableBoolean(false);

    public MapBottomSheetViewModel(MapNavigator navigator) {
        this.mNavigator = navigator;
    }

    public void onScrollButtonClick() {
        if (!isSheetExpanded.get()) {
            mNavigator.setBottomSheetExpand(true);
        } else {
            mNavigator.setBottomSheetExpand(false);
        }
    }
}

