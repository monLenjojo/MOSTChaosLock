package com.example.xo337.mostchaoslock.firebase;

public class JavaBeanPassRecord {
    String name;
    String time;
    String url;

    public JavaBeanPassRecord() {
        super();
    }

    public JavaBeanPassRecord(String name, String time, String url) {
        this.name = name;
        this.time = time;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

