package com.inlearning.app.director.speciality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.DirectorAppRuntime;

import java.util.List;

public class SpecialityFragment extends BaseFragment implements View.OnClickListener{
//    private ImageView mSearchView;
//    private ImageView mAddView;
    private View mParentView;
    private SpecialityPresenter mSpecialityPresenter;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_director_speciality, container, false);
        initView();
        initPresenter();
        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }).start();
        return mParentView;
    }

    private void initView() {
        if (null == getActivity()) {
            return;
        }
//        mSearchView = mParentView.findViewById(R.id.imv_search);
//        mSearchView.setOnClickListener(this);
//        mAddView = mParentView.findViewById(R.id.imv_add);
//        mAddView.setOnClickListener(this);
    }

    private void initPresenter() {
        mSpecialityPresenter = new SpecialityPresenter(getChildFragmentManager(), mParentView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_search:
                break;
//            case R.id.imv_add:
//                showDialog();
//                break;
            default:
                break;
        }
    }

    private void initData() {
        Log.e("initData", "initData: ");
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
            mSpecialityPresenter.addSpeciality(speciality);
        }
    }



}
