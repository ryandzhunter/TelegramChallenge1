package ru.android.challenge.telegramchallenge1.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Egor on 05.05.2015.
 */
public class PhoneCodeEditText extends EditText {
    public PhoneCodeEditText(Context context) {
        super(context);
    }

    public PhoneCodeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhoneCodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart == selEnd && getText().length() != 0) {
            setSelection(Math.max(1, selStart));
        }
    }
}
