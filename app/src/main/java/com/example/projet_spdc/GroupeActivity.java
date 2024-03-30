package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupeActivity extends AppCompatActivity {
    Groupe gr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe);

        gr = Groupe.listeGroupe.get(getIntent().getIntExtra("groupe",0));

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
    }



}