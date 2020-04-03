package com.inlearning.app.director.person.coursemanager.classes.organize;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.inlearning.app.R;

public class OrganizeCoursePresenter {

    private Context mContext;
    private Dialog mDialog;

    public OrganizeCoursePresenter(Context context) {
        mContext = context;
    }

    public void showOrganizeDialog() {
        mDialog = new Dialog(mContext, R.style.SimpleDialog);//SimpleDialog
        mDialog.setContentView(R.layout.dialog_organize_course);
//        TextView singleView = mDialog.findViewById(R.id.tv_single_import);
//        TextView excelView = mDialog.findViewById(R.id.tv_excel_import);
//        singleView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//            }
//        });
//        excelView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//            }
//        });
        mDialog.show();
    }
}
