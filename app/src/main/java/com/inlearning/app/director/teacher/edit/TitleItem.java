package com.inlearning.app.director.teacher.edit;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.inlearning.app.R;

public class TitleItem implements View.OnClickListener {

    private EditItemView mEditItemView;
    private Context mContext;

    public TitleItem(Context context, View rootView) {
        mContext = context;
        mEditItemView = rootView.findViewById(R.id.item_tea_title);
        mEditItemView.setOnClickListener(this);
        mEditItemView.mTitleView.setText("头衔");
    }

    @Override
    public void onClick(View view) {
        showDialog();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(mContext, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_input);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("编辑头衔");
        final EditText infoView = dialog.findViewById(R.id.et_input_content);
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
                mEditItemView.mContentView.setText(infoView.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public String getContent() {
        return mEditItemView.mContentView.getText().toString();
    }

}