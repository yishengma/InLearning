package com.inlearning.student.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.student.R;
import com.inlearning.student.bean.CourseBean;
import com.inlearning.student.ui.NetViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;
import com.zhpan.bannerview.holder.HolderCreator;

import java.util.ArrayList;
import java.util.List;

import static com.zhpan.bannerview.constants.PageStyle.MULTI_PAGE_SCALE;

public class RecentCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_BANNER = 0;
    public static final int ITEM_TYPE_TITLE= 1;
    public static final int ITEM_TYPE_COURSE = 2;
    private List<CourseBean> mList;
    private OnClickListener mOnClickListener;
    private List<NetViewHolder.Banner> mBannerList;

    public RecentCourseAdapter(List<CourseBean> list) {
        mList = list;
        mBannerList = new ArrayList<>();
        mBannerList.add(new NetViewHolder.Banner(R.drawable.test10));
        mBannerList.add(new NetViewHolder.Banner(R.drawable.test2));
        mBannerList.add(new NetViewHolder.Banner(R.drawable.test3));
        mBannerList.add(new NetViewHolder.Banner(R.drawable.test4));
    }

    public interface OnClickListener {
        void onClick(CourseBean courseBean);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case ITEM_TYPE_COURSE:
                View courseView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recent_course, viewGroup, false);
                return new CourseViewHolder(courseView);
            case ITEM_TYPE_BANNER:
                View bannerView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_banner_view, viewGroup, false);
                return new BannerViewHolder(bannerView);
            case ITEM_TYPE_TITLE:
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_type, viewGroup, false);
                return new TypeViewHolder(view);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CourseViewHolder) {
            bindCourseView((CourseViewHolder) viewHolder, i);
        }else if (viewHolder instanceof  BannerViewHolder) {
            bindBannerView((BannerViewHolder) viewHolder);
        }else {
            switch (getItemViewType(i)){
                case ITEM_TYPE_TITLE:
                    ((TypeViewHolder) viewHolder).mType.setText("必修课");
                    break;
            }
        }
    }

    private void bindCourseView(RecentCourseAdapter.CourseViewHolder viewHolder, int i) {
        final CourseBean course = mList.get(i);
        viewHolder.mCourseName.setText(course.getName());
        viewHolder.mCourseTime.setText(course.getTime());
        viewHolder.mCourseType.setText(course.getType());
        viewHolder.mCourseStuCount.setText(String.format("%d学生", course.getStudentCount()));
        //Glide.with(viewHolder.itemView).load(course.getIconUrl()).into(viewHolder.mCourseIcon);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(course);
                }
            }
        });
    }

    private void bindBannerView(RecentCourseAdapter.BannerViewHolder viewHolder) {
        viewHolder.mBannerViewPager.showIndicator(true)
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
        viewHolder.mBannerViewPager.stopLoop();
    }


    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }


    class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseName;
        private TextView mCourseTime;
        private TextView mCourseType;
        private TextView mCourseStuCount;
        private ImageView mCourseIcon;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            mCourseName = itemView.findViewById(R.id.tv_course_name);
            mCourseTime = itemView.findViewById(R.id.tv_course_time);
            mCourseType = itemView.findViewById(R.id.tv_course_type);
            mCourseIcon = itemView.findViewById(R.id.imv_course_icon);
            mCourseStuCount = itemView.findViewById(R.id.tv_student_count);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private BannerViewPager<NetViewHolder.Banner, NetViewHolder> mBannerViewPager;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            mBannerViewPager = itemView.findViewById(R.id.banner_view);
        }
    }

    class TypeViewHolder extends RecyclerView.ViewHolder {
        private TextView mType;

        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);
            mType = itemView.findViewById(R.id.tv_course_type);
        }
    }
}
