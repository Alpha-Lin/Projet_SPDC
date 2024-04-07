package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listViewMPs;
    private SearchView searchBar;
    public GroupeLoader gr;
    public DeputeLoader dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMPs = findViewById(R.id.listViewMPs);
        searchBar = findViewById(R.id.search_bar);
        Common.mainActivity = this;

        try {
            setupSearchView();
            gr = new GroupeLoader(this);
            gr.research();
        }catch (Exception e){

        }

    }

    public void onGroupeLoaded() {
        DeputeLoader mp = new DeputeLoader(this,gr);
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
    public static void getInternet(GroupeLoader gr){

    }
    public static void getInternet(MP_Activity mp){

    }
    public static void gotBackInternet(){
        if(Common.groupLoaded){
            Common.mainActivity.gr = new GroupeLoader(Common.mainActivity);
            Common.mainActivity.gr.research();
        }else if(Common.mpLoaded){
            Common.mainActivity.onGroupeLoaded();
        }else{

        }
    }
}