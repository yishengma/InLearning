package com.inlearning.app.director.speciality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.DirectorAppRuntime;

import java.util.List;

public class SpecialityFragment extends BaseFragment implements View.OnClickListener {
    //    private ImageView mSearchView;
//    private ImageView mAddView;
    private View mParentView;
    private SpecialityPresenter mSpecialityPresenter;
    private TextView mEmptyView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_director_speciality, container, false);
        initView(mParentView);
        initPresenter();
        return mParentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView(View view) {
        if (null == getActivity()) {
            return;
        }
        mEmptyView = view.findViewById(R.id.tv_empty);
    }

    private void initPresenter() {
        mSpecialityPresenter = new SpecialityPresenter(getChildFragmentManager(), mParentView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_search:
                break;
            default:
                break;
        }
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
                mSpecialityPresenter.reset();
                for (Speciality s : specialities) {
                    addSpeciality(true, s);
                }
                if (specialities != null && !specialities.isEmpty()) {
                    mEmptyView.setVisibility(View.GONE);
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
