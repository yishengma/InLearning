package com.inlearning.app.common;

import android.content.Context;
import android.content.Intent;
import com.inlearning.app.BaseActivity;
import android.os.Bundle;
import android.view.View;

import com.inlearning.app.BaseActivity;
import com.inlearning.app.R;
import com.inlearning.app.common.util.StatusBar;

public class AboutAppActivity extends BaseActivity {


    public static void startAboutActivity(Context context) {
        Intent intent = new Intent(context, AboutAppActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this, true);
        setContentView(R.layout.activity_about_app);
        findViewById(R.id.imv_edit_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
