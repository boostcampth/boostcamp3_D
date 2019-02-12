package com.teamdonut.eatto.ui.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardAddActivityBinding;
import com.teamdonut.eatto.model.BoardAddAPI;
import com.teamdonut.eatto.model.ServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BoardAddActivity extends AppCompatActivity implements BoardNavigator {

    private BoardAddActivityBinding binding;
    private BoardViewModel mViewModel;
    private int hourOfDay;
    private int minute;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_add_activity);
        mViewModel = new BoardViewModel(this);
        binding.setViewmodel(mViewModel);
        initToolbar();
        compositeDisposable = new CompositeDisposable();
    }

    public void initToolbar() {
        //setting Toolbar
        setSupportActionBar(binding.tbBoardAdd);

        //Toolbar nav button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_write:
                //게시글 추가
                addBoardProcess();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTimePickerClicked() {

        Calendar cal = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String setTime = hourOfDay + "시 " + minute + "분";
                setHourOfDay(hourOfDay);
                setMinute(minute);
                mViewModel.time.set(setTime);
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(getApplicationContext()));

        dialog.show();
    }

    //게시글 추가에 사용될 Board 객체 생성
    public Board makeBoard() {

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String appointed_time = df.format(currentTime);
        appointed_time += " " + Integer.toString(getHourOfDay()) + ":" + Integer.toString(getMinute()) + ":00";


        return new Board(binding.etInputTitle.getText().toString(),
                binding.etInputAddress.getText().toString(), appointed_time,
                "맥도날드",
                Integer.parseInt(binding.etInputMaxPerson.getText().toString()),
                mViewModel.getMin_age(), mViewModel.getMax_age(),
                Integer.parseInt(binding.etInputBudget.getText().toString()),
                binding.etInputContent.getText().toString(),
                127.0123,
                36.123,
                1
        );

    }

    //게시글 추가 함수
    public void addBoardProcess() {

        Board board = makeBoard();

        BoardAddAPI service = ServiceGenerator.createService(BoardAddAPI.class);
        Single<Board> result = service.addBoard(board);

        compositeDisposable.add(
                result.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((data) -> {
                                    Log.d("result", data.toString());

                                }, (e) -> {
                                    e.printStackTrace();
                                }
                        )
        );

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
