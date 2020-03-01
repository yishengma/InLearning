package com.inlearning.app.director.course.edit;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.inlearning.app.R;

public class NameItemView implements View.OnClickListener{
    private FrameLayout mItemView;
    private TextView mNameView;
    private View mRootView;
    private EditInfoView mEditInfoView;
    public NameItemView(View rootView) {
        mRootView = rootView;
        mItemView = rootView.findViewById(R.id.fl_edit_course_name);
        mNameView = rootView.findViewById(R.id.tv_edit_course_name);
        mItemView.setOnClickListener(this);
        mEditInfoView = rootView.findViewById(R.id.edit_name_view);
        mEditInfoView.setTitle("课程名字");
        mEditInfoView.setHideListener(new EditInfoView.HideListener() {
            @Override
            public void onHide(String content) {
                mNameView.setText(content);
            }
        });
    }

    @Override
    public void onClick(View view) {
        mEditInfoView.setVisibility(View.VISIBLE);
    }

    public String getContent() {
        return mNameView.getText().toString();
    }
}
