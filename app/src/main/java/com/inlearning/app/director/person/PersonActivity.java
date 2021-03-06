package com.inlearning.app.director.person;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.inlearning.app.BaseActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.PasswordActivity;
import com.inlearning.app.common.bean.Director;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.common.util.LoadingDialog;
import com.inlearning.app.common.util.PhotoUtils;
import com.inlearning.app.common.util.StatusBar;
import com.inlearning.app.common.util.TakePhotoUtil;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.common.widget.PersonInfoView;
import com.inlearning.app.director.DirectorAppRuntime;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PersonActivity extends BaseActivity {

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
    private ImageView mBackView;
    private TextView mEditPasswordView;

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
        mImageView.setPersonImageView(mDirector.mName, mDirector.getProfilePhotoUrl());
        mIdentifyView = new PersonInfoView(this);
        mIdentifyView.setTitleText("身份");
        mIdentifyView.setPersonContentView("管理员");
        mNameView = new PersonInfoView(this);
        mNameView.setTitleText("姓名");
        mNameView.setPersonContentView(mDirector.getName());
        mRootView.addView(mImageView);
        addDivideView();
        mRootView.addView(mIdentifyView);
        addDivideView();
        mRootView.addView(mNameView);
        addDivideView();
        mImageView.setClickListener(new PersonInfoView.ClickListener() {
            @Override
            public void onClick() {
                mImageDialog.show();
            }
        });
        initDialog();
        mBackView = findViewById(R.id.imv_edit_back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEditPasswordView = findViewById(R.id.tv_edit_password);
        mEditPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordActivity.startPasswordActivity(PersonActivity.this, PasswordActivity.Type.DIRECTOR);
            }
        });
    }

    public void addDivideView() {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.split_line));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);

        mRootView.addView(view, params);
    }


    private Dialog mImageDialog;
    private TextView mUpdateView;
    private ImageView mPersonImageView;
    private ImageView mDeleteImageView;
    private TextView mAddImageView;
    private FrameLayout mPersonImageLayout;

    private String mImageFilePath;

    private void initDialog() {
        mImageDialog = new Dialog(this, R.style.SimpleDialog);//SimpleDialog
        mImageDialog.setContentView(R.layout.view_dialog_choose_image);
        mImageDialog.setCanceledOnTouchOutside(true);
        mUpdateView = mImageDialog.findViewById(R.id.tv_update_image);
        mPersonImageView = mImageDialog.findViewById(R.id.imv_person_image);
        mDeleteImageView = mImageDialog.findViewById(R.id.imv_image_delete);
        mPersonImageLayout = mImageDialog.findViewById(R.id.view_person_image);
        mAddImageView = mImageDialog.findViewById(R.id.tv_add_image);
        mImageDialog.setCanceledOnTouchOutside(true);
        mAddImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickDialog();
            }
        });
        mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPostImageVisible(false);
                mImageFilePath = "";
                mAddImageView.setVisibility(VISIBLE);
                mUpdateView.setEnabled(false);
                mUpdateView.setBackground(getDrawable(R.drawable.bg_edit_gray_shape));
            }
        });
        mUpdateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateinfo();
            }
        });
        mImageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setPostImageVisible(false);
                mImageFilePath = "";
                mAddImageView.setVisibility(VISIBLE);
                mUpdateView.setEnabled(false);
                mUpdateView.setBackground(getDrawable(R.drawable.bg_edit_gray_shape));

            }
        });
    }

    private void updateinfo() {
        ThreadMgr.getInstance().postToSubThread(new Runnable() {
            @Override
            public void run() {
                BmobFile bmobFile = new BmobFile(new File(mImageFilePath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        mDirector.setProfilePhotoUrl(bmobFile.getFileUrl());
                        mDirector.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                updateImage();
                            }
                        });
                        DirectorAppRuntime.setsDirector(mDirector);
                    }
                });
            }
        });
    }

    private void updateImage() {
        LoadingDialog.showLoadingDialog(PersonActivity.this,"正在上传...");
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.closeDialog();
                mImageDialog.dismiss();
                mImageView.setPersonImageView(mDirector.mName, mDirector.getProfilePhotoUrl());
            }
        });
    }


    private void setPostImageVisible(boolean show) {
        mPersonImageLayout.setVisibility(show ? VISIBLE : GONE);
    }

    public void showPickDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        bottomDialog.setContentView(R.layout.dialog_pick_image);
        TextView albumView = bottomDialog.findViewById(R.id.tv_album);
        TextView takePhotoView = bottomDialog.findViewById(R.id.tv_take_photo);
        TextView cancelView = bottomDialog.findViewById(R.id.tv_cancel);
        albumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoObtainStoragePermission();
                bottomDialog.dismiss();
            }
        });
        takePhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoObtainCameraPermission();
                bottomDialog.dismiss();
            }
        });
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }


    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private String mTakePhotoPath = "";

    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                mTakePhotoPath = TakePhotoUtil.takePhoto(this, CODE_CAMERA_REQUEST);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        mTakePhotoPath = TakePhotoUtil.takePhoto(this, CODE_CAMERA_REQUEST);
                    }
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                }
                break;
            default:
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST:
                    setQuesImage(new File(mTakePhotoPath));
                    break;
                case CODE_GALLERY_REQUEST:
                    String path = FileUtil.getChooseFileResultPath(this, data.getData());
                    setQuesImage(new File(path));
                    break;
                default:
            }
        }
    }

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    public boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    public void setQuesImage(File fileCropUri) {
        mPersonImageLayout.setVisibility(VISIBLE);
        Glide.with(this).load(fileCropUri).into(mPersonImageView);
        mImageFilePath = fileCropUri.getPath();
        mAddImageView.setVisibility(GONE);
        mUpdateView.setBackground(getDrawable(R.drawable.bg_edit_blue_shape));
        mUpdateView.setEnabled(true);
    }
}

