package com.example.projet_spdc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupeActivity extends AppCompatActivity implements View.OnClickListener {
    Groupe gr;

    DBHandler handler;

    Button favGroupButtonAdd;
    Button favGroupButtonDel;


    private SearchView searchBar;
    private Toolbar toolbar;
    private Button homeBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchBar = findViewById(R.id.search_bar);
        toolbar.setTitle("");

        handler = new DBHandler(this);

        favGroupButtonAdd = findViewById(R.id.favGroupButtonAdd);
        favGroupButtonDel = findViewById(R.id.favGroupButtonDel);

        gr = Groupe.listeGroupe.get(getIntent().getIntExtra("groupe",0));

        if(handler.selectAllFavGroup().contains(gr))
            favGroupButtonDel.setVisibility(View.VISIBLE);
        else
            favGroupButtonAdd.setVisibility(View.VISIBLE);

        TextView groupe = findViewById(R.id.nom_groupe);
        TextView nb = findViewById(R.id.nb_mps);
        TextView accro = findViewById(R.id.acronyme);
        ListView list = findViewById(R.id.listMPGroupe);

        ArrayList<String> listMPs = new ArrayList<>();
        for(Depute d : gr.getListDepute()){
            listMPs.add(d.getNom_de_famille() + " " + d.getPrenom());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.layout_mps_group, listMPs);
        list.setAdapter(arrayAdapter);

        groupe.setText("Nom : "+ gr.getNom());
        accro.setText("Acronyme : " + gr.getAcronyme());
        nb.setText("Nombre de parlementaires : "+ gr.getListDepute().size());
        Context c = this;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Depute d = gr.listDepute.get(position);
                Intent intent_MP = new Intent(c, MP_Activity.class);
                intent_MP.putExtra("MP", d.getId() - 1);
                startActivity(intent_MP);
            }
        });
    }

    public void goToHome(){
        Intent favIntent = new Intent(this, MainActivity.class);
        startActivity(favIntent);
    }

    @Override
    public void onClick(View v) {
        Log.d("Mon id gr : ", gr.getId() +"");

        if(v.getId() == R.id.favGroupButtonAdd) {
            handler.insertFavGroup(gr.getId());
            favGroupButtonAdd.setVisibility(View.GONE);
            favGroupButtonDel.setVisibility(View.VISIBLE);
        }
        else {
            handler.deleteFavGroup(gr.getId());
            favGroupButtonAdd.setVisibility(View.VISIBLE);
            favGroupButtonDel.setVisibility(View.GONE);
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