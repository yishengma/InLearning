package com.inlearning.app.director.speciality;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.BaseFragment;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.util.LoadingDialog;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.director.person.coursemanager.classes.ClassCourseModel;
import com.inlearning.app.director.speciality.classinfo.ClassInfoActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SpecialityInfoFragment extends BaseFragment {
    private RecyclerView mRvSpecialityClass;
    private List<ClassInfo> mClassList = new ArrayList<>();
    private SpecialityInfoAdapter mSpecialityInfoAdapter;
    private TextView mEmptyView;
    private Speciality mSpeciality;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_director_speciality_info, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView(View view) {
        mRvSpecialityClass = view.findViewById(R.id.rv_content);
        mRvSpecialityClass.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mSpecialityInfoAdapter = new SpecialityInfoAdapter(mClassList);
        mRvSpecialityClass.setAdapter(mSpecialityInfoAdapter);
        mSpecialityInfoAdapter.setClickListener(new SpecialityInfoAdapter.ClickListener() {
            @Override
            public void onClick(ClassInfo classInfo) {
                ClassInfoActivity.startActivity(getContext(), classInfo);
            }

            @Override
            public void onLongClick(View view, float x, float y, ClassInfo classInfo) {
                showPopWindow(view, x, y, classInfo);
            }
        });
        mEmptyView = view.findViewById(R.id.tv_empty);
    }

    public SpecialityInfoFragment setSpeciality(Speciality speciality) {
        if (speciality == null) {
            return this;
        }
        mSpeciality = speciality;
        initData();
        return this;
    }

    private PopupWindow mPopupWindow;

    private void showPopWindow(final View view, float x, float y, final ClassInfo classInfo) {
        //获取PopWindow宽和高
        mPopupWindow = new PopupWindow(getLayoutInflater().inflate(R.layout.view_menu_window, null), 300, 100, true);
        mPopupWindow.setOutsideTouchable(true);//设置外部可点击取消
        mPopupWindow.setElevation(5);//设置阴影
        view.setBackgroundColor(getResources().getColor(R.color.app_global_simple_gray));
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int xoff = (int) x;
        int yoff = 0 - (view.getHeight() - (int) y) - mPopupWindow.getHeight() - 50;
        if (x > width / 2) {
            xoff = (int) x - width / 2 + 10;
            mPopupWindow.setAnimationStyle(R.style.AnimationRight);
        } else {
            mPopupWindow.setAnimationStyle(R.style.AnimationLeft);
        }
        mPopupWindow.showAsDropDown(view, xoff, yoff, Gravity.CENTER);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                view.setBackgroundResource(R.drawable.bg_item_click);
            }
        });
        TextView msgView = mPopupWindow.getContentView().findViewById(R.id.tv_menu_msg);
        msgView.setText("删除该班级");
        msgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(classInfo);
                mPopupWindow.dismiss();
            }
        });
    }

    private void showDialog(final ClassInfo classInfo) {
        final Dialog dialog = new Dialog(getContext(), R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该班级信息？删除之后不可恢复！");
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
                deleteClass(classInfo);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteClass(final ClassInfo classInfo) {
        LoadingDialog.showLoadingDialog(getContext(), "正在删除");
        SpecialityModel.deleteClass(classInfo, new SpecialityModel.Callback<ClassInfo>() {
            @Override
            public void onResult(boolean suc, ClassInfo c) {
                LoadingDialog.closeDialog();
                if (suc) {
                    showToast("删除成功");
                    updateList(classInfo);
                }
            }
        });
    }

    private void showToast(final String msg) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateList(final ClassInfo classInfo) {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                Iterator<ClassInfo> iterator = mClassList.iterator();
                while (iterator.hasNext()) {
                    ClassInfo classInfo1 = iterator.next();
                    if (classInfo1 == null) {
                        continue;
                    }
                    if (classInfo1.getObjectId().equals(classInfo.getObjectId())) {
                        iterator.remove();
                        break;
                    }
                }
                mSpecialityInfoAdapter.notifyDataSetChanged();
                mEmptyView.setVisibility(mClassList.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void initData() {
        if (mSpeciality == null) {
            return;
        }
        ClassCourseModel.getClassInfo(mSpeciality, new ClassCourseModel.Callback<List<ClassInfo>>() {
            @Override
            public void onResult(boolean suc, List<ClassInfo> classInfos) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mClassList.clear();
                        for (ClassInfo classInfo : classInfos) {
                            mClassList.add(classInfo.setType(ClassInfo.ITEM_CLASS_INFO));
                        }
                        if (mSpecialityInfoAdapter != null) {
                            mSpecialityInfoAdapter.notifyDataSetChanged();
                        }
                        if (mEmptyView != null) {
                            mEmptyView.setVisibility(mClassList.isEmpty() ? View.VISIBLE : View.GONE);
                        }
                    }
                });
            }
        });
    }


}
