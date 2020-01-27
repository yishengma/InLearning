package com.yishengma.inlearning.ui;

import android.view.View;
import android.widget.TextView;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.bean.CourseBean;
import com.zhpan.bannerview.holder.ViewHolder;

public class NetViewHolder implements ViewHolder<CourseBean> {

    @Override
    public int getLayoutId() {
        return R.layout.item_net;
    }

    @Override
    public void onBind(View itemView, CourseBean data, int position, int size) {
        TextView textView = itemView.findViewById(R.id.tv_describe);
        textView.setText(data.getName());
    }
}
