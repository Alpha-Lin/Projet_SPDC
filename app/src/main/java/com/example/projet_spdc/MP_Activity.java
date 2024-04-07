package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MP_Activity extends AppCompatActivity  {
    Depute MP;
    Button favMPButtonAdd;
    Button favMPButtonDel;
    DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp);

        favMPButtonAdd = findViewById(R.id.favMPButtonAdd);
        favMPButtonDel = findViewById(R.id.favMPButtonDel);

        handler = new DBHandler(this);

        int id_mp = getIntent().getIntExtra("MP", -1);

        if(id_mp == -1)
            Log.d("donnée mal transmise", "");

        MP = Depute.getListDepute().get(id_mp);

        if(handler.selectAllFavMP().contains(MP))
            favMPButtonDel.setVisibility(View.VISIBLE);
        else
            favMPButtonAdd.setVisibility(View.VISIBLE);

        // Pour charger la photo de profile et les votes
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap data_photo = Common.getImageFromHTTP("https://nosdeputes.fr/depute/photo/" + MP.getSlug() + "/200");
                String data_votes = Common.getDataFromHTTP("https://nosdeputes.fr/" + MP.getSlug() + "/votes/json");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ImageView photo_MP = findViewById(R.id.Photo_MP);
                        photo_MP.setImageBitmap(data_photo);

                        try {
                            decodeJSONVotes(data_votes);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        onVotesLoaded();
                    }
                });
            }
        });

        TextView nom_MP = findViewById(R.id.Nom_MP);
        TextView debut_mandat = findViewById(R.id.Debut_mandat);
        TextView parti = findViewById(R.id.Parti);
        TextView groupe_mp = findViewById(R.id.Groupe_MP);
        TextView circo_mp = findViewById(R.id.Circo_MP);
        TextView dep = findViewById(R.id.depMP);

        nom_MP.setText("Nom : " + MP.getNom_de_famille() + " " + MP.getPrenom());
        debut_mandat.setText("Début de mandat : " + MP.getMandat_debut());
        parti.setText("Parti : " + MP.getParti_financier());
        groupe_mp.setText("Groupe : " + MP.getGroupe().getNom());
        circo_mp.setText("Circonscription : " + MP.getNum_circo() + "");
        dep.setText("Département: "+MP.getDepartement());

        LinearLayout websites = findViewById(R.id.websites);
        for(int i = 0; i < MP.getWebsites().size(); i++){
            TextView website_text = new TextView(this);
            website_text.setText(MP.getWebsites().get(i));
            website_text.setPadding(30, 0, 0, 0);
            websites.addView(website_text);
        }

        LinearLayout emails = findViewById(R.id.emails);
        for(int i = 0; i < MP.getEmails().size(); i++){
            TextView email_text = new TextView(this);
            email_text.setText(MP.getEmails().get(i));
            email_text.setPadding(30, 0, 0, 0);
            emails.addView(email_text);
        }

        LinearLayout adresses = findViewById(R.id.adresses);
        for(int i = 0; i < MP.getAdresses().size(); i++){
            TextView adresse_text = new TextView(this);
            adresse_text.setText(MP.getAdresses().get(i));
            adresse_text.setPadding(30, 0, 0, 0);
            adresses.addView(adresse_text);
        }
    }

    public void decodeJSONVotes(String data) throws JSONException {
        JSONObject jso = new JSONObject(data);
        JSONArray votes = jso.getJSONArray("votes");
        for(int i = 0; i < votes.length(); i++){
            JSONObject vote = (votes.getJSONObject(i)).getJSONObject("vote");
            MP.addVote(new Vote(vote.getJSONObject("scrutin").getString("date"), vote.getJSONObject("scrutin").getString("titre"), vote.getString("position")));
        }
    }

    public void onVotesLoaded(){
        LinearLayout votes = findViewById(R.id.votes);
        for(int i = 0; i < MP.getListVotes().size(); i++){
            LinearLayout vote = new LinearLayout(this);
            vote.setOrientation(LinearLayout.VERTICAL);
            TextView intitule = new TextView(this);
            TextView date = new TextView(this);
            TextView position = new TextView(this);

            intitule.setText("• Intitulé : " + MP.getListVotes().get(i).getIntitutle());
            intitule.setPadding(5, 0, 0, 0);
            date.setText("Date : " + MP.getListVotes().get(i).getDate());
            date.setPadding(30, 0, 0, 0);
            position.setText("Position : " + MP.getListVotes().get(i).getPosition());
            position.setPadding(30, 0, 0, 0);

            vote.addView(intitule);
            vote.addView(date);
            vote.addView(position);

            votes.addView(vote);
        }
    }

    public void goToGroup(View view) {
        Intent group_activity = new Intent(this, GroupeActivity.class);
        group_activity.putExtra("groupe", Groupe.listeGroupe.indexOf(MP.getGroupe()));
        startActivity(group_activity);
    }

    public void onClickMP(View v) {
        Log.w("ajout de mp",""+v.getId());
        if(v.getId() == R.id.favMPButtonAdd) {
            handler.insertFavMP(MP.getId());
            favMPButtonAdd.setVisibility(View.GONE);
            favMPButtonDel.setVisibility(View.VISIBLE);
        }
        else {
            handler.deleteFavMP(MP.getId());
            favMPButtonAdd.setVisibility(View.VISIBLE);
            favMPButtonDel.setVisibility(View.GONE);
        }
    }
}