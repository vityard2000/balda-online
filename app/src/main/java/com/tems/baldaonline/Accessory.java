package com.tems.baldaonline;

public class Accessory {
    private int res;
    private int z_index;
    private int resPreview;

    public Accessory(int res, int resPreview, int z_index) {
        this.res = res;
        this.z_index = z_index;
        this.resPreview = resPreview;
    }

    public void setResPreview(int resPreview) {
        this.resPreview = resPreview;
    }

    public int getResPreview() {
        return resPreview;
    }

    public int getRes() {
        return res;
    }

    public int getZ_index() {
        return z_index;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public void setZ_index(int z_index) {
        this.z_index = z_index;
    }
}
