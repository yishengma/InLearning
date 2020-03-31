package com.inlearning.app.teacher.attendclass.func.material;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;

public class MaterialView extends RelativeLayout implements View.OnClickListener {
    public MaterialView(Context context) {
        this(context, null);
    }

    public MaterialView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private ImageView mBackView;
    private TextView mAddView;
    private TextView mTitleView;
    private RecyclerView mRvMaterialView;
    private TextView mEmptyView;

    public interface ClickListener {
        void onBack();

        void onAdd();
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_material_function, this);
        mBackView = view.findViewById(R.id.imv_bar_back);
        mAddView = view.findViewById(R.id.tv_bar_add);
        mTitleView = view.findViewById(R.id.tv_edit_title);
        mRvMaterialView = view.findViewById(R.id.rv_material);
        mRvMaterialView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mEmptyView = view.findViewById(R.id.tv_empty);
        mBackView.setOnClickListener(this);
        mAddView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_bar_back:
                if (mClickListener != null) {
                    mClickListener.onBack();
                }
                break;
            case R.id.tv_bar_add:
                if (mClickListener != null) {
                    mClickListener.onAdd();
                }
                break;
        }
    }

    public void refreshMaterialView(boolean hasData) {
        mRvMaterialView.setVisibility(hasData ? VISIBLE : GONE);
        mEmptyView.setVisibility(hasData ? GONE : VISIBLE);
    }

    public void setTitle(String msg) {
        mTitleView.setText(msg);
    }

    public RecyclerView getMaterialView() {
        return mRvMaterialView;
    }

}
