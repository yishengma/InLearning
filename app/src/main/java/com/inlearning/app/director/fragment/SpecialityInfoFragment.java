package com.inlearning.app.director.fragment;

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
import com.inlearning.app.director.adapter.SpecialityInfoAdapter;
import java.util.ArrayList;
import java.util.List;


public class SpecialityInfoFragment extends BaseFragment {
    private RecyclerView mRvSpecialityClass;
    private List<ClassInfo> mClassList;
    private SpecialityInfoAdapter mSpecialityInfoAdapter;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_speciality_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvSpecialityClass = view.findViewById(R.id.rv_content);
        mRvSpecialityClass.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mClassList = new ArrayList<>();
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_SEPARATE));
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_CLASS_INFO));
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_CLASS_INFO));
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_CLASS_INFO));
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_CLASS_INFO));
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_CLASS_INFO));
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_SEPARATE));
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_CLASS_INFO));
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_CLASS_INFO));
        mClassList.add(new ClassInfo().setType(SpecialityInfoAdapter.ITEM_CLASS_INFO));
        mSpecialityInfoAdapter = new SpecialityInfoAdapter(mClassList);
        mRvSpecialityClass.setAdapter(mSpecialityInfoAdapter);
    }
}
