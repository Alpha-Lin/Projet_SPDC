package com.example.projet_spdc;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
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

public class GroupeLoader {
    public void research() {
        String param = "https://www.nosdeputes.fr/organismes/groupe/json";
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
                            decodeJson(data);
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
    void transformJSONObjectIntoGroupe(JSONObject object) throws JSONException {
        Groupe groupe = new Groupe();
        groupe.setId(Integer.parseInt(object.getString("id")));
        groupe.setSlug(object.getString("slug"));
        groupe.setNom(object.getString("nom"));
        groupe.setAcronyme(object.getString("acronyme"));
        groupe.setCurrentlyExist(Boolean.valueOf(object.getString("groupe_actuel")));
        groupe.setColor(object.getString("couleur"));
        groupe.setLink(object.getString("url_nosdeputes_api"));
        groupe.confirmGroup();
    }

    void decodeJson(String str) throws JSONException {
        JSONObject jso = new JSONObject(str);
        JSONArray jsonArray = jso.getJSONArray("organismes");
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject organisme = jsonArray.getJSONObject(i);
            JSONObject insideOrganisme = organisme.getJSONObject("organisme");
            Log.w("ijdfjhdfqg",organisme.toString());
            Log.w("ijdfjhdfqg",insideOrganisme.toString());

            transformJSONObjectIntoGroupe(insideOrganisme);
        }
    }
}
