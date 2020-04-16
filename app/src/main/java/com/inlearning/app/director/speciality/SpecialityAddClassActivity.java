package com.inlearning.app.director.speciality;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.LoadingDialog;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.util.ToastUtil;
import com.inlearning.app.common.widget.EditItemView;
import com.inlearning.app.director.BaseSingleImportActivity;
import com.inlearning.app.director.DirectorAppRuntime;

import static android.view.Gravity.CENTER;

public class SpecialityAddClassActivity extends BaseSingleImportActivity implements TextWatcher {

    public static void startSpecialityAddClassActivity(Context context) {
        Intent intent = new Intent(context, SpecialityAddClassActivity.class);
        context.startActivity(intent);
    }

    private EditItemView mSpecialityView;
    private EditItemView mCountEditView;
    private TextView mSaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initSaveButton();
    }

    private void initView() {
        mTitleView.setText("新增班级");
        mSpecialityView = new EditItemView(this);
        mSpecialityView.setHint("专业名称");
        mCountEditView = new EditItemView(this);
        mCountEditView.setHint("班级数目");
        mCountEditView.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        mCountEditView.setTextWatcher(this);
        mSpecialityView.setTextWatcher(this);
        mRootView.addView(mSpecialityView);
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
                && !TextUtils.isEmpty(mCountEditView.getContent())
        ) {
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
        Speciality speciality = null;
        for (Speciality s : DirectorAppRuntime.getSpecialities()) {
            if (s.getName().equals(mSpecialityView.getContent())) {
                speciality = s;
                break;
            }
        }
        if (speciality == null) {
            ToastUtil.showToast("该专业不存在，请先新增专业", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(mCountEditView.getContent())) {
            ToastUtil.showToast("输入合法的数目", Toast.LENGTH_SHORT);
            return;
        }
        int count = Integer.valueOf(mCountEditView.getContent());
        if (count <= 0) {
            ToastUtil.showToast("输入合法的数目", Toast.LENGTH_SHORT);
            return;
        }
        LoadingDialog.showLoadingDialog(SpecialityAddClassActivity.this, "正在添加..");
        SpecialityModel.addClasses(speciality, count, new SpecialityModel.Callback<Speciality>() {
            @Override
            public void onResult(boolean suc, Speciality speciality) {
                LoadingDialog.closeDialog();
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
                Toast.makeText(SpecialityAddClassActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
