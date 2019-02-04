package com.teamdonut.eatto.ui.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.teamdonut.eatto.R;
import com.teamdonut.eatto.databinding.BoardAddActivityBinding;

public class BoardAddActivity extends AppCompatActivity {

    private BoardAddActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.board_add_activity);

        initToolbar();
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
                Toast.makeText(this, "뒤로가기 버튼 클릭", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_write:
                Toast.makeText(this, "등록 버튼 클릭", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
