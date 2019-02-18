package com.teamdonut.eatto.ui.map.bottomsheet;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.teamdonut.eatto.R;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

public class MapBoardBinding {

    @BindingAdapter({"highlightNumber", "mainText"})
    public static void setSpannableText(TextView view, int number, String mainText) {
        if (number >= 0) {
            String numberText = String.valueOf(number);

            SpannableStringBuilder sb = new SpannableStringBuilder(numberText);

            sb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(view.getContext(), R.color.colorHungryRed))
                    , 0, numberText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.append(mainText);

            view.setText(sb, TextView.BufferType.SPANNABLE);
        }
    }
}

