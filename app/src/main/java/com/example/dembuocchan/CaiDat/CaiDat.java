package com.example.dembuocchan.CaiDat;

import android.view.View;

public class CaiDat {
    private String title;
    private int icon;
    private View.OnClickListener clickListener;

    public CaiDat(String title, int icon, View.OnClickListener clickListener) {
        this.title = title;
        this.icon = icon;
        this.clickListener = clickListener;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }
}
