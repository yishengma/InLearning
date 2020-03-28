package com.inlearning.app.teacher.attendclass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;

public class ChapterFuncItemView extends RelativeLayout implements View.OnClickListener {


    public ChapterFuncItemView(Context context) {
        this(context, null);
    }

    public ChapterFuncItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChapterFuncItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private ImageView mIconView;
    private TextView mTextView;
    private TextView mContentView;
    private RelativeLayout mItemView;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_chapter_function, this);
        mIconView = view.findViewById(R.id.imv_icon);
        mTextView = view.findViewById(R.id.tv_text);
        mContentView = view.findViewById(R.id.tv_content);
        mItemView = view.findViewById(R.id.item_view);
        mItemView.setOnClickListener(this);
    }

    public void setText(String msg) {
        mTextView.setText(msg);
    }


    public void setTextColor(int color) {
        mTextView.setTextColor(getResources().getColor(color));
    }

    public void setContent(String content) {
        mContentView.setText(content);
    }

    public void setIcon(int resId) {
        mIconView.setImageDrawable(getResources().getDrawable(resId));
    }

    public interface ClickListener {
        void onClick();
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_view:
                if (mClickListener != null) {
                    mClickListener.onClick();
                }
                break;
        }
    }
}
