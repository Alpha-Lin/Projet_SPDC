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
public class GroupeLoader extends AbstractLoader{


    public GroupeLoader(MainActivity mainActivity){
        super.param = "https://www.nosdeputes.fr/organismes/groupe/json";
        super.mainActivity = mainActivity;
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

    @Override
    void onLoaded() {
        mainActivity.onGroupeLoaded();
    }
}
