package com.teamdonut.eatto.ui.board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardDetailActivityBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class BoardDetailActivity extends AppCompatActivity implements BoardNavigator{

    private BoardDetailActivityBinding binding;
    private BoardViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_detail_activity);
        mViewModel = new BoardViewModel(this);
        binding.setViewmodel(mViewModel);

        Intent intent = getIntent();

        Log.d("recieve test",intent.getExtras().getString("addressName"));
        Log.d("receive test", intent.getExtras().getInt("maxPerson")+"");
            Board board = new Board(intent.getStringExtra("title"),
                    intent.getExtras().getString("addressName"),
                    intent.getExtras().getString("appointedTime"),
                    intent.getExtras().getString("restaurantName"),
                    intent.getExtras().getInt("maxPerson"),
                    intent.getExtras().getInt("minAge"),
                    intent.getExtras().getInt("maxAge"),
                    intent.getExtras().getDouble("longitude"),
                    intent.getExtras().getDouble("latitude"),
                    intent.getExtras().getLong("writerId"),
                    intent.getExtras().getString("writerPhoto"),
                    intent.getExtras().getString("wrtierName"));

        binding.setBoard(board);

        /*intent.putExtra("title", board.getTitle());
        intent.putExtra("restaurantName", board.getRestaurantName());
        intent.putExtra("minAge", board.getMinAge());
        intent.putExtra("maxAge", board.getMaxAge());
        intent.putExtra("budget", board.getBudget());
        intent.putExtra("appointedTime", board.getAppointedTime());
        intent.putExtra("content", board.getContent());
        intent.putExtra("currentPerson", board.getCurrentPerson());
        intent.putExtra("maxPerson", board.getMaxPerson());
*/

    }

    //액티비티 종료
    @Override
    public void onBoardDetailExitClick() {
        Log.d("arrived","check2");
        finish();
    }
}
