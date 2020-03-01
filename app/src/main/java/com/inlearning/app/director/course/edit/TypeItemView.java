package com.inlearning.app.director.course.edit;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.inlearning.app.R;

public class TypeItemView implements View.OnClickListener {
    private FrameLayout mItemView;
    private TextView mTypeView;
    private View mRootView;
    private EditTypeView mEditTypeView;

    public TypeItemView(View rootView) {
        mRootView = rootView;
        mItemView = rootView.findViewById(R.id.fl_edit_course_type);
        mTypeView = rootView.findViewById(R.id.tv_edit_course_type);
        mItemView.setOnClickListener(this);

        mEditTypeView = rootView.findViewById(R.id.edit_type_view);
        mEditTypeView.setHideListener(new EditTypeView.HideListener() {
            @Override
            public void onHide(String content) {
                mTypeView.setText(content);
            }
        });
    }

    @Override
    public void onClick(View view) {
        mEditTypeView.setVisibility(View.VISIBLE);
    }


    public String getContent() {
        return mTypeView.getText().toString();
    }
}
