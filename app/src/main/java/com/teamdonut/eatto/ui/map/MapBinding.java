package com.teamdonut.eatto.ui.map;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.databinding.BindingAdapter;

public class MapBinding {

    @BindingAdapter("keywordDate")
    public static void setKeywordDate(TextView view, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M.dd", Locale.KOREA);

        view.setText(dateFormat.format(date));
    }
}
