package com.inlearning.app.teacher.attendclass.func.material;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Materials;

import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {
    private List<Materials> mMaterials;

    public MaterialAdapter(List<Materials> materials) {
        mMaterials = materials;
    }

    public interface ClickListener {
        void onClick(Materials materials);

        void onUpload(Materials materials);

        void onDelete(Materials materials);
    }

    private ClickListener mClickListener;

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_material_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Materials materials = mMaterials.get(i);
        viewHolder.setIconView(Materials.getType(materials.getMaterialName()));
        viewHolder.mNameView.setText(materials.getMaterialName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick(materials);
                }
            }
        });
        if (materials.getMaterialFile() == null || TextUtils.isEmpty(materials.getMaterialFile().getFileUrl())) {
            viewHolder.mUploadView.setText("上传");
            viewHolder.mUploadView.setEnabled(true);
        } else {
            viewHolder.mUploadView.setText("已上传");
            viewHolder.mUploadView.setEnabled(false);
        }
        viewHolder.mUploadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onUpload(materials);
                }
            }
        });
        viewHolder.mDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onDelete(materials);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMaterials.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIconView;
        TextView mNameView;
        TextView mUploadView;
        TextView mDeleteView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIconView = itemView.findViewById(R.id.imv_material_icon);
            mNameView = itemView.findViewById(R.id.tv_material_name);
            mUploadView = itemView.findViewById(R.id.tv_material_upload);
            mDeleteView = itemView.findViewById(R.id.tv_material_delete);
        }

        public void setIconView(@Materials.Type int type) {
            switch (type) {
                case Materials.Type.DOC:
                    mIconView.setImageResource(R.drawable.icon_material_doc);
                    break;
                case Materials.Type.PPT:
                    mIconView.setImageResource(R.drawable.icon_material_ppt);
                    break;
                case Materials.Type.PDF:
                    mIconView.setImageResource(R.drawable.icon_material_pdf);
                    break;
            }
        }
    }
}
