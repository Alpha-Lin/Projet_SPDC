package com.example.projet_spdc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WIFIReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isAvailable()) {
                Common.hasInternet = true;
                Toast.makeText(context, "Connecté au réseau", Toast.LENGTH_LONG).show();
            } else {
                Common.hasInternet = false;
                Toast.makeText(context, "Déconnecté du réseau", Toast.LENGTH_LONG).show();
            }
        }
    }
}
