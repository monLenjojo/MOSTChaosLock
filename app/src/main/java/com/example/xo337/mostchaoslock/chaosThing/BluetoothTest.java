package com.example.xo337.mostchaoslock.chaosThing;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothTest {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter adapter;
    BluetoothDevice device;
    BluetoothSocket socket;
    InputStream read;
    OutputStream write;

    public BluetoothTest() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        socket = null;
        adapter.startDiscovery();
    }

    public boolean connect(Context context, String Mac) {
        device = adapter.getRemoteDevice(Mac);
        int sdk = Integer.parseInt(Build.VERSION.SDK);
        try {
            if (sdk >= 10) {
                socket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            } else {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            }
            socket.connect();
            read = socket.getInputStream();
            write = socket.getOutputStream();
            return true;
        } catch (IOException e) {
            e.printStackTrace();

//            Toast.makeText(context, "連線失敗", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    String log = "test", strSet = "0";

    public void send(double val) {
//        int sendVal = Float.floatToIntBits(val);
        long sendVal = Double.doubleToLongBits(val);
        Log.i(log, "sendVal = " + sendVal);
        int f1 = Integer.parseInt(strSet), f2 = Integer.parseInt(strSet), f3 = Integer.parseInt(strSet),
                f4 = Integer.parseInt(strSet), f5 = Integer.parseInt(strSet), f6 = Integer.parseInt(strSet),
                f7 = Integer.parseInt(strSet), f8 = Integer.parseInt(strSet), f9 = Integer.parseInt(strSet),
                f10 = Integer.parseInt(strSet), f11 = Integer.parseInt(strSet);
//        byte us[] = new byte[11];
//        us[0] = (byte) ((sendVal & 0xe0000000) >>> 29);
//        f1 = us[0];
//        us[1] = (byte) ((sendVal & 0x1c000000) >>> 26);
//        f2 = us[1];
//        us[2] = (byte) ((sendVal & 0x03800000) >>> 23);
//        f3 = us[2];
//        us[3] = (byte) ((sendVal & 0x00700000) >>> 20);
//        f4 = us[3];
//        us[4] = (byte) ((sendVal & 0x000e0000) >>> 17);
//        f5 = us[4];
//        us[5] = (byte) ((sendVal & 0x0001c000) >>> 14);
//        f6 = us[5];
//        us[6] = (byte) ((sendVal & 0x00003800) >>> 11);
//        f7 = us[6];
//        us[7] = (byte) ((sendVal & 0x00000700) >>> 8);
//        f8 = us[7];
//        us[8] = (byte) ((sendVal & 0x000000e0) >>> 5);
//        f9 = us[8];
//        us[9] = (byte) ((sendVal & 0x0000001c) >>> 2);
//        f10 = us[9];
//        us[10] = (byte) ((sendVal & 0x00000003));
//        f11 = us[10];
        byte us[] = new byte[8];
        us[0] = (byte) ((sendVal >>> 56) & 0xFF);
        f1 = us[0];
        us[1] = (byte) ((sendVal >>> 48) & 0x00FF);
        f2 = us[1];
        us[2] = (byte) ((sendVal >>> 40) & 0x0000FF);
        f3 = us[2];
        us[3] = (byte) ((sendVal >>> 32) & 0x000000FF) ;
        f4 = us[3];
        us[4] = (byte) ((sendVal & 0xFF000000) >>> 24);
        f5 = us[4];
        us[5] = (byte) ((sendVal & 0xFF0000) >>> 16);
        f6 = us[5];
        us[6] = (byte) ((sendVal & 0xFF00) >>> 8);
        f7 = us[6];
        us[7] = (byte) ((sendVal & 0xFF));
        f8 = us[7];
        try {
            write.write(f1);
            write.write(f2);
            write.write(f3);
            write.write(f4);
            write.write(f5);
            write.write(f6);
            write.write(f7);
            write.write(f8);
//            write.write(f9);
//            write.write(f10);
//            write.write(f11);
            Log.i(log, "f1 = " + f1);
            Log.i(log, "f2 = " + f2);
            Log.i(log, "f3 = " + f3);
            Log.i(log, "f4 = " + f4);
            Log.i(log, "f5 = " + f5);
            Log.i(log, "f6 = " + f6);
            Log.i(log, "f7 = " + f7);
            Log.i(log, "f8 = " + f8);
//            Log.i(log, "f9 = " + f9);
//            Log.i(log, "f10 = " + f10);
//            Log.i(log, "f11 = " + f11);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Float getIEEE754Return() {
        int floatVal = Integer.parseInt("0");
        try {
            floatVal |= (read.read() & 0x07) << 29;
            floatVal |= (read.read() & 0x07) << 26;
            floatVal |= (read.read() & 0x07) << 23;
            floatVal |= (read.read() & 0x07) << 20;
            floatVal |= (read.read() & 0x07) << 17;
            floatVal |= (read.read() & 0x07) << 14;
            floatVal |= (read.read() & 0x07) << 11;
            floatVal |= (read.read() & 0x07) << 8;
            floatVal |= (read.read() & 0x07) << 5;
            floatVal |= (read.read() & 0x07) << 2;
            floatVal |= (read.read() & 0x07);
            return Float.intBitsToFloat(floatVal);
        } catch (IOException e) {
            e.printStackTrace();
            return 0f;
        }
    }

    public String getCheckMCUState() {
        int check;
        try {
            check = read.read();
        } catch (IOException e) {
            e.printStackTrace();
            return "ErrorCheck";
        }
        switch (check) {
            case 65:
                Log.d("BluetoothTest", "A");
                return "A";
            case 66:
                Log.d("BluetoothTest", "B");
                return "B";
            case 67:
                Log.d("BluetoothTest", "C");
                return "C";
            case 68:
                Log.d("BluetoothTest", "D");
                return "D";
            case 69:
                Log.d("BluetoothTest", "E");
                return "E";
            case 161:
                Log.d("BluetoothTest", "A");
                return "A";
            case 162:
                Log.d("BluetoothTest", "B");
                return "B";
            case 163:
                Log.d("BluetoothTest", "C");
                return "C";
            case 164:
                Log.d("BluetoothTest", "D");
                return "D";
            case 165:
                Log.d("BluetoothTest", "E");
                return "E";
        }
        return "ErrorFind " + String.valueOf(check);
    }

    public void resetBluetoothSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return socket != null;
    }
    public void setSocketNull() {
        try {
            if (read != null) {
                read.close();
            }
            if(write !=null){
                write.close();
            }
            if(socket!=null){
                socket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reSetBluetooth(){
        adapter.disable();
        adapter.enable();
    }
}
