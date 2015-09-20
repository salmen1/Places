package com.ciklum.testing.places.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //nothing to do
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //nothing to do
    }

    @Override
    public void afterTextChanged(Editable s) {
        //nothing to do
    }
}
