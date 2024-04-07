package com.example.projet_spdc;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class ReceiverConnection extends BroadcastReceiver {
    private MainActivity ct;

    public ReceiverConnection(MainActivity ct){
        this.ct = ct;
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        ct.registerReceiver(this, intentFilter);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("miamoooooooooooo","test");
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected)
            ct.connected();
    }
}



