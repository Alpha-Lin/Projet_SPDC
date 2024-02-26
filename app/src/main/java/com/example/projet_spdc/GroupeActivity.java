package com.example.projet_spdc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupeActivity  extends AppCompatActivity {
    TextView groupe;
    TextView vin;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void research(String groupe) {
        String param = "https://api.openweathermap.org/data/2.5/weather?q="+city+",fr&units=metric&lang=fr&appid=ea3fd47b79737d51d2e5684dcd45ed17";
        System.out.println(param);
        System.out.println(city);
        Log.i("verif",param);
        Log.i("city",param);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String data = getDataFromHTTP(param);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            display(decodeJson(data,city));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }

    private String getDataFromHTTP(String param) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection connexion = null;
        try {
            URL url = new URL(param);
            connexion = (HttpURLConnection) url.openConnection();
            connexion.setRequestMethod("GET");
            InputStream inputStream = connexion.getInputStream();
            InputStreamReader inputStreamReader = new
                    InputStreamReader(inputStream);
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String ligne = "";
            while ((ligne = bf.readLine()) != null) {
                result.append(ligne);
            }
            inputStream.close();
            bf.close();
            connexion.disconnect();
        }catch (IOException e){
            result = new StringBuilder("Erreur d'internet");
            Log.e("erreur",e.toString());
        }
        catch (Exception e) {
            result = new StringBuilder("Erreur ");
            Log.e("erreur",e.toString());
        }
        return result.toString();
    }
    String decodeJson(String str,String ville) throws JSONException {
        JSONObject jso = new JSONObject(str);
        JSONObject coord = jso.getJSONObject("coord");
        JSONObject main = jso.getJSONObject("main");

        String result = "Météo de la ville de " + ville+ ": \n";
        result += "Température: "+main.getString("temp");
        result += "\nLatitude: "+coord.getString("lat");
        result += "\nLOngitude: "+coord.getString("lon");

        return result;
    }


}
