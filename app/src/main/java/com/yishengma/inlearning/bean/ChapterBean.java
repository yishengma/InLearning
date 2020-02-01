package com.yishengma.inlearning.bean;

public class ChapterBean {
    public static final int ITEM_TYPE_CHAPTER = 0;
    public static final int ITEM_TYPE_LOAD = 1;
    private String mTime;
    private String mName;
    private String mMaterialUrl;
    private String mVideoUrl;
    private String mDiscussZone;
    private String mVideoDuration;
    private int mItemType;

    public ChapterBean() {
        mItemType  = ITEM_TYPE_CHAPTER;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getName() {
        return mName;
    }

    public ChapterBean setName(String name) {
        mName = name;
        return this;
    }

    public String getMaterialUrl() {
        return mMaterialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        mMaterialUrl = materialUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public String getDiscussZone() {
        return mDiscussZone;
    }

    public void setDiscussZone(String discussZone) {
        mDiscussZone = discussZone;
    }

    public String getVideoDuration() {
        return mVideoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.mVideoDuration = videoDuration;
    }

    public int getItemType() {
        return mItemType;
    }

    public ChapterBean setItemType(int type) {
        this.mItemType = type;
        return this;
    }
}
