package com.example.xo337.mostchaoslock.firebase;

public class JavaBeanDeviceKey {
    String deviceId;
    String chaosKey;
    String macAddress;

    public JavaBeanDeviceKey() {
        super();
    }

    public JavaBeanDeviceKey(String deviceId, String chaosKey, String macAddress) {
        this.deviceId = deviceId;
        this.chaosKey = chaosKey;
        this.macAddress = macAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getChaosKey() {
        return chaosKey;
    }

    public void setChaosKey(String chaosKey) {
        this.chaosKey = chaosKey;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
