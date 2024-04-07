package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MP_Activity extends AppCompatActivity {
    Depute MP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp);
        MP_Activity mpActivity = this;
        int id_mp = getIntent().getIntExtra("MP", -1);

        if(id_mp == -1)
            Log.d("donnée mal transmise", "");

        MP = Depute.getListDepute().get(id_mp);

        loadInfo(mpActivity);
    }

    public void loadInfo(MP_Activity mpActivity) {
        // Pour charger la photo de profile et les votes
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ImageView photo_MP = findViewById(R.id.Photo_MP);

        TextView nom_MP = findViewById(R.id.Nom_MP);
        TextView debut_mandat = findViewById(R.id.Debut_mandat);
        TextView parti = findViewById(R.id.Parti);
        TextView groupe_mp = findViewById(R.id.Groupe_MP);
        TextView circo_mp = findViewById(R.id.Circo_MP);
        TextView dep = findViewById(R.id.depMP);
        Handler handler = new Handler(Looper.getMainLooper());
        nom_MP.setText("Nom : " + MP.getNom_de_famille() + " " + MP.getPrenom());
        debut_mandat.setText("Début de mandat : " + MP.getMandat_debut());
        parti.setText("Parti : " + MP.getParti_financier());
        groupe_mp.setText("Groupe : " + MP.getGroupe().getNom());
        circo_mp.setText("Circonscription : " + MP.getNum_circo() + "");
        dep.setText("Département: "+MP.getDepartement());
        LinearLayout websites = findViewById(R.id.websites);
        for(int i = 0; i < MP.getWebsites().size(); i++){
            TextView website_text = new TextView(mpActivity);
            website_text.setText(MP.getWebsites().get(i));
            website_text.setPadding(30, 0, 0, 0);
            websites.addView(website_text);
        }

        LinearLayout emails = findViewById(R.id.emails);
        for(int i = 0; i < MP.getEmails().size(); i++){
            TextView email_text = new TextView(mpActivity);
            email_text.setText(MP.getEmails().get(i));
            email_text.setPadding(30, 0, 0, 0);
            emails.addView(email_text);
        }

        LinearLayout adresses = findViewById(R.id.adresses);
        for(int i = 0; i < MP.getAdresses().size(); i++){
            TextView adresse_text = new TextView(mpActivity);
            adresse_text.setText(MP.getAdresses().get(i));
            adresse_text.setPadding(30, 0, 0, 0);
            adresses.addView(adresse_text);
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap data_photo = Common.getImageFromHTTP("https://nosdeputes.fr/depute/photo/" + MP.getSlug() + "/200");
                String data_votes = null;
                try {
                    data_votes = Common.getDataFromHTTP("https://nosdeputes.fr/" + MP.getSlug() + "/votes/json");
                    String finalData_votes = data_votes;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            photo_MP.setImageBitmap(data_photo);
                            try {
                                decodeJSONVotes(finalData_votes);
                            } catch (JSONException e) {
                                //TODO write something instead of votes
                                Common.hasInternet = false;
                                MainActivity.getInternet(mpActivity);
                            }
                            onVotesLoaded();
                        }
                    });
                } catch (IOException e) {
                    //TODO Load a fake img or do sthm like that
                }

            }
        });
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
}