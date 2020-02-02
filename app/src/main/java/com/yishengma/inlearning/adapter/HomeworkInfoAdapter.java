package com.yishengma.inlearning.adapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import com.yishengma.inlearning.R;
import com.yishengma.inlearning.bean.ChapterBean;
import com.yishengma.inlearning.bean.CourseBean;
import java.util.ArrayList;
import java.util.List;

public class HomeworkInfoAdapter extends RecyclerView.Adapter<HomeworkInfoAdapter.CourseViewHolder> {
    private List<CourseBean> mCourseList;

    public HomeworkInfoAdapter(List<CourseBean> courseList) {
        mCourseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task_homework_view, viewGroup, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder viewHolder, int i) {
        CourseBean bean = mCourseList.get(i);
        viewHolder.mCourseName.setText(bean.getName());
        CourseViewHolder.Adapter adapter = new CourseViewHolder.Adapter(bean.getChapters());
        viewHolder.mRvCourseChapter.setLayoutManager(new LinearLayoutManager(viewHolder.itemView.getContext(), LinearLayoutManager.VERTICAL, false));
        viewHolder.mRvCourseChapter.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRvCourseChapter;
        private TextView mCourseName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            mRvCourseChapter = itemView.findViewById(R.id.rv_course_chapter);
            mCourseName = itemView.findViewById(R.id.tv_course_name);
        }

        public  static class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
            private List<ChapterBean> mTempChapterList;
            private List<ChapterBean> mChapterList;

            public Adapter(List<ChapterBean> tempChapterList) {
                this.mTempChapterList = new ArrayList<>();
                mTempChapterList.addAll(tempChapterList);
                mChapterList = new ArrayList<>();
                int size = mTempChapterList.size();
                if (size <= 3) {
                    mChapterList.addAll(mTempChapterList);
                } else {
                    for (int i = 0; i < 3; i++) {
                        mChapterList.add(mTempChapterList.remove(i));
                    }
                    mChapterList.add(new ChapterBean().setItemType(ChapterBean.ITEM_TYPE_LOAD));
                }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                switch (i) {
                    case ChapterBean.ITEM_TYPE_CHAPTER:
                        View chapterView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course_homework, viewGroup, false);
                        return new ChapterViewHolder(chapterView);
                    case ChapterBean.ITEM_TYPE_LOAD:
                        View loadView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_load_more, viewGroup, false);
                        return new LoadMoreViewHolder(loadView);
                    default:
                        break;
                }
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                ChapterBean chapterBean = mChapterList.get(i);
                if (viewHolder instanceof ChapterViewHolder) {
                    ((ChapterViewHolder) viewHolder).mChapterName.setText(chapterBean.getName());
//                    ((ChapterViewHolder)viewHolder).mChapterInfo.setText();
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
                if (viewHolder instanceof LoadMoreViewHolder) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mChapterList.remove(mChapterList.size() - 1);
                            while (!mTempChapterList.isEmpty()) {
                                mChapterList.add(mTempChapterList.remove(0));
                            }
                            Adapter.this.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return mChapterList.size();
            }

            @Override
            public int getItemViewType(int position) {
                return mChapterList.get(position).getItemType();
            }

            class ChapterViewHolder extends RecyclerView.ViewHolder {
                private TextView mChapterName;

                public ChapterViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mChapterName = itemView.findViewById(R.id.tv_chapter_name);
                }
            }

            class LoadMoreViewHolder extends RecyclerView.ViewHolder {
                public LoadMoreViewHolder(@NonNull View itemView) {
                    super(itemView);
                }
            }
        }
    }
}
