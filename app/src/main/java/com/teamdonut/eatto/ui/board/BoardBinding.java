package com.teamdonut.eatto.ui.board;

import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.databinding.BindingAdapter;

public class BoardBinding {

    @BindingAdapter("transdate")
    public static void setText(TextView view, String serverDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            Date date = format.parse(serverDate.replaceAll("Z$", "+0000"));
            view.setText( Integer.toString(date.getHours()) + "시 " + Integer.toString(date.getMinutes()) + "분");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @BindingAdapter("transPeson")
    public static void setPerson(MaterialEditText view, int person) {
        view.setText(Integer.toString(person) + "명");
    }

    @BindingAdapter("transBudget")
    public static void setBudget(MaterialEditText view, int budget) {
        if (budget == 0) {
            view.setText("상관없음");
        } else {
            view.setText(Integer.toString(budget) + "원");
        }
    }
}
