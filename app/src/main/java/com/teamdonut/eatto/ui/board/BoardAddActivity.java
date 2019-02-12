package com.teamdonut.eatto.ui.board;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.BoardAddActivityBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class BoardAddActivity extends AppCompatActivity {
    private BoardAddActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_add_activity);
        initToolbar();
        EditTextSetMaxLine(binding.etInputContent, 15);
    }

    public void initToolbar() {
        //setting Toolbar
        setSupportActionBar(binding.tbBoardAdd);

        //Toolbar nav button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_24dp);
    }

    public void EditTextSetMaxLine(EditText editText, int lines){
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
                return true;
            case R.id.menu_write:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
