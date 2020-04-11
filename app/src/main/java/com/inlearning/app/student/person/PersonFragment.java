package com.inlearning.app.student.person;

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
import com.inlearning.app.common.model.UserModel;
import com.inlearning.app.student.StudentRuntime;
import com.inlearning.app.teacher.TeacherRuntime;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView mProfilePhotoView;
    private TextView mNameView;
    private TextView mInfoView;
    private RelativeLayout mMineDiscussView;
    private RelativeLayout mPersonInfoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_person, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mNameView.setText(StudentRuntime.getStudent().getName());
        mInfoView.setText(StudentRuntime.getStudent().getClassInfo().getName());
        if (TextUtils.isEmpty(StudentRuntime.getStudent().getProfilePhotoUrl())) {
            return;
        }
        Glide.with(this).load(StudentRuntime.getStudent().getProfilePhotoUrl()).into(mProfilePhotoView);
    }

    private void initView(View view) {
        mProfilePhotoView = view.findViewById(R.id.cv_profile_photo);
        mNameView = view.findViewById(R.id.tv_name);
        mInfoView = view.findViewById(R.id.tv_info);
//        Glide.with(this).load(UserModel.getDirector().getProfilePhotoUrl()).into(mProfilePhotoView);
//        mNameView.setText(UserModel.getDirector().getName());
        mInfoView.setText(UserModel.getStudent().getClassInfo().getName());
        mMineDiscussView = view.findViewById(R.id.view_mine_discuss);
        mMineDiscussView.setOnClickListener(this);

        mPersonInfoView = view.findViewById(R.id.view_person_info);
        mPersonInfoView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_mine_discuss:
                MineDiscussActivity.startMineDiscussActivity(getContext());
                break;
            case R.id.view_person_info:
                PersonActivity.startActivity(getContext(), StudentRuntime.getStudent());
                break;
        }
    }
}
