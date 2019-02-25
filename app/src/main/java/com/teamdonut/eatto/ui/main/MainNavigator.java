package com.teamdonut.eatto.ui.main;


import androidx.fragment.app.Fragment;

interface MainNavigator {
    void changeScreen(int itemId, Fragment fragment);
}
