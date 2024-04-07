package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriActivity extends AppCompatActivity {
    LinearLayout depLayout;
    LinearLayout groupLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depLayout = findViewById(R.id.layoutForFavDep);
        groupLayout = findViewById(R.id.layoutForFavGroup);
        FavoriActivity fav = this;
        //TODO Load fav
        ArrayList<Depute> listFavDep = new ArrayList<>();
        ArrayList<Groupe> listFavGroup = new ArrayList<>();
        for(Depute d: listFavDep){
            LinearLayout l = new LinearLayout(this);
            TextView t = new TextView(this);
            Button b = new Button(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            t.setText(d.nom_de_famille+ " "+ d.getPrenom());
            b.setText("Retirer");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO remove from DB
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
            t.setText(g.nom);
            b.setText("Retirer");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO remove from DB
                }
            });
            l.addView(t);
            l.addView(b);
            groupLayout.addView(l);
        }
        setContentView(R.layout.activity_favori);
    }
}