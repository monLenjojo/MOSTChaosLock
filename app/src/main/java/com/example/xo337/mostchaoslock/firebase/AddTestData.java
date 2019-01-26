package com.example.xo337.mostchaoslock.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddTestData {
    public String DEVICE_MODULE_LOCK = "1";
    public void add(int num, String module, final String key, final String macAddress) {
        for (int i = 0; i < num; i++) {
            FirebaseDatabase.getInstance().getReference("deviceList").push().setValue(new JavaBeanDevice("null",FirebaseDatabase.getInstance().getReference().push().getKey(), module, "false"));
        }
        DatabaseReference deviceList = FirebaseDatabase.getInstance().getReference("deviceList");
        deviceList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    JavaBeanDeviceKey deviceKey = new JavaBeanDeviceKey(child.getKey(),key,macAddress);
                    JavaBeanDeviceControl deviceControl = new JavaBeanDeviceControl(child.getKey(),"off");
                    FirebaseDatabase.getInstance().getReference("deviceKey").child(child.getKey()).setValue(deviceKey);
                    FirebaseDatabase.getInstance().getReference("deviceControl").child(child.getKey()).setValue(deviceControl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
