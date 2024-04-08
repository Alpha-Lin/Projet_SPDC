package com.example.projet_spdc.loader;

import android.os.Handler;
import android.os.Looper;

import com.example.projet_spdc.Common;
import com.example.projet_spdc.activity.MainActivity;

import org.json.JSONException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractLoader {
    String param;
    MainActivity mainActivity;

    abstract void decodeJson(String str) throws JSONException;
    abstract void onLoaded();
    /**
     * Lance le thread de connexion à l'API puis lance l'analyse du résultat
     */
    public void research() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String data = Common.getDataFromHTTP(param);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            decodeJson(data);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        onLoaded();
                    }
                });
            }
        });
    }
}
