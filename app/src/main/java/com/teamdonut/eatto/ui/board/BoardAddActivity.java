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

import com.google.android.gms.common.util.Strings;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.util.SnackBarUtil;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.BoardAddActivityBinding;
import com.teamdonut.eatto.ui.board.search.BoardSearchActivity;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import io.realm.Realm;

public class BoardAddActivity extends AppCompatActivity implements BoardNavigator {
    private BoardAddActivityBinding binding;
    private BoardViewModel mViewModel;
    private final int BOARD_SEARCH_REQUEST = 101;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_add_activity);
        mViewModel = new BoardViewModel(this);
        binding.setViewmodel(mViewModel);

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
                break;
            case R.id.menu_write:
                //게시글 추가
                addBoardProcess();
                break;
        }
        return true;
    }


    @Override
    public void onTimePickerClick() {

        Calendar cal = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String setTime = hourOfDay + "시 " + minute + "분";
                mViewModel.setHourOfDay(hourOfDay);
                mViewModel.setMinute(minute);
                mViewModel.time.set(setTime);
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(getApplicationContext()));

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public boolean inputCheck() {

        boolean titleCheck = Strings.isEmptyOrWhitespace(binding.etInputTitle.getText().toString());
        boolean addressCheck = Strings.isEmptyOrWhitespace(binding.tvInputAddress.getText().toString());
        boolean appointedTimeCheck = Strings.isEmptyOrWhitespace(binding.tvInputTime.getText().toString());
        boolean maxPersonCheck = Strings.isEmptyOrWhitespace(binding.etInputMaxPerson.getText().toString());

        if (titleCheck || addressCheck || appointedTimeCheck || maxPersonCheck)
            return false;
        else
            return true;

    }

    //게시글 추가 함수
    public void addBoardProcess() {

        if (inputCheck()) {

            Board board = mViewModel.makeBoard(binding.etInputTitle.getText().toString(), Integer.parseInt(binding.etInputMaxPerson.getText().toString()));

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

            mViewModel.addBoard(board);

        } else {
            SnackBarUtil.showSnackBar(binding.rlBoardAddLayout, R.string.board_add_snack_bar);
        }

    }

    public void onBoardAddFinish() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    public void onBoardSearchShowClick() {
        Intent intent = new Intent(this, BoardSearchActivity.class);
        startActivityForResult(intent, BOARD_SEARCH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case BOARD_SEARCH_REQUEST:
                    mViewModel.getAddress().set( "("+data.getStringExtra("placeName")+") "+data.getStringExtra("addressName"));
                    mViewModel.setPlaceName(data.getStringExtra("placeName"));
                    mViewModel.setAddressName(data.getStringExtra("addressName"));
                    mViewModel.setLongitude(data.getStringExtra("x"));
                    mViewModel.setLatitude(data.getStringExtra("y"));
                    break;
            }
        }
    }

}
