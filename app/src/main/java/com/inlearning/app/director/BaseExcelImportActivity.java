package com.inlearning.app.director;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
    private CheckBox mChooseAllView;
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
                chooseAll(b);
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
                mBottomBar.setVisibility(View.GONE);
                mChooseAllView.setChecked(false);
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
        final Dialog dialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText(getTitleMsg());
        TextView infoView = dialog.findViewById(R.id.tv_content);
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
                upload();
                dialog.dismiss();
            }
        });
        dialog.show();
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
            doOpenFileResult(chooseFilePath);
        }
    }

    protected void setEmptyTipsVisibility(int visibility) {
        mEmptyTipsView.setVisibility(visibility);
    }

    protected void setRvContentVisibility(int visibility) {
        mRvContent.setVisibility(visibility);
    }

    protected abstract String getTitleMsg();

    protected abstract RecyclerView.Adapter getAdapter();

    protected abstract void delete();

    protected abstract void upload();

    protected abstract void doOpenFileResult(String path);

    protected abstract void chooseAll(boolean is);

}
