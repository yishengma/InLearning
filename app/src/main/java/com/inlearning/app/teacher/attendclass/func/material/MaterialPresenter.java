package com.inlearning.app.teacher.attendclass.func.material;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Materials;
import com.inlearning.app.common.util.FileUtil;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.teacher.attendclass.func.video.VideoUploadMgr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

import static com.inlearning.app.director.BaseExcelImportActivity.EXCEL_OPEN_REQUEST;

public class MaterialPresenter {

    private MaterialView mMaterialView;
    private MaterialTbsView mMaterialTbsView;
    private MaterialAdapter mMaterialAdapter;
    private Activity mActivity;
    private List<Materials> mMaterials;
    private CourseChapter mChapter;
    private Map<Materials, String> mMaterialPathMap;

    public MaterialPresenter(Activity activity, CourseChapter chapter) {
        mActivity = activity;
        mChapter = chapter;
        mMaterials = new ArrayList<>();
        mMaterialView = mActivity.findViewById(R.id.view_material_func);
        mMaterialTbsView = mActivity.findViewById(R.id.view_material_tbs_view);
        mMaterialAdapter = new MaterialAdapter(mMaterials);
        mMaterialAdapter.setClickListener(new MaterialAdapter.ClickListener() {
            @Override
            public void onClick(Materials materials) {
                mMaterialView.setVisibility(View.GONE);
                mMaterialTbsView.setVisibility(View.VISIBLE);
                mMaterialTbsView.openFile(materials.getMaterialName(),materials.getMaterialFile().getFileUrl());
            }

            @Override
            public void onUpload(final Materials materials) {
                ThreadMgr.getInstance().postToSubThread(new Runnable() {
                    @Override
                    public void run() {
                        uploadFile(mChapter, materials, mMaterialPathMap.get(materials));
                    }
                });
            }

            @Override
            public void onDelete(Materials materials) {
                showDialog(materials);
            }
        });
        mMaterialTbsView.setClickListener(new MaterialTbsView.ClickListener() {
            @Override
            public void onBack() {
                mMaterialView.setVisibility(View.VISIBLE);
                mMaterialTbsView.setVisibility(View.GONE);

            }
        });
        mMaterialView.setClickListener(new MaterialView.ClickListener() {
            @Override
            public void onBack() {
                mActivity.finish();
            }

            @Override
            public void onAdd() {
                pickMaterial();
            }
        });
        mMaterialView.getMaterialView().setAdapter(mMaterialAdapter);
        mMaterialView.refreshMaterialView(false);
        mMaterialPathMap = new HashMap<>();
        initData();
    }

    private void initData() {
        MaterialModel.getMaterialByChapter(mChapter, new MaterialModel.Callback<List<Materials>>() {
            @Override
            public void onResult(final List<Materials> materials) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mMaterials.addAll(materials);
                        mMaterialAdapter.notifyDataSetChanged();
                        mMaterialView.refreshMaterialView(materials.size() != 0);
                    }
                });
            }
        });
    }

    private void pickMaterial() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*.icon_material_pdf");
        intent.setType("*/*.icon_material_ppt");
        intent.setType("*/*.pptx");
        intent.setType("*/*.icon_material_doc");
        intent.setType("*/*.docx");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        mActivity.startActivityForResult(intent, EXCEL_OPEN_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = data.getData();
        if (uri == null) {
            return;
        }
        String path = FileUtil.getChooseFileResultPath(mActivity, uri);
        final Materials materials = new Materials();
        materials.setChapter(mChapter);
        materials.setMaterialName(new File(path).getName());
        materials.setType(Materials.getType(path));
        mMaterials.add(materials);
        mMaterialAdapter.notifyDataSetChanged();
        mMaterialView.refreshMaterialView(mMaterials.size() != 0);
        mMaterialPathMap.put(materials, path);
    }

    private void uploadFile(final CourseChapter chapter, final Materials materials, String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                materials.setMaterialFile(bmobFile);
                MaterialModel.addMaterial(chapter, materials, new MaterialModel.Callback<Materials>() {
                    @Override
                    public void onResult(Materials materials) {
                        updateMaterial();
                    }
                });
            }

            @Override
            public void onProgress(Integer value) {

            }
        });
    }

    private void updateMaterial() {
        ThreadMgr.getInstance().postToUIThread(new Runnable() {
            @Override
            public void run() {
                if (mActivity == null) {
                    return;
                }
                Toast.makeText(mActivity, "上传成功", Toast.LENGTH_SHORT).show();
                mMaterialAdapter.notifyDataSetChanged();
                mMaterialView.refreshMaterialView(mMaterials.size() != 0);
            }
        });
    }

    public void showMaterialView() {
        mMaterialView.setVisibility(View.VISIBLE);
    }

    private void showDialog(final Materials materials) {
        final Dialog dialog = new Dialog(mActivity, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该资料");
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
                deleteMaterial(materials);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteMaterial(final Materials materials) {
        if (materials.getMaterialFile() == null) {
            mMaterials.remove(materials);
            mMaterialAdapter.notifyDataSetChanged();
            mMaterialView.refreshMaterialView(mMaterials.size() != 0);
            return;
        }
        MaterialModel.deleteMaterial(materials, new MaterialModel.Callback<Boolean>() {
            @Override
            public void onResult(Boolean aBoolean) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mMaterials.remove(materials);
                        mMaterialAdapter.notifyDataSetChanged();
                        mMaterialView.refreshMaterialView(mMaterials.size() != 0);
                    }
                });
            }
        });
    }

    public void onDestory() {
        mMaterialTbsView.onDestroy();
    }
}
