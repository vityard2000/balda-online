package com.tems.baldaonline.domain;

import android.graphics.Color;

public class Mascot {
    private final int id;
    private int color;
    private final Outfit outfit;
    public Mascot(int id, int color, Outfit outfit) {
        this.color = color;
        this.outfit = outfit;
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public String getStringColor(){
        return "#" + Integer.toHexString(color);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Outfit getOutfit() {
        return outfit;
    }

}
