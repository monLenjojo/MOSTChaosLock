package com.example.xo337.mostchaoslock.firebase;

public class JavaBeanDevice {
    String ownerId;
    String pointKey;
    String module;
    String onRegistered;

    public JavaBeanDevice() {
        super();
    }

    public JavaBeanDevice(String ownerId, String pointKey, String module, String onRegistered) {
        this.ownerId = ownerId;
        this.pointKey = pointKey;
        this.module = module;
        this.onRegistered = onRegistered;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPointKey() {
        return pointKey;
    }

    public void setPointKey(String pointKey) {
        this.pointKey = pointKey;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOnRegistered() {
        return onRegistered;
    }

    public void setOnRegistered(String onRegistered) {
        this.onRegistered = onRegistered;
    }
}
