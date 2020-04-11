package com.inlearning.app.teacher.attendclass.func.discuss;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.common.util.PhotoUtils;
import com.inlearning.app.common.util.TakePhotoUtil;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public abstract class BaseFuncView extends RelativeLayout {

    public BaseFuncView(Context context) {
        super(context);
    }

    public BaseFuncView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFuncView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract Activity getActivity();

    void showPickDialog() {
        final Dialog bottomDialog = new Dialog(getActivity(), R.style.BottomDialog);
        bottomDialog.setContentView(R.layout.dialog_pick_image);
        TextView albumView = bottomDialog.findViewById(R.id.tv_album);
        TextView takePhotoView = bottomDialog.findViewById(R.id.tv_take_photo);
        TextView cancelView = bottomDialog.findViewById(R.id.tv_cancel);
        albumView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                autoObtainStoragePermission();
                bottomDialog.dismiss();
            }
        });
        takePhotoView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                autoObtainCameraPermission();
                bottomDialog.dismiss();
            }
        });
        cancelView.setOnClickListener(new OnClickListener() {
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
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            }
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                mTakePhotoPath = TakePhotoUtil.takePhoto(getActivity(), CODE_CAMERA_REQUEST);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        mTakePhotoPath = TakePhotoUtil.takePhoto(getActivity(), CODE_CAMERA_REQUEST);
                    }
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(getActivity(), CODE_GALLERY_REQUEST);
                }
                break;
            default:
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST:
                    Log.e("ethan", mTakePhotoPath);
                    setQuesImage(new File(mTakePhotoPath));
                    break;
                case CODE_GALLERY_REQUEST:
                    String path = FileUtil.getChooseFileResultPath(getActivity(), data.getData());
                    Log.e("ethan", path);
                    setQuesImage(new File(path));
                    break;
                default:
            }
        }
    }

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(getActivity(), CODE_GALLERY_REQUEST);
        }

    }

    public boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    abstract void setQuesImage(File fileCropUri);

    abstract void setQuesImage(Bitmap bitmap);
}
