package com.example.projet_spdc;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupeLoader {
    String param;
    MainActivity mainActivity;

    public GroupeLoader(MainActivity mainActivity){
        param = "https://www.nosdeputes.fr/organismes/groupe/json";
        this.mainActivity = mainActivity;
    }

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

                        mainActivity.onGroupeLoaded();
                    }
                });
            }
        });
    }

    private void transformJSONObjectIntoGroupe(JSONObject object) throws JSONException {
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

    public void decodeJson(String str) throws JSONException {
        JSONObject jso = new JSONObject(str);
        JSONArray jsonArray = jso.getJSONArray("organismes");
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject organisme = jsonArray.getJSONObject(i);
            JSONObject insideOrganisme = organisme.getJSONObject("organisme");

            transformJSONObjectIntoGroupe(insideOrganisme);
        }
    }
}
