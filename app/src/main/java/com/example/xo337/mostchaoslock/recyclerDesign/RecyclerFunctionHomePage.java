package com.example.xo337.mostchaoslock.recyclerDesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.xo337.mostchaoslock.firebase.JavaBeanMyDevice;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerFunctionHomePage {
    Context context;
    RecyclerView recyclerView;
    String firebaseId;
    DatabaseReference dataRef;
    ArrayList<JavaBeanMyDevice> arrayList = new ArrayList<JavaBeanMyDevice>();
    public RecyclerFunctionHomePage(Context context, RecyclerView recyclerView, String firebaseId) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.firebaseId = firebaseId;
        dataRef = FirebaseDatabase.getInstance().getReference("userList").child(firebaseId).child("myDevice");
        dataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("Recycler",dataSnapshot.getValue().toString());
                JavaBeanMyDevice data = dataSnapshot.getValue(JavaBeanMyDevice.class);
                Log.d("Recycler",data.getDeviceName());
                arrayList.add(data);
                upData();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void upData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerAdapterHomePage adapter = new RecyclerAdapterHomePage(context,arrayList,firebaseId);
        recyclerView.setAdapter(adapter);
    }
}
