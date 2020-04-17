package com.inlearning.app.common.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;


public class PersonInfoView extends RelativeLayout {

    public PersonInfoView(Context context) {
        this(context, null);
    }

    public PersonInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface ClickListener {
        void onClick();
    }
    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    private TextView mPersonTitleView;
    private TextView mPersonContentView;
    private ImageView mPersonImageView;
    private View mRootView;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_person_info, this);
        mRootView = view.findViewById(R.id.root_view);
        mPersonTitleView = view.findViewById(R.id.tv_person_title);
        mPersonContentView = view.findViewById(R.id.tv_person_content);
        mPersonImageView = view.findViewById(R.id.imv_person_image);
        mRootView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick();
                }
            }
        });

    }

    public void setTitleText(String msg) {
        mPersonTitleView.setVisibility(VISIBLE);
        mPersonTitleView.setText(msg);
    }

    public void setPersonImageView(String path) {
        mPersonContentView.setVisibility(GONE);
        mPersonImageView.setVisibility(VISIBLE);
        if (TextUtils.isEmpty(path)) {
            mPersonImageView.setBackgroundDrawable(getContext().getDrawable(R.drawable.icon_common_image));
            return;
        }
        Glide.with(getContext()).load(path).into(mPersonImageView);
    }

    public void setPersonContentView(String msg) {
        mPersonContentView.setVisibility(VISIBLE);
        mPersonContentView.setText(msg);
    }

}
