package com.teamdonut.eatto.ui.board.detail;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.databinding.BindingAdapter;

public class BoardDetailBinding {

    @BindingAdapter("time")
    public static void convertDateToTime(TextView view, String dateText) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
        SimpleDateFormat realFormat = new SimpleDateFormat("HH:mm aaa", Locale.US);
        try {
            Date date = format.parse(dateText);
            view.setText(realFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter("koreanDay")
    public static void convertDateToString(TextView view, String dateText) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
        SimpleDateFormat realFormat = new SimpleDateFormat("M월 dd일 E요일", Locale.KOREA);
        try {
            Date date = format.parse(dateText);
            view.setText(realFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

