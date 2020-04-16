package com.inlearning.app.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TakePhotoUtil {

    public static String takePhoto(Activity mActivity, int flag) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = null;
        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            String sdcardState = Environment.getExternalStorageState();
            File outputImage = null;
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                try {
                    outputImage = createImageFile(mActivity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

            }
            if (outputImage == null) {
                return "";
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = getUriForFile(mActivity, outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            mActivity.startActivityForResult(takePictureIntent, flag);
            return outputImage.getAbsolutePath();
        }
        return "";
    }

    private static File createImageFile(Activity mActivity) throws IOException {
        String timeStamp = formatTime(System.currentTimeMillis(), "yyyyMMdd_HHmmss");
        String imageFileName = "JPEG_" + timeStamp;//创建以时间命名的文件名称
        File storageDir = getOwnCacheDirectory(mActivity, "takephoto");//创建保存的路径
        File image = new File(storageDir.getPath(), imageFileName + ".jpg");
        if (!image.exists()) {
            try { //在指定的文件夹中创建文件
                image.createNewFile();
            } catch (Exception e) {
            }
        }
        return image;
    }

    private static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null; //判断sd卡正常挂载并且拥有权限的时候创建文件
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }

    public static Uri getUriForFile(Context context, File file) {
        Uri result = null;
        try {
            result = FileProvider.getUriForFile(context, "com.inlearning.app.fileprovider", file);
        } catch (Exception e) {


        }
        return result;
    }

    public static String formatTime(long time, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.getDefault());
        Date date = new Date(time);
        return sdf.format(date);
    }
}
