package com.teamdonut.eatto.ui.board;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.google.android.gms.common.util.Strings;
import com.google.android.material.snackbar.Snackbar;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardAddActivityBinding;
import com.teamdonut.eatto.model.BoardAPI;
import com.teamdonut.eatto.model.ServiceGenerator;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BoardAddActivity extends AppCompatActivity implements BoardNavigator {
    private BoardAddActivityBinding binding;
    private BoardViewModel mViewModel;
    private int hourOfDay;
    private int minute;
    private CompositeDisposable disposables;
    private final int BOARD_SEARCH_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_add_activity);
        mViewModel = new BoardViewModel(this);
        binding.setViewmodel(mViewModel);
        disposables = new CompositeDisposable();

        initToolbar();
        editTextSetMaxLine(binding.etInputContent, 15);
    }

    public void editTextSetMaxLine(EditText editText, int lines) {
        editText.addTextChangedListener(new TextWatcher() {
            String previousString = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getLineCount() >= lines) {
                    editText.setText(previousString);
                    editText.setSelection(editText.length());
                }
            }
        });
    }

    public void initToolbar() {
        //setting Toolbar
        setSupportActionBar(binding.tbBoardAdd);

        //Toolbar nav button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void onTimePickerClick() {

        Calendar cal = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String setTime = hourOfDay + "시 " + minute + "분";
                setHourOfDay(hourOfDay);
                setMinute(minute);
                mViewModel.time.set(setTime);
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(getApplicationContext()));

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public boolean inputCheck() {

        boolean titleCheck = Strings.isEmptyOrWhitespace(binding.etInputTitle.getText().toString());
        boolean addressCheck = Strings.isEmptyOrWhitespace(binding.tvInputAddress.getText().toString());
        boolean appointedTimeCheck = binding.tvInputTime.getText().toString().equals(getResources().getText(R.string.board_tv_time_hint).toString());
        boolean maxPersonCheck = Strings.isEmptyOrWhitespace(binding.etInputMaxPerson.getText().toString());

        if (titleCheck || addressCheck || appointedTimeCheck || maxPersonCheck)
            return false;
        else
            return true;

    }

    //게시글 추가에 사용될 Board 객체 생성
    public Board makeBoard() {

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String appointedTime = df.format(currentTime);
        appointedTime += " " + Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + ":00";

        Board board = new Board(binding.etInputTitle.getText().toString(),
                binding.tvInputAddress.getText().toString(), appointedTime,
                "맥도날드",
                Integer.parseInt(binding.etInputMaxPerson.getText().toString()),
                mViewModel.getMinAge(), mViewModel.getMaxAge(),
                127.0123,
                36.123,
                1
        );

        if (Strings.isEmptyOrWhitespace(binding.etInputContent.getText().toString())) {
            board.setContent("");
        } else {
            board.setContent(binding.etInputContent.getText().toString());
        }

        if (Strings.isEmptyOrWhitespace(binding.etInputBudget.getText().toString())) {
            board.setBudget("0");
        } else {
            board.setBudget(binding.etInputBudget.getText().toString());
        }

        return board;
    }

    //게시글 추가 함수
    public void addBoardProcess() {

        if (inputCheck()) {

            Board board = makeBoard();

            BoardAPI service = ServiceGenerator.createService(BoardAPI.class, ServiceGenerator.BASE);
            Single<Board> result = service.addBoard(board);

            disposables.add(
                    result.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((data) -> {
                                        setResult(RESULT_OK);
                                        finish();
                                    }, (e) -> {
                                        e.printStackTrace();
                                    }
                            )
            );

        } else {
            Snackbar.make(binding.rlBoardAddLayout, R.string.board_add_snack_bar, Snackbar.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }

    @Override
    public void onBoardSearchShowClick() {
        Intent intent = new Intent(this, BoardSearchActivity.class);
        startActivityForResult(intent, BOARD_SEARCH_REQUEST);
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
