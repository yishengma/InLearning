package com.inlearning.app.director.person.coursemanager.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.DirectorAppRuntime;
import com.inlearning.app.director.speciality.SpecialityModel;

import java.util.List;

public class ClassListActivity extends AppCompatActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ClassListActivity.class);
        context.startActivity(intent);
    }

    private View mParentView;
    private ClassListPresenter mClassListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list2);
        mParentView = findViewById(R.id.root_view);
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