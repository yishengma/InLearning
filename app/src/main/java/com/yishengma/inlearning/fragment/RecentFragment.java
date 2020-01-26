package com.yishengma.inlearning.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yishengma.inlearning.R;
import com.yishengma.inlearning.adapter.RecentCourseAdapter;
import com.yishengma.inlearning.fragment.BaseFragment;
import com.yishengma.inlearning.ui.CourseSearchActivity;
import com.yishengma.inlearning.ui.NetViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;
import com.zhpan.bannerview.holder.HolderCreator;

import java.util.ArrayList;
import java.util.List;

import static com.zhpan.bannerview.constants.PageStyle.MULTI_PAGE_SCALE;

public class RecentFragment extends BaseFragment implements View.OnClickListener {
    private List<String> mList;
    private RecentCourseAdapter mCourseAdapter;
    private RecyclerView mRecyclerView;
    private ImageView mSearchView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        initViewPager(view);
        return view;
    }

    private BannerViewPager<String, NetViewHolder> mBannerViewPager;

    private void initViewPager(View view) {
        mList = new ArrayList<>();
        mList.add("0");
        mList.add("1");
        mList.add("2");
        mList.add("3");
        mBannerViewPager = view.findViewById(R.id.banner_view);
        mBannerViewPager.showIndicator(true)
                .setInterval(3000)
                .setCanLoop(false)
                .setAutoPlay(true)
                .setRoundCorner(7)
                .setPageStyle(MULTI_PAGE_SCALE)
                .setIndicatorColor(Color.parseColor("#935656"), Color.parseColor("#FF4C39"))
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setScrollDuration(1000)
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setHolderCreator(new HolderCreator<NetViewHolder>() {
                    @Override
                    public NetViewHolder createViewHolder() {
                        return new NetViewHolder();
                    }
                })
                .create(mList);
        mBannerViewPager.stopLoop();
        mRecyclerView = view.findViewById(R.id.rv_recent_course);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        mCourseAdapter = new RecentCourseAdapter(mList);
        mRecyclerView.setAdapter(mCourseAdapter);
        mSearchView = view.findViewById(R.id.imv_search);
        mSearchView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_search:
                CourseSearchActivity.startActivity(getContext());
                break;
            default:
                break;
        }
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
