package com.teamdonut.eatto.ui.board.detail;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.teamdonut.eatto.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.ContextCompat;
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

    @BindingAdapter({"commentNumber", "mainText"})
    public static void setSpannableText(TextView view, int number, String mainText) {
        if (number >= 0) {
            String numberText = String.valueOf(number);

            SpannableStringBuilder sb = new SpannableStringBuilder(mainText);
            sb.append(numberText);
            sb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(view.getContext(), R.color.colorHungryRed))
                    , mainText.length(), mainText.length()+numberText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            view.setText(sb, TextView.BufferType.SPANNABLE);
        }
    }
}

