package com.example.projet_spdc;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Loader {
    protected String param;

    protected void research() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        Log.d("URL dl", "moimoi");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("URL dl", "presque");
                String data = Common.getDataFromHTTP(param);
                Log.d("URL dl", data);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            decodeJson(data);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }

     protected abstract void decodeJson(String str) throws JSONException;
}
