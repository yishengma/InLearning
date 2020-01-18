package com.yishengma.inlearning.recent;

import android.view.View;
import android.widget.TextView;

import com.yishengma.inlearning.R;
import com.zhpan.bannerview.holder.ViewHolder;

public class NetViewHolder implements ViewHolder<String> {

    @Override
    public int getLayoutId() {
        return R.layout.item_net;
    }

    @Override
    public void onBind(View itemView, String data, int position, int size) {
        TextView textView = itemView.findViewById(R.id.tv_describe);
        textView.setText(data);
    }
}
