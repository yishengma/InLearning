package com.inlearning.app.director;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.util.FileUtil;

public abstract class BaseExcelImportActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int EXCEL_OPEN_REQUEST = 1;
    private ImageView mBackView;
    private TextView mTitleView;
    private TextView mManageView;
    private RecyclerView mRvContent;
    private RadioButton mChooseAllView;
    private ViewGroup mEmptyTipsView;
    private TextView mDeleteView;
    private RelativeLayout mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_excel_import);
        initView();
    }

    private void initView() {
        mBackView = findViewById(R.id.imv_edit_back);
        mTitleView = findViewById(R.id.tv_edit_title);
        mManageView = findViewById(R.id.tv_edit_manager);
        mRvContent = findViewById(R.id.rv_content);
        mChooseAllView = findViewById(R.id.btn_choose_all);
        mEmptyTipsView = findViewById(R.id.ll_empty_view);
        mDeleteView = findViewById(R.id.tv_delete);
        mBottomBar = findViewById(R.id.rl_bottom_tool);
        mTitleView.setText(getTitleMsg());
        mRvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvContent.setAdapter(getAdapter());
        mBackView.setOnClickListener(this);
        mManageView.setOnClickListener(this);
        mDeleteView.setOnClickListener(this);
        mEmptyTipsView.setOnClickListener(this);
        mChooseAllView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_edit_back:
                finish();
                break;
            case R.id.ll_empty_view:
                openFileManager();
                break;
            case R.id.tv_delete:
                delete();
                break;
            case R.id.tv_edit_manager:
                onClickManager();
                break;
            default:
                break;
        }
    }

    private void onClickManager() {
        if (mManageView.getText().equals("管理")) {
            mBottomBar.setVisibility(View.VISIBLE);
            mManageView.setText("完成");
        } else if (mManageView.getText().equals("完成")) {
            showUploadDialog();
        }
    }

    private void showUploadDialog() {

    }


    private void openFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*.xls");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, EXCEL_OPEN_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//选择文件返回
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EXCEL_OPEN_REQUEST) {
            String chooseFilePath;
            Uri uri = data.getData();
            chooseFilePath = FileUtil.getChooseFileResultPath(this, uri);
            Log.e("chooseFilePath", chooseFilePath);
            doOpenFileResult(chooseFilePath);
        }
    }


    protected abstract String getTitleMsg();

    protected abstract RecyclerView.Adapter getAdapter();

    protected abstract void delete();

    protected abstract void upload();

    protected abstract void doOpenFileResult(String path);
}
