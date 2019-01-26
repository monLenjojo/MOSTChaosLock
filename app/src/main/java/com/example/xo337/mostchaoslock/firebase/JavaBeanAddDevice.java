package com.example.xo337.mostchaoslock.firebase;

public class JavaBeanAddDevice {
    String ownerId;
    String onRegistered;

    public JavaBeanAddDevice() {
        super();
    }

    public JavaBeanAddDevice(String ownerId, String onRegistered) {
        this.ownerId = ownerId;
        this.onRegistered = onRegistered;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOnRegistered() {
        return onRegistered;
    }

    public void setOnRegistered(String onRegistered) {
        this.onRegistered = onRegistered;
    }
}
