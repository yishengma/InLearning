package com.yishengma.inlearning.launch;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jaeger.library.StatusBarUtil;
import com.yishengma.inlearning.R;
import com.yishengma.inlearning.home.HomePageActivity;
import com.yishengma.inlearning.util.StatusBar;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this,true);
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
