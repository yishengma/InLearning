package com.inlearning.app.teacher.attendclass;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.util.V;

public class CourseChapterAdapter extends RecyclerView.Adapter<CourseChapterAdapter.ViewHolder> {
    private List<ChapterProxy> mCourseChapters;

    public CourseChapterAdapter(List<ChapterProxy> chapterProxies) {
        mCourseChapters = chapterProxies;
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public interface OnClickListener {

        void onTitleClick(CourseChapter chapter);

        void onVideoClick(CourseChapter chapter);

        void onTimeClick(CourseChapter chapter);

        @Deprecated
        void onExerciseClick(CourseChapter chapter);

        void onMaterialClick(CourseChapter chapter);

        void onHomeworkClick(CourseChapter chapter);

        void onDiscussClick(CourseChapter chapter);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_teacher_course_chapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChapterProxy proxy = mCourseChapters.get(i);
        final CourseChapter chapter = proxy.getChapter();
        viewHolder.mChapterNumView.setText(String.format("第%s节", chapter.getChapterNum()));
        viewHolder.mChapterNameView.setText(chapter.getChapterName());
        viewHolder.mChapterNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onTitleClick(chapter);
                }
            }
        });
        if (TextUtils.isEmpty(chapter.getVideoFile().getFileUrl())) {
            viewHolder.mVideoView.setImageResource(R.drawable.icon_play_gray);
            viewHolder.mProgressBar.setVisibility(View.GONE);
        } else {
            viewHolder.mVideoView.setImageResource(R.drawable.icon_play_blue);
            viewHolder.mProgressBar.setVisibility(View.VISIBLE);
            viewHolder.mProgressBar.setProgress(100);
        }
        if (proxy.getProgress() != 0 && proxy.getProgress() != 100) {
            viewHolder.mProgressBar.setVisibility(View.VISIBLE);
            viewHolder.mProgressBar.setProgress(proxy.getProgress());
        }
        viewHolder.mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onVideoClick(chapter);
                }
            }
        });
        if (!TextUtils.isEmpty(chapter.getDeadLine().trim())) {
            Date date = new Date(Long.valueOf(chapter.getDeadLine().trim()));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String time = dateFormat.format(date);
            viewHolder.mTimeFuncView.setTextColor(R.color.app_global_blue);
            viewHolder.mTimeFuncView.setIcon(R.drawable.icon_time_blue);
            viewHolder.mTimeFuncView.setContent(time);
        } else {
            viewHolder.mTimeFuncView.setTextColor(R.color.app_global_gray);
            viewHolder.mTimeFuncView.setIcon(R.drawable.icon_time_gray);
            viewHolder.mTimeFuncView.setContent("暂未指定");
        }
        viewHolder.mTimeFuncView.setClickListener(new ChapterFuncItemView.ClickListener() {
            @Override
            public void onClick() {
                if (mOnClickListener != null) {
                    mOnClickListener.onTimeClick(chapter);
                }
            }
        });
        if (chapter.getMaterialCount() != 0) {
            viewHolder.mMaterialFuncView.setTextColor(R.color.app_global_blue);
            viewHolder.mMaterialFuncView.setIcon(R.drawable.icon_material_blue);
            viewHolder.mMaterialFuncView.setContent(chapter.getMaterialCount() + "个文件");
        } else {
            viewHolder.mMaterialFuncView.setTextColor(R.color.app_global_gray);
            viewHolder.mMaterialFuncView.setIcon(R.drawable.icon_material_gray);
            viewHolder.mMaterialFuncView.setContent("暂未上传");
        }
        viewHolder.mMaterialFuncView.setClickListener(new ChapterFuncItemView.ClickListener() {
            @Override
            public void onClick() {
                if (mOnClickListener != null) {
                    mOnClickListener.onMaterialClick(chapter);
                }
            }
        });
//
//        if (chapter.getExerciseCount() != 0) {
//            viewHolder.mExerciseFuncView.setTextColor(R.color.app_global_blue);
//            viewHolder.mExerciseFuncView.setIcon(R.drawable.icon_exercise_blue);
//            viewHolder.mExerciseFuncView.setContent(chapter.getMaterialCount() + "个题目");
//        } else {
//            viewHolder.mExerciseFuncView.setTextColor(R.color.app_global_gray);
//            viewHolder.mExerciseFuncView.setIcon(R.drawable.icon_exercise_gray);
//            viewHolder.mExerciseFuncView.setContent("暂未上传");
//        }
//        viewHolder.mExerciseFuncView.setClickListener(new ChapterFuncItemView.ClickListener() {
//            @Override
//            public void onClick() {
//                if (mOnClickListener != null) {
//                    mOnClickListener.onExerciseClick(chapter);
//                }
//            }
//        });
        if (chapter.getHomeworkCount() != 0) {
            viewHolder.mHomeworkFuncView.setTextColor(R.color.app_global_blue);
            viewHolder.mHomeworkFuncView.setIcon(R.drawable.icon_homework_blue);
            viewHolder.mHomeworkFuncView.setContent(chapter.getHomeworkCount() + "个题目");
        } else {
            viewHolder.mHomeworkFuncView.setTextColor(R.color.app_global_gray);
            viewHolder.mHomeworkFuncView.setIcon(R.drawable.icon_homework_gray);
            viewHolder.mHomeworkFuncView.setContent("暂未上传");
        }
        viewHolder.mHomeworkFuncView.setClickListener(new ChapterFuncItemView.ClickListener() {
            @Override
            public void onClick() {
                if (mOnClickListener != null) {
                    mOnClickListener.onHomeworkClick(chapter);
                }
            }
        });
        if (chapter.getDiscussCount() != 0) {
            viewHolder.mDiscussFuncView.setTextColor(R.color.app_global_blue);
            viewHolder.mDiscussFuncView.setIcon(R.drawable.icon_discuss_blue);
            viewHolder.mDiscussFuncView.setContent(chapter.getDiscussCount() + "个讨论");
        } else {
            viewHolder.mDiscussFuncView.setTextColor(R.color.app_global_gray);
            viewHolder.mDiscussFuncView.setIcon(R.drawable.icon_dicuss_gray);
            viewHolder.mDiscussFuncView.setContent("暂无讨论");
        }
        viewHolder.mDiscussFuncView.setClickListener(new ChapterFuncItemView.ClickListener() {
            @Override
            public void onClick() {
                if (mOnClickListener != null) {
                    mOnClickListener.onDiscussClick(chapter);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mCourseChapters == null) {
            return 0;
        }
        return mCourseChapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mChapterNumView;
        private TextView mChapterNameView;
        private ImageView mVideoView;
        private ChapterFuncItemView mTimeFuncView;
        private ChapterFuncItemView mMaterialFuncView;
//        private ChapterFuncItemView mExerciseFuncView;
        private ChapterFuncItemView mHomeworkFuncView;
        private ChapterFuncItemView mDiscussFuncView;
        private ProgressBar mProgressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mChapterNumView = itemView.findViewById(R.id.tv_chapter_num);
            mChapterNameView = itemView.findViewById(R.id.tv_chapter_name);
            mVideoView = itemView.findViewById(R.id.imv_video_play);
            mProgressBar = itemView.findViewById(R.id.view_progress);
            mTimeFuncView = itemView.findViewById(R.id.func_time_view);
            mTimeFuncView.setText("时间");
            mMaterialFuncView = itemView.findViewById(R.id.func_material_view);
            mMaterialFuncView.setText("资料");
//            mExerciseFuncView = itemView.findViewById(R.id.func_exercise_view);
//            mExerciseFuncView.setText("练习");
            mHomeworkFuncView = itemView.findViewById(R.id.func_homework_view);
            mHomeworkFuncView.setText("作业");
            mDiscussFuncView = itemView.findViewById(R.id.func_discuss_view);
            mDiscussFuncView.setText("讨论");
        }
    }
}
