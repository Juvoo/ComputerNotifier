package com.kinghouser.util;

import java.io.IOException;

public class Notification {

    private String title;
    private String content;

    public Notification(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void show() {
        try {
            Runtime.getRuntime().exec(new String[] { "osascript", "-e", "display notification " + content + " with title " + title });
        } catch (IOException ignored) {}
    }
}
