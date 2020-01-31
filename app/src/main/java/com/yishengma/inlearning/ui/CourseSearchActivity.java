package com.yishengma.inlearning.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.yishengma.inlearning.R;
import com.yishengma.inlearning.util.StatusBar;
import com.yishengma.inlearning.widget.SearchView.ICallBack;
import com.yishengma.inlearning.widget.SearchView.SearchView;
import com.yishengma.inlearning.widget.SearchView.bCallBack;


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
