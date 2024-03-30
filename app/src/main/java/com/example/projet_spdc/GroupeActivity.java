package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupeActivity extends AppCompatActivity {
    Groupe gr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe);
        Log.w("meeeeeeeeee",""+Groupe.listeGroupe.size());
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
        Context c = this;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Depute d = gr.listDepute.get(position);
                Intent intent_MP = new Intent(c, MP_Activity.class);
                intent_MP.putExtra("MP", d.getId());
                startActivity(intent_MP);
            }
        });
    }



}