package com.inlearning.app.director.speciality;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.director.BaseSearchActivity;

import java.util.ArrayList;
import java.util.List;

public class SpecialityClassSearchActivity extends BaseSearchActivity {

    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, SpecialityClassSearchActivity.class);
        context.startActivity(intent);
    }

    private List<ClassInfo> mClassList;
    private SpecialityInfoAdapter mInfoAdapter;

    @Override
    protected void initAdapter() {
        mClassList = new ArrayList<>();
        mInfoAdapter = new SpecialityInfoAdapter(mClassList);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return mInfoAdapter;
    }

    @Override
    protected void doSearch(String key) {

    }

    @Override
    protected void resetList() {
        mClassList.clear();
        mInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected String editHint() {
        return "搜索班级";
    }
}
