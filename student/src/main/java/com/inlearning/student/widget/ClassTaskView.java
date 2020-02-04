package com.inlearning.student.widget;


import android.view.View;
import android.widget.LinearLayout;

public class ClassTaskView {
    private LinearLayout mTaskView;
    private View mRedPointView;


    public ClassTaskView(View taskView, View redPointView) {
        mTaskView = (LinearLayout) taskView;
        mRedPointView = redPointView;

    }

    public void setRedPointVisibility(int visibility) {
        mRedPointView.setVisibility(visibility);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mTaskView.setOnClickListener(listener);
    }

}
