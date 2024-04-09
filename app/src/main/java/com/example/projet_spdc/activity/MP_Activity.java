package com.example.projet_spdc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.projet_spdc.Common;
import com.example.projet_spdc.db.DBHandler;
import com.example.projet_spdc.object.Depute;
import com.example.projet_spdc.R;
import com.example.projet_spdc.object.Vote;
import com.example.projet_spdc.object.Groupe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MP_Activity extends AppCompatActivity  {
    Depute MP;
    ArrayList<String> listPhones = new ArrayList<>();

    ArrayList<Button> listButtonCall = new ArrayList<>();
    LinearLayout ll;
    Button favMPButtonAdd;
    Button favMPButtonDel;
    DBHandler handler;

    private SearchView searchBar;
    private Toolbar toolbar;

    public void goToHome(){
        Intent favIntent = new Intent(this, MainActivity.class);
        startActivity(favIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchBar = findViewById(R.id.search_bar);
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

        if(Common.checkConnection(this)){
            ExecutorService executor = Executors.newSingleThreadExecutor();

            Handler handler = new Handler(Looper.getMainLooper());
            ll = findViewById(R.id.llcontact);

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
        }

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

        MP_Activity mpActivity = this;
        LinearLayout websites = findViewById(R.id.websites);
        for(int i = 0; i < MP.getWebsites().size(); i++){
            TextView website_text = new TextView(this);
            website_text.setText(MP.getWebsites().get(i));
            website_text.setPadding(30, 0, 0, 0);
            websites.addView(website_text);
            int finalI = i;
            website_text.setOnClickListener(v -> {
                Intent group_activity = new Intent(mpActivity, WebActivity.class);
                group_activity.putExtra("website",MP.getWebsites().get(finalI));
                startActivity(group_activity);
            });
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
            String[] splited = MP.getAdresses().get(i).split("Téléphone : ");
            if(splited.length > 1){
                String tel = splited[1];
                adresse_text.setText(splited[0]);
                tel = tel.replace("."," ");
                listPhones.add(tel);
            }
            adresse_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://www.google.com/maps/search/"+adresse_text.getText().toString();
                    Intent group_activity = new Intent(mpActivity, WebActivity.class);
                    group_activity.putExtra("website",url);
                    startActivity(group_activity);

                }
            });
        }
        
        //Ajout des télpehones si présent
        if(listPhones.size() > 0){
            IntentFilter intentFilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");

            BroadcastReceiver airmodeReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    for(Button button : listButtonCall){
                        button.setEnabled(!button.isEnabled());
                    }
                }
            };
            registerReceiver(airmodeReceiver, intentFilter);

            LinearLayout phonesFather = new LinearLayout(this);
            ll.addView(phonesFather);
            phonesFather.setOrientation(LinearLayout.VERTICAL);
            TextView appelTXT = new TextView(this);
            phonesFather.addView(appelTXT);
            appelTXT.setText("Appeler :");

            LinearLayout phones = new LinearLayout(this);
            phones.setOrientation(LinearLayout.VERTICAL);
            phonesFather.addView(phones);
            for(String str : listPhones){
                Button btn = new Button(this);
                phones.addView(btn);
                btn.setText(str);
                listButtonCall.add(btn);
                btn.setEnabled(Settings.System.getInt(getContentResolver(),
                        Settings.Global.AIRPLANE_MODE_ON, 0) == 0);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callMP(str);
                    }
                });
            }
        }
        toolbar.setTitle("Député: "+MP.getPrenom()+" "+MP.getNom_de_famille());
        TextView txt = findViewById(R.id.toolbarTXT);
        txt.setText("Député: "+MP.getPrenom()+" "+MP.getNom_de_famille());

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


    /**
     * Permet d'appeler un député
     * @param phone le numéro à appeler
     */
    public void callMP(String phone){
        Intent i_call = new Intent(Intent.ACTION_DIAL);
        i_call.setData(Uri.parse("tel:" + phone));
        startActivity(i_call);
    }

    /**
     * Permet d'ajouter ou retirer des favoris un député
     * @param v le bouton
     */
    public void onClickMP(View v) {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.favBtn){
            Intent favIntent = new Intent(this, FavoriActivity.class);
            startActivity(favIntent);
        }else if(item.getItemId() == R.id.aproposBTN){
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        }

        return true;
    }
}