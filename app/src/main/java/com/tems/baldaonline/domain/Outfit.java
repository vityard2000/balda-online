package com.tems.baldaonline.domain;

import androidx.annotation.DrawableRes;

public class Outfit {
    private @DrawableRes int zIndex0;
    private @DrawableRes int zIndex1;
    private @DrawableRes int zIndex2;
    private @DrawableRes int zIndex3;

    public Outfit() {
    }

    public int getzIndex1() {
        return zIndex1;
    }

    public void setzIndex1(int zIndex1) {
        this.zIndex1 = zIndex1;
    }

    public int getzIndex2() {
        return zIndex2;
    }

    public void setzIndex2(int zIndex2) {
        this.zIndex2 = zIndex2;
    }

    public int getzIndex3() {
        return zIndex3;
    }

    public void setzIndex3(int zIndex3) {
        this.zIndex3 = zIndex3;
    }

    public int getzIndex0() {
        return zIndex0;
    }

    public void setzIndex0(int zIndex0) {
        this.zIndex0 = zIndex0;
    }
}
