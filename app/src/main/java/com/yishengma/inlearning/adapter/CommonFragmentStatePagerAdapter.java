package com.yishengma.inlearning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


public class CommonFragmentStatePagerAdapter<F extends Fragment> extends FragmentStatePagerAdapter {


    private List<F> list;
    private boolean myNotify = false;

    public CommonFragmentStatePagerAdapter(FragmentManager fm, List<F> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getItemPosition(Object object) {
        if (!myNotify) {
            return super.getItemPosition(object);
        } else {
            return POSITION_NONE;
        }
    }

    public void notifyDataSetChanged(boolean isRefresh) {
        myNotify = isRefresh;
        super.notifyDataSetChanged();
        myNotify = false;
    }


    @Override
    public F getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

}
