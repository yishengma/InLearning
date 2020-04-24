package com.inlearning.app.director.speciality;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.director.BaseSearchActivity;
import com.inlearning.app.director.DirectorAppRuntime;
import com.inlearning.app.director.speciality.classinfo.ClassInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class SpecialityClassSearchActivity extends BaseSearchActivity {

    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, SpecialityClassSearchActivity.class);
        context.startActivity(intent);
    }

    private List<ClassInfo> mClassList;
    private SpecialityInfoAdapter mInfoAdapter;
    private List<ClassInfo> mTotalClassList;

    @Override
    protected void initAdapter() {
        mTotalClassList = new ArrayList<>();
        mClassList = new ArrayList<>();
        mInfoAdapter = new SpecialityInfoAdapter(mClassList);
        mTotalClassList.addAll(DirectorAppRuntime.getsClassInfo());
        mInfoAdapter.setClickListener(new SpecialityInfoAdapter.ClickListener() {
            @Override
            public void onClick(ClassInfo classInfo) {
                ClassInfoActivity.startActivity(SpecialityClassSearchActivity.this, classInfo);
            }

            @Override
            public void onLongClick(View view, float x, float y, ClassInfo classInfo) {

            }
        });

    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return mInfoAdapter;
    }

    @Override
    protected void doSearch(String key) {
        mClassList.clear();
        for (ClassInfo classInfo : mTotalClassList) {
            if (classInfo.getName().contains(key)) {
                mClassList.add(classInfo);
            }
        }
        mInfoAdapter.notifyDataSetChanged();
        setEmptyView(mClassList);
    }

    @Override
    protected void resetList() {
        mClassList.clear();
        mInfoAdapter.notifyDataSetChanged();
        setEmptyView(mClassList);
    }

    @Override
    protected String editHint() {
        return "搜索班级";
    }
}
