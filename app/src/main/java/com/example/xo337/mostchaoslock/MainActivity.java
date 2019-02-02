package com.example.xo337.mostchaoslock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xo337.mostchaoslock.firebase.AddTestData;
import com.example.xo337.mostchaoslock.firebase.BelongNewDevice;
import com.example.xo337.mostchaoslock.recyclerDesign.RecyclerFunctionHomePage;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    private
    String checkBoxString;
    public static String firebaseUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                auth.signOut();
//                AddTestData testData = new AddTestData();
//                testData.add(10,testData.DEVICE_MODULE_LOCK,"null","null");
                addNewDevice();
            }
        });
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null) {
                    Intent pag = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(pag);
                    MainActivity.this.finish();
                }else {
                    firebaseUid = firebaseAuth.getUid();
                    RecyclerView recyclerView = findViewById(R.id.recyclerHomeView);
                    RecyclerFunctionHomePage view = new RecyclerFunctionHomePage(MainActivity.this,recyclerView,firebaseUid);
                }
            }
        });
    }
    CheckBox checkBox_Lock, checkBox_check, checkBox_camera;
    CheckBox.OnCheckedChangeListener checkBoxListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.addDeviceView_checkBox_Lock:
                    if (isChecked) {
                        checkBox_check.setChecked(false);
                        checkBox_camera.setChecked(false);
                    }
                    break;
                case R.id.addDeviceView_checkBox_check:
                    if (isChecked) {
                        checkBox_Lock.setChecked(false);
                        checkBox_camera.setChecked(false);
                    }
                    break;
                case R.id.addDeviceView_checkBox_camera:
                    if (isChecked) {
                        checkBox_Lock.setChecked(false);
                        checkBox_check.setChecked(false);
                    }
                    break;
                case R.id.addDeviceView_checkBox_Future:
                    break;
            }
            checkBoxString = buttonView.getText().toString();
            Log.e("CheckBox", "choose： " + checkBoxString);
        }
    };
    private void addNewDevice() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        final View alertDialogView = layoutInflater.inflate(R.layout.alertdialog_input_product, null);
        final EditText newDeviceKey = alertDialogView.findViewById(R.id.addDeviceView_ed_deviceKey);
        final EditText newDeviceName = alertDialogView.findViewById(R.id.addDeviceView_ed_deviceName);
        checkBox_Lock = alertDialogView.findViewById(R.id.addDeviceView_checkBox_Lock);
        checkBox_check = alertDialogView.findViewById(R.id.addDeviceView_checkBox_check);
        checkBox_camera = alertDialogView.findViewById(R.id.addDeviceView_checkBox_camera);
        checkBox_Lock.setOnCheckedChangeListener(checkBoxListener);
        checkBox_check.setOnCheckedChangeListener(checkBoxListener);
        checkBox_camera.setOnCheckedChangeListener(checkBoxListener);
        alertDialog.setView(alertDialogView).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String deviceKey = newDeviceKey.getText().toString();
                final String deviceName = newDeviceName.getText().toString();
                if (TextUtils.isEmpty(deviceKey)) {
                    Toast.makeText(MainActivity.this, "Device key不可為空呦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(deviceName)) {
                    Toast.makeText(MainActivity.this, "幫您的設備取個名子吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(checkBoxString)) {
                    Toast.makeText(MainActivity.this, "要記得選擇產品呦", Toast.LENGTH_SHORT).show();
                    return;
                }
                new BelongNewDevice(MainActivity.this).addDevice(deviceKey,firebaseUid,deviceName);
//                new AddDevice(new JavaBeanSetDevice(deviceName,deviceKey,checkBoxString),firebaseUid,MainActivity.this);
            }
        }).setNegativeButton("cancel", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_account:
                Intent pageAccount = new Intent(this,UserInformationActivity.class);
                startActivity(pageAccount);
                return true;
            case R.id.action_logout:
                auth.signOut();
                firebaseUid = null;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
