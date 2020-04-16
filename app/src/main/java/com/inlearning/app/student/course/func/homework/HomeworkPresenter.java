package com.inlearning.app.student.course.func.homework;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Answer;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.common.util.LoadingDialog;
import com.inlearning.app.common.util.PhotoUtils;
import com.inlearning.app.common.util.TakePhotoUtil;
import com.inlearning.app.common.util.ThreadMgr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class HomeworkPresenter {

    private HomeworkFuncView mHomeworkFuncView;
    private List<Homework> mHomeworkList;
    private HomeworkAdapter mHomeworkAdapter;
    private CourseChapter mChapter;
    private Activity mContext;
    private Homework mHomework;

    public HomeworkPresenter(final Activity context, View rootView, CourseChapter chapter) {
        mContext = context;
        mHomeworkFuncView = rootView.findViewById(R.id.view_homework_func);
        mHomeworkFuncView.setClickListener(new HomeworkFuncView.ClickListener() {
            @Override
            public void onBack() {
                context.finish();
            }
        });
        mHomeworkList = new ArrayList<>();
        mHomeworkAdapter = new HomeworkAdapter(mHomeworkList, context);
        mHomeworkAdapter.setClickListener(new HomeworkAdapter.ClickListener() {
            @Override
            public void onAddImage(Homework homework) {
                mHomework = homework;
                showPickDialog();
            }

            @Override
            public void onDeleteImage(Homework homework) {
                homework.getAnswer().setImageUrl("");
                mHomeworkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSaveAnswer(final Homework homework) {
                LoadingDialog.showLoadingDialog(mContext, "正在保存...");
                HomeworkModel.uploadAnswer(homework.getAnswer(), new HomeworkModel.Callback<Answer>() {
                    @Override
                    public void onResult(final Answer answer) {
                        LoadingDialog.closeDialog();
                        ThreadMgr.getInstance().postToUIThread(new Runnable() {
                            @Override
                            public void run() {
                                homework.setAnswer(answer);
                                mHomeworkAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });
        mHomeworkFuncView.getRvHomework().setAdapter(mHomeworkAdapter);
        mChapter = chapter;
        HomeworkModel.getHomework(mChapter, new HomeworkModel.Callback<List<Homework>>() {
            @Override
            public void onResult(List<Homework> homeworkList) {
                mHomeworkList.clear();
                mHomeworkList.addAll(homeworkList);
                mHomeworkAdapter.notifyDataSetChanged();
                mHomeworkFuncView.refreshUI(!mHomeworkList.isEmpty());

            }
        });
    }

    public void show() {
        mHomeworkFuncView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mHomeworkFuncView.setVisibility(View.GONE);
    }

    private void showPickDialog() {
        final Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
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
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.CAMERA)) {
            }
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                mTakePhotoPath = TakePhotoUtil.takePhoto(mContext, CODE_CAMERA_REQUEST);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        mTakePhotoPath = TakePhotoUtil.takePhoto(mContext, CODE_CAMERA_REQUEST);
                    }
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(mContext, CODE_GALLERY_REQUEST);
                }
                break;
            default:
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST:
                    mHomework.getAnswer().setImageUrl(new File(mTakePhotoPath).getPath());
                    mHomeworkAdapter.notifyDataSetChanged();
                    break;
                case CODE_GALLERY_REQUEST:
                    String path = FileUtil.getChooseFileResultPath(mContext, data.getData());
                    mHomework.getAnswer().setImageUrl(new File(path).getPath());
                    mHomeworkAdapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    }

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(mContext, CODE_GALLERY_REQUEST);
        }

    }

    private boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}
