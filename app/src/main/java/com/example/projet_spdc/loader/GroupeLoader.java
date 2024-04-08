package com.example.projet_spdc.loader;

import android.os.Handler;
import android.os.Looper;

import com.example.projet_spdc.Common;
import com.example.projet_spdc.activity.MainActivity;
import com.example.projet_spdc.object.Groupe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Permet de load un groupe.
 */
public class GroupeLoader {
    String param;
    MainActivity mainActivity;

    public GroupeLoader(MainActivity mainActivity){
        param = "https://www.nosdeputes.fr/organismes/groupe/json";
        this.mainActivity = mainActivity;
    }

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

                        mainActivity.onGroupeLoaded();
                    }
                });
            }
        });
    }

    /**
     * Transform un groupe sous format json en groupe et l(ajoute à la liste des groupes
     * @param object le groupe sous format json
     * @throws JSONException en cas d'erreur dans le jspn
     */
    private void transformJSONObjectIntoGroupe(JSONObject object) throws JSONException {
        if(!object.getString("nom").equals("false")){
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
    }

    /**
     * Analyse les fichier json des groupes
     * @param str le fichier json sous format texte
     * @throws JSONException si le fichier n'est pas convenable
     */
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
