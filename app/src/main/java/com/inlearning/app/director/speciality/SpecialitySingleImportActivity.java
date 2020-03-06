package com.inlearning.app.director.speciality;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.widget.EditItemView;
import com.inlearning.app.director.BaseSingleImportActivity;

import static android.view.Gravity.CENTER;

public class SpecialitySingleImportActivity extends BaseSingleImportActivity implements TextWatcher {
    public static void startEditActivity(Context context) {
        Intent intent = new Intent(context, SpecialitySingleImportActivity.class);
        context.startActivity(intent);
    }

    private EditItemView mNameEditView;
    private EditItemView mShortEditView;
    private EditItemView mCountEditView;
    private TextView mSaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initSaveButton();
    }

    private void initView() {
        mTitleView.setText("新增专业");
        mNameEditView = new EditItemView(this);
        mNameEditView.setHint("专业名称");
        mShortEditView = new EditItemView(this);
        mShortEditView.setHint("专业简称（2-3个字符）");
        mCountEditView = new EditItemView(this);
        mCountEditView.setHint("班级数目");
        mCountEditView.setInputType(InputType.TYPE_CLASS_NUMBER);
        mCountEditView.setTextWatcher(this);
        mShortEditView.setTextWatcher(this);
        mNameEditView.setTextWatcher(this);
        mRootView.addView(mNameEditView);
        mRootView.addView(mShortEditView);
        mRootView.addView(mCountEditView);
    }

    private void initSaveButton() {
        mSaveView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = PixeUtil.dp2px(24);
        params.rightMargin = PixeUtil.dp2px(24);
        params.topMargin = PixeUtil.dp2px(24);
        mSaveView.setBackgroundResource(R.drawable.bg_edit_gray_shape);
        mSaveView.setText("保存");
        mSaveView.setEnabled(false);
        mSaveView.setTextColor(Color.parseColor("#61000000"));
        mSaveView.setGravity(CENTER);
        mSaveView.setTextSize(PixeUtil.dp2sp(16));
        mSaveView.setLayoutParams(params);
        mRootView.addView(mSaveView);
        mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSpeciality();
            }
        });
    }

    @Override
    protected void doBack() {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        changeSaveViewStatus();
    }

    private void changeSaveViewStatus() {
        if (!TextUtils.isEmpty(mCountEditView.getContent())
                && !TextUtils.isEmpty(mShortEditView.getContent())
                && !TextUtils.isEmpty(mNameEditView.getContent())) {
            mSaveView.setTextColor(Color.WHITE);
            mSaveView.setBackgroundResource(R.drawable.bg_edit_blue_shape);
            mSaveView.setEnabled(true);
        } else {
            mSaveView.setBackgroundResource(R.drawable.bg_edit_gray_shape);
            mSaveView.setEnabled(false);
            mSaveView.setTextColor(Color.parseColor("#61000000"));
        }
    }

    private void addSpeciality() {
        Speciality speciality = new Speciality();
        speciality.setName(mNameEditView.getContent())
                .setShortName(mShortEditView.getContent())
                .setClassCount(Integer.valueOf(mCountEditView.getContent()));
        SpecialityModel.addSpeciality(speciality, new SpecialityModel.Callback<Speciality>() {
            @Override
            public void onResult(boolean suc, Speciality speciality) {
                if (suc) {
                    showToast();
                    finish();
                }
            }
        });
    }

    private void showToast() {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SpecialitySingleImportActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
