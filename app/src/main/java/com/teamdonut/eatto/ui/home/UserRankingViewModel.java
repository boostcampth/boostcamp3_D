package com.teamdonut.eatto.ui.home;

import com.teamdonut.eatto.data.User;

public class UserRankingViewModel {
    private User user;
    private int rank;
    public UserRankingViewModel(User user, int rank){
        this.user = user;
        this.rank = rank;
    }

    public String getUserNickName() {
        return user.getNickName();
    }

    public String getUserScoreSum() {
        return String.valueOf(user.getScoreSum());
    }

    public String getRank(){
        return String.valueOf(rank+1);
    }
}
