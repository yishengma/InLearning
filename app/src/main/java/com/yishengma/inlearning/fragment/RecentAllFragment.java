package com.yishengma.inlearning.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.adapter.RecentCourseAdapter;
import com.yishengma.inlearning.bean.CourseBean;
import com.yishengma.inlearning.ui.CourseSearchActivity;
import com.yishengma.inlearning.ui.CourseTaskActivity;
import com.yishengma.inlearning.ui.NetViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;
import com.zhpan.bannerview.holder.HolderCreator;

import java.util.ArrayList;
import java.util.List;

import static com.zhpan.bannerview.constants.PageStyle.MULTI_PAGE_SCALE;

public class RecentAllFragment extends BaseFragment {
    private List<CourseBean> mList;
    private RecentCourseAdapter mCourseAdapter;
    private RecyclerView mRecyclerView;
    private List<NetViewHolder.Banner> mBannerList;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        Log.e("RecentAllFragment","Create");
        initData();
        initViewPager(view);
        return view;
    }

    private BannerViewPager<NetViewHolder.Banner, NetViewHolder> mBannerViewPager;

    private void initViewPager(View view) {
        mBannerList = new ArrayList<>();
        mBannerList.add(new NetViewHolder.Banner(R.drawable.test10));
        mBannerList.add(new NetViewHolder.Banner(R.drawable.test2));
        mBannerList.add(new NetViewHolder.Banner(R.drawable.test3));
        mBannerList.add(new NetViewHolder.Banner(R.drawable.test4));
        mBannerViewPager = view.findViewById(R.id.banner_view);
        mBannerViewPager.showIndicator(true)
                .setInterval(3000)
                .setCanLoop(false)
                .setAutoPlay(true)
                .setRoundCorner(7)
                .setPageStyle(MULTI_PAGE_SCALE)
                .setScrollDuration(1000)
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setHolderCreator(new HolderCreator<NetViewHolder>() {
                    @Override
                    public NetViewHolder createViewHolder() {
                        return new NetViewHolder();
                    }
                })
                .create(mBannerList);
        mBannerViewPager.stopLoop();
        mRecyclerView = view.findViewById(R.id.rv_recent_course);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        mCourseAdapter = new RecentCourseAdapter(mList);
        mRecyclerView.setAdapter(mCourseAdapter);
        mCourseAdapter.setOnClickListener(new RecentCourseAdapter.OnClickListener() {
            @Override
            public void onClick(CourseBean courseBean) {
                CourseTaskActivity.startActivity(getContext());
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
        mList.add(new CourseBean());
    }


    //    @Override
//    protected void onA() {
//        if (mBannerViewPager != null)
//            mBannerViewPager.stopLoop();
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mBannerViewPager != null)
//            mBannerViewPager.startLoop();
//    }
}
