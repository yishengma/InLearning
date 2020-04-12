package com.inlearning.app.director.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.FeedbackActivity;
import com.inlearning.app.common.model.UserModel;
import com.inlearning.app.director.DirectorAppRuntime;
import com.inlearning.app.director.person.coursemanager.CourseManagerActivity;
import com.inlearning.app.director.person.specialitymanager.SpecialityManagerActivity;
import com.inlearning.app.student.StudentRuntime;
import com.inlearning.app.teacher.TeacherRuntime;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView mProfilePhotoView;
    private TextView mNameView;
    private TextView mInfoView;
    private RelativeLayout mSpecialityManagerView;
    private RelativeLayout mCourseManagerView;
    private RelativeLayout mPersonInfoView;
    private RelativeLayout mFeedbackView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_person, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mNameView.setText(DirectorAppRuntime.getsDirector().mName);
        mInfoView.setText("管理员");
        if (TextUtils.isEmpty(DirectorAppRuntime.getsDirector().getProfilePhotoUrl())) {
            return;
        }
        Glide.with(this).load(DirectorAppRuntime.getsDirector().getProfilePhotoUrl()).into(mProfilePhotoView);
    }

    private void initView(View view) {
        mProfilePhotoView = view.findViewById(R.id.cv_profile_photo);
        mNameView = view.findViewById(R.id.tv_name);
        mInfoView = view.findViewById(R.id.tv_info);
        mSpecialityManagerView = view.findViewById(R.id.rl_speciality_manager);
        mCourseManagerView = view.findViewById(R.id.rl_course_manager);
//        Glide.with(this).load(UserModel.getDirector().getProfilePhotoUrl()).into(mProfilePhotoView);
        mNameView.setText(DirectorAppRuntime.getsDirector().getName());
        mInfoView.setText("管理员");
        mSpecialityManagerView.setOnClickListener(this);
        mCourseManagerView.setOnClickListener(this);
        mPersonInfoView = view.findViewById(R.id.view_person_info);
        mPersonInfoView.setOnClickListener(this);

        mFeedbackView = view.findViewById(R.id.view_feed_back);
        mFeedbackView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_speciality_manager:
                SpecialityManagerActivity.startActivity(getContext());
                break;
            case R.id.rl_course_manager:
                CourseManagerActivity.startActivity(getContext());
                break;
            case R.id.view_person_info:
                PersonActivity.startActivity(getContext(), DirectorAppRuntime.getsDirector());
                break;
            case R.id.view_feed_back:
                FeedbackActivity.startFeedbackActivity(getContext());
                break;
        }
    }
}
