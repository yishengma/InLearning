package com.inlearning.app.teacher.person;

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
import com.inlearning.app.common.bean.Director;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.director.DirectorAppRuntime;
import com.inlearning.app.teacher.TeacherRuntime;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView mProfilePhotoView;
    private TextView mNameView;
    private TextView mInfoView;
    private RelativeLayout mInfoLayoutView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_person, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mNameView.setText(TeacherRuntime.getCurrentTeacher().getName());
        mInfoView.setText(TeacherRuntime.getCurrentTeacher().getTitle());
        if (TextUtils.isEmpty(TeacherRuntime.getCurrentTeacher().getProfilePhotoUrl())) {
            return;
        }
        Glide.with(this).load(TeacherRuntime.getCurrentTeacher().getProfilePhotoUrl()).into(mProfilePhotoView);

    }

    private void initView(View view) {
        mProfilePhotoView = view.findViewById(R.id.cv_profile_photo);
        mNameView = view.findViewById(R.id.tv_name);
        mInfoView = view.findViewById(R.id.tv_info);
        mInfoLayoutView = view.findViewById(R.id.view_person_info);
        mInfoLayoutView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_person_info:
                PersonActivity.startActivity(getContext(), TeacherRuntime.getCurrentTeacher());
                break;
        }
    }
}
