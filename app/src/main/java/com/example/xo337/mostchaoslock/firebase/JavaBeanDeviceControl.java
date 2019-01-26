package com.example.xo337.mostchaoslock.firebase;

public class JavaBeanDeviceControl {
    String deviceId;
    String lockState;

    public JavaBeanDeviceControl() {
        super();
    }

    public JavaBeanDeviceControl(String deviceId, String lockState) {
        this.deviceId = deviceId;
        this.lockState = lockState;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }
}
