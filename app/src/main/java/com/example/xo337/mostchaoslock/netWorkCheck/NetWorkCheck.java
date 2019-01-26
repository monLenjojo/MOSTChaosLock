package com.example.xo337.mostchaoslock.netWorkCheck;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
public class NetWorkCheck {
    Context context;

    public NetWorkCheck(Context context) {
        this.context = context;
    }

    public void getNetWorkCheck() {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Log.d("LogInActivity", "網路連線狀態： "+"錯誤");
            new AlertDialog.Builder(context)
                    .setTitle("無法連線")
                    .setMessage("請確認裝置是否連上網路並可正常使用")
                    .setPositiveButton("完成", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getNetWorkCheck();
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new AlertDialog.Builder(context)
                                    .setMessage("裝置無法連線將無法完成功能\n")
                                    .setPositiveButton("沒關係", null)
                                    .show();
                        }
                    })
                    .show();
        }else {
            Log.d("LogInActivity", "網路連線狀態： "+"正常");
        }
    }
}
