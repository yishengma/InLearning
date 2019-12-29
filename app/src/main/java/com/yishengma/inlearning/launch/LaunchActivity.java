package com.yishengma.inlearning.launch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.home.HomePageActivity;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initView();
    }

    private void initView() {
        mLoginView = findViewById(R.id.btn_login);
        mLoginView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                HomePageActivity.startHomePageActivity(LaunchActivity.this);
                break;
        }
    }
}
