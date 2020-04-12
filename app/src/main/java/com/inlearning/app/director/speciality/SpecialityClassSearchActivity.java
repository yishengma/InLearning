package com.inlearning.app.director.speciality;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.director.BaseSearchActivity;
import com.inlearning.app.director.DirectorAppRuntime;

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
        for (Speciality s : DirectorAppRuntime.getSpecialities()) {
            mTotalClassList.addAll(s.getClassInfoList());
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return mInfoAdapter;
    }

    @Override
    protected void doSearch(String key) {
        mClassList.clear();
        for (ClassInfo classInfo: mTotalClassList) {
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
