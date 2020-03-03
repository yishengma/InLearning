package com.inlearning.app.director.course.edit;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.inlearning.app.R;

import java.io.File;

public class IconItemView implements View.OnClickListener{

    private FrameLayout mItemView;
    private ImageView mIconImageView;
    private View mRootView;
    private EditInfoView mEditInfoView;
    private File mIconFile;
    public IconItemView(View rootView) {
        mRootView = rootView;
        mItemView = rootView.findViewById(R.id.fl_edit_course_icon);
        mIconImageView = rootView.findViewById(R.id.imv_edit_course_icon);
        mItemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
