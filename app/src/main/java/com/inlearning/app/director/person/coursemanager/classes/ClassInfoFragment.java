package com.inlearning.app.director.person.coursemanager.classes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.director.speciality.SpecialityInfoAdapter;

import java.util.ArrayList;
import java.util.List;


public class ClassInfoFragment extends BaseFragment {
    private RecyclerView mRvSpecialityClass;
    private List<ClassInfo> mClassList = new ArrayList<>();
    private SpecialityInfoAdapter mSpecialityInfoAdapter;
    private Speciality mSpeciality;
    private List<Speciality> mSpecialities = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_speciality_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvSpecialityClass = view.findViewById(R.id.rv_content);
        mRvSpecialityClass.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mSpecialityInfoAdapter = new SpecialityInfoAdapter(mClassList);
        mRvSpecialityClass.setAdapter(mSpecialityInfoAdapter);
        mSpecialityInfoAdapter.setClickListener(new SpecialityInfoAdapter.ClickListener() {
            @Override
            public void onClick(ClassInfo classInfo) {
                ClassCourseActivity.startActivity(getContext(), classInfo);
            }

            @Override
            public void onLongClick(View view, float x, float y, ClassInfo classInfo) {

            }
        });
    }

    public ClassInfoFragment setSpeciality(Speciality speciality) {
        mSpeciality = speciality;
        mClassList.clear();
        for (ClassInfo classInfo : speciality.getClassInfoList()) {
            mClassList.add(classInfo.setType(ClassInfo.ITEM_CLASS_INFO).setSpeciality(speciality));
        }
        if (mSpecialityInfoAdapter != null) {
            mSpecialityInfoAdapter.notifyDataSetChanged();
        }
        return this;
    }
}
