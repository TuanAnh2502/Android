package com.example.dembuocchan.KeHoach;

import java.io.Serializable;

public class KeHoach implements Serializable {
    private int id;
    private int resourceId;
    private String title;
    private String level;
    private String time;

    public KeHoach() {
    }

    public KeHoach(int id, int resourceId, String title, String level, String time) {
        this.id = id;
        this.resourceId = resourceId;
        this.title = title;
        this.level = level;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
