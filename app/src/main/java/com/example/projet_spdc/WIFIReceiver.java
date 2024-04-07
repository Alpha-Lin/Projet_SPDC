package com.example.projet_spdc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class WIFIReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isAvailable()) {
                Common.hasInternet = true;
                Log.w("wifistatut","connexted");
                Toast.makeText(context, "Connecté au réseau", Toast.LENGTH_LONG).show();
                MainActivity.gotBackInternet();
            } else {
                Common.hasInternet = false;
                Log.w("wifistatut","not connected");

                Toast.makeText(context, "Déconnecté du réseau", Toast.LENGTH_LONG).show();
            }
        }
    }
}
