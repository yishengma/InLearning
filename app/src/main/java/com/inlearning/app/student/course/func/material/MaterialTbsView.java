package com.inlearning.app.student.course.func.material;


import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.text.DecimalFormat;

import static android.content.Context.DOWNLOAD_SERVICE;

public class MaterialTbsView extends RelativeLayout implements View.OnClickListener, TbsReaderView.ReaderCallback {

    public MaterialTbsView(Context context) {
        this(context, null);
    }

    public MaterialTbsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialTbsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface ClickListener {
        void onBack();
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }


    private ImageView mBackView;
    private TextView mTitleView;
    private TbsReaderView mTbsReaderView;
    private TextView mDownloadTextView;
    private RelativeLayout mTbsView;
    private ProgressBar mProgressBar;
    private long mRequestId;
    private DownloadObserver mDownloadObserver;
    private DownloadManager mDownloadManager;
    private String mFileUrl;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_material_tbs_view, this);
        mBackView = view.findViewById(R.id.imv_bar_back);
        mTitleView = view.findViewById(R.id.tv_edit_title);
        mDownloadTextView = view.findViewById(R.id.tv_download);
        mTbsView = view.findViewById(R.id.tbs_view);
        mProgressBar = view.findViewById(R.id.progress_download);
        mTbsReaderView = new TbsReaderView(getContext(), this);
        mTbsView.addView(mTbsReaderView, new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                if (mClickListener != null) {
                    mClickListener.onBack();
                }
                break;
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }


    public void setTitle(String msg) {
        mTitleView.setText(msg);
    }

    public void openFile(String name ,String filePath) {
        Log.e("ethan", filePath);
        setVisibility(VISIBLE);
        mFileUrl = filePath;
        mProgressBar.setVisibility(VISIBLE);
        mDownloadTextView.setVisibility(VISIBLE);
        if (isLocalExist(parseName(filePath))) {
            Log.e("ethan", parseName(filePath));
            mDownloadTextView.setText("打开文件");
            mDownloadTextView.setVisibility(View.GONE);
            displayFile();
        } else {
            if (!filePath.contains("http")) {
                new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示:")
                        .setMessage("文件的url地址不合法哟，无法进行下载")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                return;
                            }
                        }).create().show();
            }
            Log.e("ethan", "start download");
            startDownload();
        }
        setTitle(name);
    }


    private String toUtf8String(String url) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }


    private void displayFile() {
        mProgressBar.setVisibility(GONE);
        mDownloadTextView.setVisibility(GONE);
        mTbsReaderView.setVisibility(VISIBLE);
        Bundle bundle = new Bundle();
        bundle.putString("filePath", getLocalFile(parseName(mFileUrl)).getPath());
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = mTbsReaderView.preOpen(parseFormat(parseName(mFileUrl)), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }
    }


    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String parseName(String url) {
        String fileName = null;
        try {
            fileName = url.substring(url.lastIndexOf("/") + 1);
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }
        }
        return fileName;
    }


    private boolean isLocalExist(String fileName) {
        return getLocalFile(fileName).exists();
    }

    private File getLocalFile(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        Log.e("ethan", file.getPath());
        return file;
    }


    private void startDownload() {
        mDownloadObserver = new DownloadObserver(new Handler());
        getContext().getContentResolver().registerContentObserver(
                Uri.parse("content://downloads/my_downloads"), true,
                mDownloadObserver);

        mDownloadManager = (DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);
        //将含有中文的url进行encode
        String fileUrl = toUtf8String(mFileUrl);
        try {

            DownloadManager.Request request = new DownloadManager.Request(
                    Uri.parse(fileUrl));
            request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS, parseName(mFileUrl));
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            mRequestId = mDownloadManager.enqueue(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadObserver extends ContentObserver {

        private DownloadObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            queryDownloadStatus();
        }
    }

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query()
                .setFilterById(mRequestId);
        Cursor cursor = null;
        try {
            cursor = mDownloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                long currentBytes = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                long totalBytes = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                mDownloadTextView.setText(String.format("下载中...(%s/%s)", formatKMGByBytes(currentBytes), formatKMGByBytes(totalBytes)));
                int progress = (int) ((currentBytes * 1.0) / totalBytes * 100);
                mProgressBar.setProgress(progress);
                Log.i("downloadUpdate: ", currentBytes + " " + totalBytes + " " + status + " " + progress);
                if (DownloadManager.STATUS_SUCCESSFUL == status && mDownloadTextView.getVisibility() == View.VISIBLE) {
                    if (isLocalExist(parseName(mFileUrl))) {
                        displayFile();
                    }
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private String formatKMGByBytes(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.00");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

    public void onDestroy() {
        mTbsReaderView.onStop();
        if (mDownloadObserver != null) {
            getContext().getContentResolver().unregisterContentObserver(mDownloadObserver);
        }
    }
}
