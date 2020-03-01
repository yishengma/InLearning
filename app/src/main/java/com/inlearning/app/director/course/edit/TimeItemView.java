package com.inlearning.app.director.course.edit;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.inlearning.app.R;

public class TimeItemView implements View.OnClickListener{
    private FrameLayout mItemView;
    private TextView mTimeView;
    private View mRootView;
    private EditInfoView mEditInfoView;
    public TimeItemView(View rootView) {
        mRootView = rootView;
        mItemView = rootView.findViewById(R.id.fl_edit_course_time);
        mTimeView = rootView.findViewById(R.id.tv_edit_course_time);
        mItemView.setOnClickListener(this);
        mEditInfoView = rootView.findViewById(R.id.edit_time_view);
        mEditInfoView.setTitle("课程学时");
        mEditInfoView.setHideListener(new EditInfoView.HideListener() {
            @Override
            public void onHide(String content) {
                mTimeView.setText(content);
            }
        });
    }

    @Override
    public void onClick(View view) {
        mEditInfoView.setVisibility(View.VISIBLE);
    }


    public String getContent() {
        return mTimeView.getText().toString();
    }
}
