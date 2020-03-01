package com.inlearning.app.director.course.edit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inlearning.app.R;

public class EditTypeView extends LinearLayout implements View.OnClickListener {

    public EditTypeView(Context context) {
        this(context, null);
    }

    public EditTypeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private ImageView mBackView;
    private TextView mSpecialityView;
    private TextView mChooseView;


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_edit_course_type, this);
        mBackView = view.findViewById(R.id.imv_edit_course_back);
        mBackView.setOnClickListener(this);
        mSpecialityView = view.findViewById(R.id.tv_course_type_speciality);
        mChooseView = view.findViewById(R.id.tv_course_type_choose);
        mSpecialityView.setOnClickListener(this);
        mChooseView.setOnClickListener(this);
    }

    public interface HideListener {
        void onHide(String content);
    }

    private HideListener mHideListener;

    public EditTypeView setHideListener(HideListener hideListener) {
        mHideListener = hideListener;
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_course_type_speciality:
                if (mHideListener != null) {
                    mHideListener.onHide(mSpecialityView.getText().toString());
                }
                setVisibility(GONE);
                break;
            case R.id.tv_course_type_choose:
                if (mHideListener != null) {
                    mHideListener.onHide(mChooseView.getText().toString());
                }
                setVisibility(GONE);
                break;
        }
    }

}
