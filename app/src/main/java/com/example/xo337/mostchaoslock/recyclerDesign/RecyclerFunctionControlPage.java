package com.example.xo337.mostchaoslock.recyclerDesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.example.xo337.mostchaoslock.firebase.JavaBeanPassRecord;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerFunctionControlPage {

    Context context;
    RecyclerView recyclerView;
    String firebaseId,deviceId;
    ImageView imageView;
    DatabaseReference dataRef;
    ArrayList<JavaBeanPassRecord> arrayList = new ArrayList<JavaBeanPassRecord>();

    public RecyclerFunctionControlPage(Context context, RecyclerView recyclerView, String firebaseId, String deviceId, ImageView imageView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.firebaseId = firebaseId;
        this.deviceId = deviceId;
        this.imageView = imageView;
        dataRef = FirebaseDatabase.getInstance().getReference("passRecord").child(deviceId);
        dataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("recycler",dataSnapshot.getValue().toString());
                JavaBeanPassRecord data = dataSnapshot.getValue(JavaBeanPassRecord.class);
                Log.d("recycler",data.getName());
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

    private void upData(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerAdapterControlPage adapter = new RecyclerAdapterControlPage(context,arrayList,firebaseId,imageView);
        recyclerView.setAdapter(adapter);
    }
}
