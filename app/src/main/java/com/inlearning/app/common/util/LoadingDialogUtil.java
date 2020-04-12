package com.inlearning.app.common.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inlearning.app.R;


public class LoadingDialogUtil {

    private static Dialog sDialog;

    public static Dialog showLoadingDialog(Context context, String msg) {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = view.findViewById(R.id.dialog_loading_view);// 加载布局
        TextView tipTextView = view.findViewById(R.id.tipTextView);// 提示文字
        tipTextView.setText(msg);// 设置加载信息

        sDialog = new Dialog(context, R.style.LoadingDialogStyle);// 创建自定义样式dialog
        sDialog.setCancelable(true); // 是否可以按“返回键”消失
        sDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        sDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

        Window window = sDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        sDialog.show();
        return sDialog;
    }


    public static void closeDialog() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            ThreadMgr.getInstance().postToUIThread(new Runnable() {
                @Override
                public void run() {
                    if (sDialog != null && sDialog.isShowing()) {
                        sDialog.dismiss();
                    }
                }
            });
        }
        else {
            if (sDialog != null && sDialog.isShowing()) {
                sDialog.dismiss();
            }
        }
    }

}
