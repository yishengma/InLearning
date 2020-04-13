package com.inlearning.app.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.inlearning.app.R;

public class EditItemView extends LinearLayout {

    public EditItemView(Context context) {
        this(context, null);
    }

    public EditItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private TextInputLayout mTextInputLayout;
    private TextInputEditText mTextInputEditText;
    private View mDivideView;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_edit_item, this);
        mTextInputLayout = view.findViewById(R.id.input_layout);
        mTextInputEditText = view.findViewById(R.id.et_input_content);
        mDivideView = view.findViewById(R.id.line_divide);
    }

    public String getContent() {
        return mTextInputEditText.getText().toString();
    }

    public EditItemView setHint(String hint) {
        mTextInputLayout.setHint(hint);
        return this;
    }

    public EditItemView setInputType(int type) {
        mTextInputEditText.setInputType(type);
        return this;
    }

    public void setText(String msg) {
        mTextInputEditText.setText(msg);
    }


    public EditItemView setDivideGone() {
        mDivideView.setVisibility(GONE);
        return this;
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        mTextInputEditText.addTextChangedListener(textWatcher);
    }

    public void setEnableEdit(boolean enableEdit) {
        mTextInputEditText.setEnabled(enableEdit);
    }
}
