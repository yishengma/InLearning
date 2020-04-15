package com.inlearning.app.director.person.coursemanager.speciality;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.bean.SpecialitySchedule;
import com.inlearning.app.common.util.ToastUtil;
import com.inlearning.app.director.course.CourseInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class FixCourseInfoFragment extends BaseFragment {
    private RecyclerView mRvCourseInfo;
    private List<Course2> mCourseList;
    private CourseInfoAdapter mCourseInfoAdapter;
    private String mFragmentTitle;
    private Speciality mSpeciality;
    private TextView mEmptyView;
    private List<Course2> mCacheCourse2s;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_director_course_info, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCacheCourse2s != null) {
            setCourseList(mCacheCourse2s);
        }
    }

    private void initView(View view) {
        mRvCourseInfo = view.findViewById(R.id.rv_content);
        mRvCourseInfo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if (mCourseList == null) {
            mCourseList = new ArrayList<>();
        }
        mCourseInfoAdapter = new CourseInfoAdapter(mCourseList);
        mRvCourseInfo.setAdapter(mCourseInfoAdapter);
        mCourseInfoAdapter.setClickListener(new CourseInfoAdapter.ClickListener() {
            @Override
            public void onClick(Course2 course) {
                showDialog(course);
            }

            @Override
            public void onLongClick(View view, float x, float y, Course2 course) {

            }
        });
        mEmptyView = view.findViewById(R.id.tv_empty);
    }

    public FixCourseInfoFragment setFragmentTitle(String fragmentTitle) {
        mFragmentTitle = fragmentTitle;
        return this;
    }

    public void setCourseList(List<Course2> courseList) {
        if (mCourseList == null) {
            mCourseList = new ArrayList<>();
        }
        mCourseList.clear();
        for (Course2 c : courseList) {
            if (mFragmentTitle.contains(c.getType())) {
                mCourseList.add(c);
            }
        }
        mCacheCourse2s = courseList;
        if (mCourseInfoAdapter != null) {
            mCourseInfoAdapter.notifyDataSetChanged();
        }
        if (mEmptyView != null) {
            mEmptyView.setVisibility(mCourseList.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }


    public void setSpeciality(Speciality speciality) {
        mSpeciality = speciality;
    }


    private void showDialog(final Course2 course2) {
        final Dialog dialog = new Dialog(getContext(), R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("排课");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText(String.format("确定将%s排课到%s专业", course2.getName(), mSpeciality.getShortName()));
        TextView cancelView = dialog.findViewById(R.id.tv_cancel);
        TextView confirmView = dialog.findViewById(R.id.tv_confirm);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSpeciality(course2);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void addSpeciality(Course2 course2) {
        SpecialitySchedule specialitySchedule = new SpecialitySchedule();
        specialitySchedule.setCourse2(course2);
        Speciality speciality = new Speciality();
        speciality.setObjectId(mSpeciality.getObjectId());
        specialitySchedule.setSpeciality(speciality);
        SpecialityScheduleModel.addSpecialitySchedule(specialitySchedule, new SpecialityScheduleModel.Callback<SpecialitySchedule>() {
            @Override
            public void onResult(SpecialitySchedule schedule) {
                if (schedule !=null && getActivity() != null) {
                    getActivity().finish();
                }
                ToastUtil.showToast("该排课已存在", Toast.LENGTH_SHORT);
            }
        });
    }
}
