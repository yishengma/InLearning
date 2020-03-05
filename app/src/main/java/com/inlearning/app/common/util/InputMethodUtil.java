package com.inlearning.app.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputMethodUtil {
    public static void forceToHideSoftInput(View view, Activity activity) {
        View focusView = activity.getCurrentFocus();
        if (focusView == null) {
            focusView = view;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
    }

    public static void forceToShowSoftInput(View view, Activity activity) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }
}
