package com.teamdonut.eatto.ui.home;

import com.teamdonut.eatto.data.User;

public class UserRankingViewModel {
    private User user;

    public UserRankingViewModel(User user){
        this.user = user;
    }

    public String getUserNickName() {
        return user.getNickName();
    }

    public String getUserScoreSum() {
        return String.valueOf(user.getScoreSum());
    }
}
