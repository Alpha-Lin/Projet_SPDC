package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupeActivity extends AppCompatActivity {

    TextView groupe;
    TextView nb;
    ListView list;
    TextView accro;
    Groupe gr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe);

        gr = Groupe.listeGroupe.get(getIntent().getIntExtra("groupe",0));


        groupe = (TextView)findViewById(R.id.groupeInput);
        nb = (TextView)findViewById(R.id.nbDepute);
        list = (ListView)findViewById(R.id.listMPGroupe);
        accro = (TextView)findViewById(R.id.acronymeInput);
        ArrayList<String> strList = new ArrayList<>();
        for(Depute d : gr.listDepute){
            strList.add(d.nom_de_famille);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_groupe, R.id.liste,strList );
        Log.w("ffffffffff",""+strList.size());
        list.setAdapter(arrayAdapter);
        groupe.setText(" "+gr.nom);
        nb.setText(" "+strList.size());
        accro.setText(" "+gr.acronyme);


    }



}