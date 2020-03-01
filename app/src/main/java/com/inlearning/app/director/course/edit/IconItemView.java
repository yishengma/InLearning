package com.inlearning.app.director.course.edit;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.inlearning.app.R;

public class IconItemView implements View.OnClickListener{

    private FrameLayout mItemView;
    private ImageView mIconImageView;
    private View mRootView;
    private EditInfoView mEditInfoView;
    public IconItemView(View rootView) {
        mRootView = rootView;
        mItemView = rootView.findViewById(R.id.fl_edit_course_icon);
        mIconImageView = rootView.findViewById(R.id.imv_edit_course_icon);
//        mEditInfoView = rootView.findViewById(R.id.edit_info_view);
        mItemView.setOnClickListener(this);
//        mEditInfoView.setTitle("图标");
    }

    @Override
    public void onClick(View view) {

    }
}
