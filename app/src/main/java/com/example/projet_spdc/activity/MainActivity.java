package com.example.projet_spdc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import com.example.projet_spdc.Common;
import com.example.projet_spdc.object.Depute;
import com.example.projet_spdc.R;
import com.example.projet_spdc.loader.DeputeLoader;
import com.example.projet_spdc.object.Groupe;
import com.example.projet_spdc.loader.GroupeLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listViewMPs;
    private SearchView searchBar;
    private BroadcastReceiver br;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        MainActivity ma = this;

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Common.checkConnection(context))
                    connected();
            }
        };
        registerReceiver(br, intentFilter);

        listViewMPs = findViewById(R.id.listViewMPs);
        searchBar = findViewById(R.id.search_bar);

        if (Common.checkConnection(this))
            connected();
    }

    public void connected(){
        if (br != null)
            unregisterReceiver(br);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listViewMPs = findViewById(R.id.listViewMPs);
        searchBar = findViewById(R.id.search_bar);
        toolbar.setTitle("Home");
      
        setupSearchView();
      
        GroupeLoader gr = new GroupeLoader(this);

        gr.research();
    }

    public void onGroupeLoaded() {
        DeputeLoader mp = new DeputeLoader(this);
        mp.research();
    }

    public void onMPsLoaded(){
        TextView textLoading = findViewById(R.id.textLoading);
        textLoading.setVisibility(View.GONE);
        searchBar.setVisibility(View.VISIBLE);

        ArrayList<String> listMPsGroups = new ArrayList<>();
        for(Depute d : Depute.getListDepute())
            listMPsGroups.add("(MP) " + d.getNom_de_famille() + " " + d.getPrenom());
        for(Groupe g : Groupe.listeGroupe)
            listMPsGroups.add("(GRP) " + g.getNom());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMPsGroups);
        listViewMPs.setAdapter(adapter);

        Context con = this;

        listViewMPs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String[] infos = arg0.getAdapter().getItem(position).toString().split(" ");

                if(infos[0].equals("(MP)")) {
                    Depute d = Depute.getListDepute().stream().filter(depute -> depute.getNom_de_famille().equals(infos[1]) && depute.getPrenom().equals(infos[2])).findFirst().get();
                    Intent intent_MP = new Intent(con, MP_Activity.class);
                    intent_MP.putExtra("MP", d.getId() - 1);
                    startActivity(intent_MP);
                }else{
                    Groupe g = Groupe.listeGroupe.stream().filter(groupe -> groupe.getNom().equals(arg0.getAdapter().getItem(position).toString().substring(6))).findFirst().get();
                    Intent intent_Group = new Intent(con, GroupeActivity.class);
                    intent_Group.putExtra("groupe", Groupe.listeGroupe.indexOf(g));
                    startActivity(intent_Group);
                }
            }
        });
    }

    private void filter(String query) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) listViewMPs.getAdapter();
        adapter.getFilter().filter(query);
    }

    private void setupSearchView() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
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
    public void goToHome(){
        Intent favIntent = new Intent(this, MainActivity.class);
        startActivity(favIntent);
    }
}