package com.inlearning.app.director;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.inlearning.app.BaseActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.BaseActivity;
import com.inlearning.app.R;
import com.inlearning.app.common.util.StatusBar;

public abstract class BaseSingleImportActivity extends BaseActivity {

//    public static void startEditActivity(Context context) {
//        Intent intent = new Intent(context, BaseSingleImportActivity.class);
//        context.startActivity(intent);
//    }

    protected ViewGroup mRootView;
    protected ImageView mBackView;
    protected TextView mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_single_import);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this,true);
        mRootView = findViewById(R.id.root_view);
        mBackView = findViewById(R.id.imv_edit_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doBack();
                finish();
            }
        });
    }

    protected abstract void doBack();
}
