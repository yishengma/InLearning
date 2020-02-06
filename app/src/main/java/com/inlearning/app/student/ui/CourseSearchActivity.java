package com.inlearning.app.student.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.inlearning.app.R;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.widget.SearchView.ICallBack;
import com.inlearning.app.common.widget.SearchView.SearchView;
import com.inlearning.app.common.widget.SearchView.bCallBack;


public class CourseSearchActivity extends AppCompatActivity {
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search);
        StatusBar.setStatusBarTranslucent(this);
        StatusBar.setStatusBarDarkMode(this,true);
        searchView = findViewById(R.id.search_view);
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {

            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CourseSearchActivity.class);
        context.startActivity(intent);
    }
}
