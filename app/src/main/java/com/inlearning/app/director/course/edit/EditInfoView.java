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
import com.inlearning.app.common.widget.OneKeyClearEditText;

public class EditInfoView extends LinearLayout implements View.OnClickListener {

    public EditInfoView(Context context) {
        this(context, null);
    }

    public EditInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private ImageView mBackView;
    private TextView mTitleView;
    private TextView mConfirmView;
    private OneKeyClearEditText mInputView;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_edit_course_info, this);
        mBackView = view.findViewById(R.id.imv_edit_course_back);
        mTitleView = view.findViewById(R.id.tv_edit_course_title);
        mConfirmView = view.findViewById(R.id.tv_edit_course_confirm);
        mInputView = view.findViewById(R.id.et_edit_course_content);
        mBackView.setOnClickListener(this);
        mConfirmView.setOnClickListener(this);
    }

    public interface HideListener {
        void onHide(String content);
    }

    private HideListener mHideListener;

    public EditInfoView setHideListener(HideListener hideListener) {
        mHideListener = hideListener;
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_edit_course_back:
                setVisibility(GONE);
                break;
            case R.id.tv_edit_course_confirm:
                if (mHideListener != null) {
                    mHideListener.onHide(mInputView.getText().toString());
                }
                setVisibility(GONE);
                break;
        }
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }
}
