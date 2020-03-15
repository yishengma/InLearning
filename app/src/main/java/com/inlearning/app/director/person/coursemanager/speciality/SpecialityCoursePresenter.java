package com.inlearning.app.director.person.coursemanager.speciality;

import android.app.Activity;
import android.view.View;

import com.inlearning.app.R;

public class SpecialityCoursePresenter {

    private View mRootView;
    private View mManagerChooseView;
    private SpecialityCourseView mSpecialityView;

    public SpecialityCoursePresenter(Activity activity) {
        mRootView = activity.findViewById(R.id.root_view);
        mManagerChooseView = activity.findViewById(R.id.view_manager_choose);
        mSpecialityView = activity.findViewById(R.id.view_speciality_course);
        mSpecialityView.setClickListener(new SpecialityCourseView.ClickListener() {
            @Override
            public void onBack() {
                mSpecialityView.hide();
                mManagerChooseView.setVisibility(View.VISIBLE);
            }
        });
    }


    public void showView() {
        mSpecialityView.show();
    }

}
