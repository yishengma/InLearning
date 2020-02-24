package com.inlearning.app.student.ui;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.zhpan.bannerview.holder.ViewHolder;

public class NetViewHolder implements ViewHolder<NetViewHolder.Banner> {

    @Override
    public int getLayoutId() {
        return R.layout.item_net;
    }

    @Override
    public void onBind(View itemView, Banner data, int position, int size) {
        Glide.with(itemView).load(data.drawable).into((ImageView) itemView.findViewById(R.id.banner_image));
    }

   public static class Banner {
        int drawable;

        public Banner(int drawable) {
            this.drawable = drawable;
        }
    }
}