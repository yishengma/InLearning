package com.inlearning.app.director.teacher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Course2;

public class CourseItemView extends FrameLayout implements View.OnClickListener {


    public CourseItemView(Context context) {
        this(context, null);
    }

    public CourseItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface ClickListener {
        void onDelete(CourseItemView view, Course2 course2);
    }

    public void setListener(ClickListener listener) {
        mListener = listener;
    }

    private ClickListener mListener;
    private TextView mNameView;
    private ImageView mDeleteView;
    private Course2 mCourse2;

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tea_edit_course_item, this);
        mNameView = view.findViewById(R.id.tv_course_name);
        mDeleteView = view.findViewById(R.id.imv_delete);
        mDeleteView.setOnClickListener(this);
    }


    public void setNameText(String msg) {
        mNameView.setText(msg);
    }


    public void setCourse2(Course2 course2) {
        mCourse2 = course2;
        mNameView.setText(course2.getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_delete:
                if (mListener != null) {
                    mListener.onDelete(this, mCourse2);
                }
                break;
        }
    }
}
