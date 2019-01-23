package com.example.xo337.mostchaoslock.userInformation;

public class JavaBeanSetPerson {
    String name;
    String phone;
    String email;

    public JavaBeanSetPerson() {
        super();
    }

    public JavaBeanSetPerson(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
