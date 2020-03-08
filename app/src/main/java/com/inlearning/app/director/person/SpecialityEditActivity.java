package com.inlearning.app.director.person;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.PixeUtil;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.widget.EditItemView;
import com.inlearning.app.director.speciality.SpecialityModel;

import static android.view.Gravity.CENTER;

public class SpecialityEditActivity extends AppCompatActivity {
    public static void startActivity(Context context, Speciality speciality) {
        Intent intent = new Intent(context, SpecialityEditActivity.class);
        intent.putExtra("speciality", speciality);
        context.startActivity(intent);
    }

    protected ViewGroup mRootView;
    protected ImageView mBackView;
    protected TextView mTitleView;
    private EditItemView mSpecialityView;
    private EditItemView mShortNameView;
    private TextView mSaveView;
    private TextView mDeleteView;
    private Speciality mSpeciality;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speciality_edit);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initView();
        initSaveButton();
    }


    private void getIntentData() {
        Intent intent = getIntent();
        mSpeciality = (Speciality) intent.getSerializableExtra("speciality");
    }

    private void initView() {
        mSpecialityView = new EditItemView(this);
        mSpecialityView.setHint("专业名称");
        mSpecialityView.setText(mSpeciality.getName());
        mShortNameView = new EditItemView(this);
        mShortNameView.setHint("专业简称");
        mShortNameView.setText(mSpeciality.getShortName());
        mRootView = findViewById(R.id.root_view);
        mBackView = findViewById(R.id.imv_manager_back);
        mTitleView = findViewById(R.id.tv_manager_title);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDeleteView = findViewById(R.id.tv_manager_delete);
        mDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        mRootView.addView(mSpecialityView);
        mRootView.addView(mShortNameView);
    }

    private void initSaveButton() {
        mSaveView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = PixeUtil.dp2px(24);
        params.rightMargin = PixeUtil.dp2px(24);
        params.topMargin = PixeUtil.dp2px(24);
        mSaveView.setBackgroundResource(R.drawable.bg_edit_blue_shape);
        mSaveView.setText("更改并保存");
        mSaveView.setEnabled(true);
        mSaveView.setTextColor(Color.WHITE);
        mSaveView.setGravity(CENTER);
        mSaveView.setTextSize(PixeUtil.dp2sp(16));
        mSaveView.setLayoutParams(params);
        mRootView.addView(mSaveView);
        mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpeciality.setName(mSpecialityView.getContent())
                        .setShortName(mShortNameView.getContent());
                SpecialityModel.updateSpeciality(mSpeciality, new SpecialityModel.Callback<Speciality>() {
                    @Override
                    public void onResult(boolean suc, Speciality speciality) {
                        showToast("更新成功");
                        finish();
                    }
                });
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_excel_import);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该专业信息？删除之后不可恢复！");
        TextView cancelView = dialog.findViewById(R.id.tv_cancel);
        TextView confirmView = dialog.findViewById(R.id.tv_confirm);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSpeciality();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void deleteSpeciality() {
        SpecialityModel.deleteSpeciality(mSpeciality, new SpecialityModel.Callback<Speciality>() {
            @Override
            public void onResult(boolean suc, Speciality speciality) {
                if (suc) {
                    showToast("删除成功");
                    finish();
                }
            }
        });
    }

    private void showToast(final String msg) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SpecialityEditActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
