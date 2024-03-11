package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MP_Activity extends AppCompatActivity {

    Depute depute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        depute = Depute.listDepute.get(getIntent().getIntExtra("depute",0));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String data = Common.getDataFromHTTP("https://www.nosdeputes.fr/mireille-clapot/json"); // a changer avec la récupération de député
                Bitmap data_photo = Common.getImageFromHTTP("https://nosdeputes.fr/depute/photo/mireille-clapot/200");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ImageView photo_MP = findViewById(R.id.Photo_MP);
                        TextView nom_MP = findViewById(R.id.Nom_MP);

                        try {
                            photo_MP.setImageBitmap(data_photo);

                            JSONObject data_MP = new JSONObject(data);
                            data_MP = data_MP.getJSONObject("depute");

                            nom_MP.setText(data_MP.getString("nom"));
                        } catch (JSONException e) {}
                    }
                });
            }
        });
    }
}