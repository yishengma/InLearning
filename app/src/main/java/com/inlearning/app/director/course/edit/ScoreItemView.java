package com.inlearning.app.director.course.edit;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.inlearning.app.R;

public class ScoreItemView implements View.OnClickListener {
    private FrameLayout mItemView;
    private TextView mScoreView;
    private View mRootView;
    private EditInfoView mEditInfoView;

    public ScoreItemView(View rootView) {
        mRootView = rootView;
        mItemView = rootView.findViewById(R.id.fl_edit_course_score);
        mScoreView = rootView.findViewById(R.id.tv_edit_course_score);
        mItemView.setOnClickListener(this);
        mEditInfoView = rootView.findViewById(R.id.edit_score_view);
        mEditInfoView.setTitle("课程学分");
        mEditInfoView.setHideListener(new EditInfoView.HideListener() {
            @Override
            public void onHide(String content) {
                mScoreView.setText(content);
            }
        });
    }

    @Override
    public void onClick(View view) {
        mEditInfoView.setVisibility(View.VISIBLE);
    }


    public String getContent() {
        return mScoreView.getText().toString();
    }
}
