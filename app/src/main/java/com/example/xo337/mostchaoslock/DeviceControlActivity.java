package com.example.xo337.mostchaoslock;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xo337.mostchaoslock.chaosThing.ChaosWithBluetooth;
import com.example.xo337.mostchaoslock.firebase.JavaBeanDevice;
import com.example.xo337.mostchaoslock.firebase.JavaBeanMyDevice;
import com.example.xo337.mostchaoslock.firebase.JavaBeanPassRecord;
import com.example.xo337.mostchaoslock.netWorkCheck.NetWorkCheck;
import com.example.xo337.mostchaoslock.recyclerDesign.RecyclerFunctionControlPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;

public class DeviceControlActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
            case R.id.navigation_share:
                    //Toast.makeText(DeviceControlActivity.this, "1", Toast.LENGTH_SHORT).show();
                Intent i = new Intent("la.droid.qr.scan");    //使用QRDroid的掃描功能
                i.putExtra("la.droid.qr.complete", true);   //完整回傳，不截掉scheme
                try {
                    //開啟 QRDroid App 的掃描功能，等待 call back onActivityResult()
                    startActivityForResult(i, 0);
                } catch (ActivityNotFoundException ex) {
                    //若沒安裝 QRDroid，則開啟 Google Play商店，並顯示 QRDroid App
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=la.droid.qr"));
                    startActivity(intent);
                }
                return true;
            case R.id.navigation_key:
                android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(DeviceControlActivity.this);
                alertDialog.setMessage("開始進行解鎖").show();
                new NetWorkCheck(DeviceControlActivity.this).getNetWorkCheck();

                    chaosWithBluetooth.unlock(deviceId);
//                    SimpleDateFormat tineFormat = new SimpleDateFormat("yyyy/MM/dd");
//                    String date = tineFormat.format(new Date().getTime());
//                    FirebaseDatabase.getInstance().getReference("passRecord").child(deviceId).push().setValue(new JavaBeanPassRecord("name",date,"https://firebasestorage.googleapis.com/v0/b/mostchaoslock.appspot.com/o/testImage.jpg?alt=media&token=a7a6ab56-6595-4ebe-bef8-ed80c3052edc"));

                    try {}catch (Exception e){
                    alertDialog.setMessage("請等待片刻後，再重新嘗試").show();
                }
                return true;
            }
            return false;
        }
    };
    private String deviceName, deviceId,devicePath;
    private boolean checkRepeat;
    private ChaosWithBluetooth chaosWithBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        Intent intent = getIntent();
        deviceId = intent.getStringExtra("DEVICE_ID");
        deviceName = intent.getStringExtra("DEVICE_NAME");
        devicePath = intent.getStringExtra("DEVICE_PATH");
        if (deviceId.isEmpty() && deviceName.isEmpty()) {
            Toast.makeText(this, "happening error", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        RecyclerView recyclerView = findViewById(R.id.controlRecyclerView);
        RecyclerFunctionControlPage recyclerFunctionControlPage = new RecyclerFunctionControlPage(this,recyclerView,MainActivity.firebaseUid,deviceId);
        TextView name = findViewById(R.id.controlText_deviceName);
        name.setText(deviceName);
        final TextView module = findViewById(R.id.controlText_deviceModule);
        FirebaseDatabase.getInstance().getReference("deviceList").child(deviceId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                JavaBeanDevice data = dataSnapshot.getValue(JavaBeanDevice.class);
                switch (data.getModule()) {
                    case "1":
                        module.setText("This device is Lock");
                        return;
                    case "2":
                        module.setText("This device is camera");
                        return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        chaosWithBluetooth = new ChaosWithBluetooth(this);
        chaosWithBluetooth.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (0 == requestCode && null != data && data.getExtras() != null) {
            checkRepeat = false;
            final String result = data.getExtras().getString("la.droid.qr.result");
            if (result.contains(".") && result.contains("#") && result.contains("#") && result.contains("$") && result.contains("[") && result.contains("]")) {
                final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("userList").child(result);
                dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            dataRef.child("myDevice").push().setValue(new JavaBeanMyDevice(deviceId, deviceName)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()) {
                                        if (task.isSuccessful()) {
                                            new AlertDialog.Builder(DeviceControlActivity.this).setMessage("分享成功").show();
                                        } else {
                                            new AlertDialog.Builder(DeviceControlActivity.this).setMessage("分享失敗").show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            } else {
                new AlertDialog.Builder(DeviceControlActivity.this).setMessage("Error, \n請掃描正確的QR Code圖像").show();
            }
        }
    }
}






