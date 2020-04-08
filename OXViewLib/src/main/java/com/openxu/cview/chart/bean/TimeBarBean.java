package com.openxu.cview.chart.bean;

import android.graphics.RectF;
import android.graphics.Region;

public class TimeBarBean {

    private int num;
    private String name;

    //bar绘制矩形
    private RectF arcRect;
    //触摸相关
    private Region region;     //扇形区域--用于判断手指触摸点是否在此范围

    @Override
    public String toString() {
        return "BarBean{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }

    public TimeBarBean() {
    }

    public TimeBarBean(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public float getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RectF getArcRect() {
        return arcRect;
    }

    public void setArcRect(RectF arcRect) {
        this.arcRect = arcRect;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getText() {
        if (num < 60) {
            return num  + "秒";
        }
        if (num < 360) {
            return num / 60 + "分钟";
        }
        return num / 360 + "小时";
    }

}
