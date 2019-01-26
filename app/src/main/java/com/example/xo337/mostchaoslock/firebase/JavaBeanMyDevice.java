package com.example.xo337.mostchaoslock.firebase;

public class JavaBeanMyDevice {
    String deviceId;
    String deviceName;

    public JavaBeanMyDevice() {
        super();
    }

    public JavaBeanMyDevice(String deviceId, String deviceName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
