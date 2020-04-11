package com.inlearning.app.director.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Director;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.widget.PersonInfoView;

public class PersonActivity extends AppCompatActivity {

    public static void startActivity(Context context, Director director) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra("director", director);
        context.startActivity(intent);
    }

    private LinearLayout mRootView;
    private PersonInfoView mImageView;
    private PersonInfoView mNameView;
    private PersonInfoView mIdentifyView;
    private Director mDirector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        getIntentData();
        initView();
    }

    private void getIntentData() {
        mDirector = (Director) getIntent().getSerializableExtra("director");
    }

    private void initView() {

        mRootView = findViewById(R.id.root_view);
        mImageView = new PersonInfoView(this);
        mImageView.setTitleText("头像");
        mImageView.setPersonImageView(mDirector.getProfilePhotoUrl());
        mIdentifyView = new PersonInfoView(this);
        mIdentifyView.setTitleText("身份");
        mIdentifyView.setPersonContentView("管理员");
        mNameView = new PersonInfoView(this);
        mNameView.setTitleText("姓名");
        mNameView.setPersonContentView(mDirector.getName());

        mRootView.addView(mIdentifyView);
        addDivideView();
        mRootView.addView(mNameView);
        addDivideView();
    }

    public void addDivideView() {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.split_line));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);

        mRootView.addView(view, params);
    }
}
