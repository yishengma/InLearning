package com.inlearning.app.director.teacher;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.director.DirectorAppRuntime;

import java.util.ArrayList;
import java.util.List;

public class TeaAddCoursePresenter implements CourseItemView.ClickListener {
    private TeacherCourseEditView mCourseEditView;
    private Context mContext;
    private Dialog mSelectCourseDialog;
    private List<Course2> mCourse2s;

    public TeaAddCoursePresenter(Context context) {
        mContext = context;
        mCourseEditView = new TeacherCourseEditView(mContext);
        mCourseEditView.setClickListener(new TeacherCourseEditView.ClickListener() {
            @Override
            public void onClick() {
                mSelectCourseDialog.show();
            }
        });
        mCourse2s = new ArrayList<>();
        initDialog();
    }


    public View getEditView() {
        return mCourseEditView;
    }

    private void initDialog() {
        mSelectCourseDialog = new Dialog(mContext, R.style.SimpleDialog);//SimpleDialog
        mSelectCourseDialog.setContentView(R.layout.dialog_tea_edit_course_info);
        mSelectCourseDialog.setCanceledOnTouchOutside(true);
        final List<Course2> course2s = DirectorAppRuntime.getCourse2s();
        RecyclerView recyclerView = mSelectCourseDialog.findViewById(R.id.rv_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_simple_view, viewGroup,false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Course2 course2 = course2s.get(i);
                ((ViewHolder) viewHolder).contentView.setText(course2.getName());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseItemView courseItemView = new CourseItemView(mContext);
                        courseItemView.setCourse2(course2);
                        courseItemView.setListener(TeaAddCoursePresenter.this);
                        mCourseEditView.addView(courseItemView);
                        mCourse2s.add(course2);
                        mSelectCourseDialog.dismiss();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return course2s.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                TextView contentView;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    contentView = itemView.findViewById(R.id.tv_content);
                }
            }
        });
    }


    @Override
    public void onDelete(CourseItemView view, Course2 course2) {
        mCourseEditView.removeView(view);
        mCourse2s.remove(course2);
    }

    public List<Course2> getCourse2s() {
        return mCourse2s;
    }

    public void addCourse(Course2 course2) {
        CourseItemView courseItemView = new CourseItemView(mContext);
        courseItemView.setCourse2(course2);
        courseItemView.setListener(TeaAddCoursePresenter.this);
        mCourseEditView.addView(courseItemView);
        mCourse2s.add(course2);
    }
}
