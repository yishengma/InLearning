package com.inlearning.app.student.course.func.material;

import android.app.Activity;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Materials;
import com.inlearning.app.common.util.ThreadMgr;


import java.util.ArrayList;
import java.util.List;

public class MaterialPresenter {

    private MaterialView mMaterialView;
    private MaterialTbsView mMaterialTbsView;
    private MaterialAdapter mMaterialAdapter;
    private Activity mActivity;
    private List<Materials> mMaterials;
    private CourseChapter mChapter;

    public MaterialPresenter(Activity activity, CourseChapter chapter) {
        mActivity = activity;
        mChapter = chapter;
        mMaterials = new ArrayList<>();
        mMaterialView = mActivity.findViewById(R.id.view_material_func);
        mMaterialTbsView = mActivity.findViewById(R.id.view_material_tbs_view);
        mMaterialAdapter = new MaterialAdapter(mMaterials);
        mMaterialAdapter.setClickListener(new MaterialAdapter.ClickListener() {
            @Override
            public void onClick(Materials materials) {
                mMaterialView.setVisibility(View.GONE);
                mMaterialTbsView.setVisibility(View.VISIBLE);
                mMaterialTbsView.openFile(materials.getMaterialName(),materials.getMaterialFile().getFileUrl());
            }
        });
        mMaterialTbsView.setClickListener(new MaterialTbsView.ClickListener() {
            @Override
            public void onBack() {
                mMaterialView.setVisibility(View.VISIBLE);
                mMaterialTbsView.setVisibility(View.GONE);

            }
        });
        mMaterialView.setClickListener(new MaterialView.ClickListener() {
            @Override
            public void onBack() {
                mActivity.finish();
            }
        });
        mMaterialView.getMaterialView().setAdapter(mMaterialAdapter);
        mMaterialView.refreshMaterialView(false);
        initData();
    }

    private void initData() {
        MaterialModel.getMaterialByChapter(mChapter, new MaterialModel.Callback<List<Materials>>() {
            @Override
            public void onResult(final List<Materials> materials) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mMaterials.addAll(materials);
                        mMaterialAdapter.notifyDataSetChanged();
                        mMaterialView.refreshMaterialView(materials.size() != 0);
                    }
                });
            }
        });
    }

    public void showMaterialView() {
        mMaterialView.setVisibility(View.VISIBLE);
    }

    public void onDestory() {
        mMaterialTbsView.onDestroy();
    }
}
