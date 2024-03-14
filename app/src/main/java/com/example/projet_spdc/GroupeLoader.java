package com.example.projet_spdc;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupeLoader extends Loader {
    public GroupeLoader(){
        super();
        param = "https://www.nosdeputes.fr/organismes/groupe/json";
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

    @Override
    protected void decodeJson(String str) throws JSONException {
        Log.d("decodeJson", str);
        JSONObject jso = new JSONObject(str);
        JSONArray jsonArray = jso.getJSONArray("organismes");
        Log.d("Orga", jsonArray.length() + "");
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject organisme = jsonArray.getJSONObject(i);
            JSONObject insideOrganisme = organisme.getJSONObject("organisme");
            Log.w("ijdfjhdfqg",organisme.toString());
            Log.w("ijdfjhdfqg",insideOrganisme.toString());

            transformJSONObjectIntoGroupe(insideOrganisme);
        }
    }
}
