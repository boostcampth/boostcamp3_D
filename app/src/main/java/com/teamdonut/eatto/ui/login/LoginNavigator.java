package com.teamdonut.eatto.ui.login;

public interface LoginNavigator {

    void redirectLoginActivity();
    void redirectMainActivity();

    void saveUser(long id, String name, String profileImage);
}
