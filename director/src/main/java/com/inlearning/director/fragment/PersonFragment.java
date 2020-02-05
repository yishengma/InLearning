package com.inlearning.director.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.common.base.BaseFragment;
import com.inlearning.common.model.UserModel;
import com.inlearning.director.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends BaseFragment {
    private CircleImageView mProfilePhotoView;
    private TextView mNameView;
    private TextView mInfoView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mProfilePhotoView = view.findViewById(R.id.cv_profile_photo);
        mNameView = view.findViewById(R.id.tv_name);
        mInfoView = view.findViewById(R.id.tv_info);
        Glide.with(this).load(UserModel.getDirector().getProfilePhotoUrl()).into(mProfilePhotoView);
        mNameView.setText(UserModel.getDirector().getName());
        mInfoView.setText("管理员");
    }
}
