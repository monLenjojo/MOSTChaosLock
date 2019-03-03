package com.example.xo337.mostchaoslock.recyclerDesign;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.xo337.mostchaoslock.DeviceControlActivity;
import com.example.xo337.mostchaoslock.R;
import com.example.xo337.mostchaoslock.firebase.JavaBeanMyDevice;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

public class RecyclerAdapterHomePage extends RecyclerView.Adapter<RecyclerAdapterHomePage.RecyclerViewHolder>{
    private DatabaseReference doorListener;
    private String doorName;
    Context context;
    ArrayList<JavaBeanMyDevice> arrayList;
    String firebaseUid;

    public RecyclerAdapterHomePage(Context context, ArrayList arrayList, String firebaseUid) {
        this.context = context;
        this.arrayList = arrayList;
        this.firebaseUid = firebaseUid;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_view,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, final int i) {
        Log.d("recyclerViewHolder", "i=" +i);
        recyclerViewHolder.recyclerButton.setText(arrayList.get(i).getDeviceName());
        recyclerViewHolder.recyclerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page = new Intent(context, DeviceControlActivity.class);
                page.putExtra("DEVICE_NAME",arrayList.get(i).getDeviceName());
                page.putExtra("DEVICE_ID",arrayList.get(i).getDeviceId());
                context.startActivity(page);
            }
        });
        doorName = arrayList.get(i).getDeviceName();
        doorListener = FirebaseDatabase.getInstance().getReference("deviceControl").child(arrayList.get(i).getDeviceId());
        doorListener.addChildEventListener(listener);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        Button recyclerButton;
        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            recyclerButton = itemView.findViewById(R.id.recyclerButton);
        }
    }

    ChildEventListener listener = new ChildEventListener(){
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//            onClickthis(doorName);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            onClickthis(doorName);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            doorListener.removeEventListener(listener);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    private void onClickthis(String doorName) {
        String id = "MOSTChaosLock";
        String name = "WarningChannel";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            Log.i("TAG", mChannel.toString());
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(context)
                    .setChannelId(id)
                    .setContentTitle("Door warning")
                    .setContentText(doorName + " is open")
                    .setSmallIcon(R.mipmap.ic_launcher).build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle("Door warning")
                    .setContentText(doorName + " is open")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setOngoing(true)
                    .setChannelId(id);//无效
            notification = notificationBuilder.build();
        }
        notificationManager.notify(111123, notification);
    }
}
