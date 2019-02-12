package com.teamdonut.eatto.common.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextUtil {

    public static void editTextSetMaxLine(EditText editText, int lines) {
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

}
