package com.example.projet_spdc.loader;

import android.os.Handler;
import android.os.Looper;

import com.example.projet_spdc.Common;
import com.example.projet_spdc.object.Depute;
import com.example.projet_spdc.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeputeLoader {
    String param;
    MainActivity mainActivity;

    public DeputeLoader(MainActivity mainActivity){
        param = "https://www.nosdeputes.fr/deputes/json";
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

                        mainActivity.onMPsLoaded();
                    }
                });
            }
        });
    }

    public DeputeLoader(String slug){
        super();
        param = "https://www.nosdeputes.fr/organisme/" + slug + "/json";
    }

    /**
     * Analyse un fichier json de liste de député
     * @param str le fichier json
     * @throws JSONException si un objet ne correspond pas à un député au format valide
     */

    public void decodeJson(String str) throws JSONException {
        JSONObject jso = new JSONObject(str);
        JSONArray deputies = jso.getJSONArray("deputes");
        for(int i = 0; i < deputies.length(); i++){
            JSONObject depute = (deputies.getJSONObject(i)).getJSONObject("depute");
            decodeDepute(depute);
        }
    }

    /**
     * Transforme un JSON objet représentant un député en député et l'ajoute à la liste des députés
     * @param jsonObject le député sous format JSON
     * @throws JSONException si le fichier json n'est pas correct
     */
    private static void decodeDepute(JSONObject jsonObject) throws JSONException {
        Depute depute = new Depute();
        depute.setId(jsonObject.getInt("id"));
        depute.setNom_de_famille(jsonObject.getString("nom_de_famille"));
        depute.setPrenom(jsonObject.getString("prenom"));
        depute.setSexe(jsonObject.getString("sexe"));
        depute.setDate_naissance(jsonObject.getString("date_naissance"));
        depute.setLieu_naissance(jsonObject.getString("lieu_naissance"));
        depute.setDepartement(jsonObject.getString("num_deptmt"));
        depute.setNum_circo(jsonObject.getInt("num_circo"));
        depute.setMandat_debut(jsonObject.getString("mandat_debut"));
        depute.setGroupe(jsonObject.getString("groupe_sigle"));
        depute.setParti_financier(jsonObject.getString("parti_ratt_financier"));

        JSONArray sites_web = jsonObject.getJSONArray("sites_web");
        for(int i = 0; i < sites_web.length(); i++)
            depute.addWebsite(sites_web.getJSONObject(i).getString("site"));
        JSONArray emails = jsonObject.getJSONArray("emails");
        for(int i = 0; i < emails.length(); i++)
            depute.addEmail(emails.getJSONObject(i).getString("email"));
        JSONArray adresses = jsonObject.getJSONArray("adresses");
        for(int i = 0; i < adresses.length(); i++)
            depute.addAdress(adresses.getJSONObject(i).getString("adresse"));
        JSONArray collaborateurs = jsonObject.getJSONArray("collaborateurs");
        for(int i = 0; i < collaborateurs.length(); i++)
            depute.addCollab(collaborateurs.getJSONObject(i).getString("collaborateur"));

        depute.setProfession(jsonObject.getString("profession"));
        depute.setPlace_en_hemycicle(Integer.parseInt(jsonObject.getString("place_en_hemicycle")));
        depute.setUrl_an(jsonObject.getString("url_an"));
        depute.setId_an(Integer.parseInt(jsonObject.getString("id_an")));
        depute.setSlug(jsonObject.getString("slug"));
        depute.setUrl_nosdeputes(jsonObject.getString("url_nosdeputes"));
        depute.setNbmandat(jsonObject.getInt("nb_mandats"));
        depute.setTwitter(jsonObject.getString("twitter"));
        depute.confirmDepute();
    }
}
