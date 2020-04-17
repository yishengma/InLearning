package com.inlearning.app.director.person.coursemanager.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.inlearning.app.BaseActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.BaseActivity;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.DirectorAppRuntime;
import com.inlearning.app.director.speciality.SpecialityModel;

import java.util.List;

public class ClassListActivity extends BaseActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ClassListActivity.class);
        context.startActivity(intent);
    }

    private View mParentView;
    private ClassListPresenter mClassListPresenter;
    private ImageView mBackView;
    private TextView mEmptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list2);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        mParentView = findViewById(R.id.root_view);
        mBackView = findViewById(R.id.imv_bar_back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEmptyView = findViewById(R.id.tv_empty);
        initPresenter();
        initData();
    }


    private void initPresenter() {
        mClassListPresenter = new ClassListPresenter(getSupportFragmentManager(), mParentView);
    }


    private void initData() {
        SpecialityModel.getSpeciality(new SpecialityModel.Callback<List<Speciality>>() {
            @Override
            public void onResult(boolean suc, List<Speciality> specialities) {
                if (suc && specialities != null) {
                    initSpecialityAndView(specialities);
                }
            }
        });
    }

    private void initSpecialityAndView(final List<Speciality> specialities) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                for (Speciality s : specialities) {
                    addSpeciality(true, s);
                }
                mEmptyView.setVisibility(specialities.isEmpty()?View.VISIBLE:View.GONE);
                DirectorAppRuntime.setSpecialities(specialities);
            }
        });
    }


    private void addSpeciality(boolean suc, Speciality speciality) {
        if (suc) {
            mClassListPresenter.addSpeciality(speciality);
        }
    }

}
