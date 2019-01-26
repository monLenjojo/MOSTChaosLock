package com.example.xo337.mostchaoslock.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class BelongNewDevice {
    Context context;

    public BelongNewDevice(Context context) {
        this.context = context;
    }

    public  void addDevice(String pointKey, final String uid, final String deviceName){
        Query query = FirebaseDatabase.getInstance().getReference("deviceList");
        query.orderByChild("pointKey").equalTo(pointKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                        for (final DataSnapshot child : dataSnapshot.getChildren()) {
                            if (child.getValue(JavaBeanDevice.class).getOnRegistered().equals("false")) {
//                                Log.d("AddDevice", "onDataChange: "+child.getValue(JavaBeanDevice.class).getOnRegistered());
                            Map<String, Object> data = new HashMap();
                            data.put("ownerId", uid);
                            data.put("onRegistered", "true");
                            FirebaseDatabase.getInstance().getReference("deviceList").child(child.getKey()).updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()) {
                                        if (task.isSuccessful()) {
                                            FirebaseDatabase.getInstance().getReference("userList").child(uid).child("myDevice").push().setValue(new JavaBeanMyDevice(child.getKey(), deviceName)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isComplete()) {
                                                        if (task.isSuccessful()) {
                                                            //完成
                                                        } else {
                                                            Log.d("AddDevice", "add new device Error");
                                                        }
                                                    }

                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
