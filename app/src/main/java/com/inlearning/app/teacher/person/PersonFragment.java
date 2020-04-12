package com.inlearning.app.teacher.person;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.LoginActivity;
import com.inlearning.app.R;
import com.inlearning.app.common.AboutAppActivity;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.FeedbackActivity;
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
    private RelativeLayout mFeedbackView;
    private RelativeLayout mAboutAppView;
    private RelativeLayout mLoginOutView;

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
        mFeedbackView = view.findViewById(R.id.view_feed_back);
        mFeedbackView.setOnClickListener(this);
        mAboutAppView = view.findViewById(R.id.view_about_app);
        mAboutAppView.setOnClickListener(this);
        mLoginOutView = view.findViewById(R.id.view_login_out);
        mLoginOutView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_person_info:
                PersonActivity.startActivity(getContext(), TeacherRuntime.getCurrentTeacher());
                break;
            case R.id.view_feed_back:
                FeedbackActivity.startFeedbackActivity(getContext());
                break;
            case R.id.view_about_app:
                AboutAppActivity.startAboutActivity(getContext());
                break;
            case R.id.view_login_out:
                showLoginOutDialog();
                break;
        }
    }

    private void showLoginOutDialog() {
        final Dialog dialog = new Dialog(getContext(), R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_login_out);
        TextView cancelView = dialog.findViewById(R.id.tv_cancel);
        TextView confirmView = dialog.findViewById(R.id.tv_confirm);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                LoginActivity.startLoginActivity(getContext());
                getActivity().finish();
            }
        });
        dialog.show();
    }
}
