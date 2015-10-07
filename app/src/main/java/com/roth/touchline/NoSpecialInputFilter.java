package com.roth.touchline;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by iuragutu on 04/10/15.
 */
public class NoSpecialInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            if (Character.isLetterOrDigit(source.charAt(i))  ||  source.charAt(i) == ' ' ) {
                return null;
            }
        }
        return "";
    }
}
