package com.example.projet_spdc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;


import java.util.List;

public class FavoriActivity extends AppCompatActivity {
    LinearLayout depLayout;
    LinearLayout groupLayout;


    private SearchView searchBar;
    private Toolbar toolbar;
    private Button homeBTN;

    DBHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favori);
        depLayout = findViewById(R.id.layoutForFavDep);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchBar = findViewById(R.id.search_bar);
        toolbar.setTitle("");

        groupLayout = findViewById(R.id.layoutForFavGroup);

        FavoriActivity fav = this;

        handler = new DBHandler(this);

        List<Depute> listFavDep = handler.selectAllFavMP();
        List<Groupe> listFavGroup = handler.selectAllFavGroup();
        for(Depute d: listFavDep){
            LinearLayout l = new LinearLayout(this);
            TextView t = new TextView(this);
            Button b = new Button(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            t.setText(d.getNom_de_famille() + " " + d.getPrenom());
            b.setText("Retirer");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.deleteFavMP(d.getId());
                    depLayout.removeView(l);
                }
            });
            l.addView(t);
            l.addView(b);
            depLayout.addView(l);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_MP = new Intent(fav, MP_Activity.class);
                    intent_MP.putExtra("MP", d.getId() - 1);
                    startActivity(intent_MP);
                }
            });
        }
        for(Groupe g: listFavGroup){
            LinearLayout l = new LinearLayout(this);
            TextView t = new TextView(this);
            Button b = new Button(this);
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent group_activity = new Intent(fav, GroupeActivity.class);
                    group_activity.putExtra("groupe", Groupe.listeGroupe.indexOf(g));
                    startActivity(group_activity);
                }
            });
            l.setOrientation(LinearLayout.HORIZONTAL);
            t.setText(g.getNom());
            b.setText("Retirer");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.deleteFavGroup(g.getId());
                    groupLayout.removeView(l);
                }
            });
            l.addView(t);
            l.addView(b);
            groupLayout.addView(l);
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
            /*Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);*/
        }

        return true;
    }
}