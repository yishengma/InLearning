package com.inlearning.app.director.person.specialitymanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.LoadingDialogUtil;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.DirectorAppRuntime;
import com.inlearning.app.director.speciality.SpecialityModel;

import java.util.ArrayList;
import java.util.List;

public class SpecialityManagerActivity extends AppCompatActivity implements View.OnClickListener {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SpecialityManagerActivity.class);
        context.startActivity(intent);
    }


    private ImageView mBackView;
    private RecyclerView mRvSpecialityView;
    private List<Speciality> mSpecialities = new ArrayList<>();
    private SpecialityManagerAdapter mManagerAdapter;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speciality_manager);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        initView();
        initData();
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_bar_back);
        mRvSpecialityView = findViewById(R.id.rv_speciality);
        mRvSpecialityView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBackView.setOnClickListener(this);
        mManagerAdapter = new SpecialityManagerAdapter(mSpecialities);
        mRvSpecialityView.setAdapter(mManagerAdapter);
        mManagerAdapter.setClickListener(new SpecialityManagerAdapter.ClickListener() {
            @Override
            public void onClick(Speciality speciality) {
                SpecialityEditActivity.startActivity(SpecialityManagerActivity.this,speciality);
            }
        });
        mEmptyView = findViewById(R.id.tv_empty);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_bar_back:
                finish();
                break;
        }
    }

    private void initData() {
        LoadingDialogUtil.showLoadingDialog(this,"加载数据中...");
        SpecialityModel.getSpeciality(new SpecialityModel.Callback<List<Speciality>>() {
            @Override
            public void onResult(boolean suc, List<Speciality> specialities) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialogUtil.closeDialog();
                        mSpecialities.clear();
                        mSpecialities.addAll(specialities);
                        mManagerAdapter.notifyDataSetChanged();
                        mEmptyView.setVisibility(mSpecialities.isEmpty()?View.VISIBLE:View.GONE);
                    }
                });
            }
        });
    }
}
