package com.inlearning.app.director.teacher.edit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.inlearning.app.R;

public class EditItemView extends FrameLayout {

    public EditItemView(@NonNull Context context) {
        this(context,null);
    }

    public EditItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

     View mItemView;
     TextView mTitleView;
     TextView mContentView;

    private void initView () {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_director_teacher_edit_item,this);
        mItemView = view.findViewById(R.id.fl_director_tea_info);
        mTitleView = view.findViewById(R.id.tv_edit_item_title);
        mContentView = view.findViewById(R.id.tv_edit_item_content);
    }
}
