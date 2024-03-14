package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GroupeLoader gr = new GroupeLoader();
        gr.research();
        Log.d("Nb Groupe", Groupe.listeGroupe.size() + "");
        for(Groupe g : Groupe.listeGroupe){
            Log.w("jkhfjkd",g.getNom());
        };

        DeputeLoader mp = new DeputeLoader();
        mp.research();
        for(Depute d : Depute.listDepute){
            Log.w("MP", d.getNom_de_famille());
        }


        /*Intent intent_MP = new Intent(this, MP_Activity.class);
        intent_MP.putExtra("MP", );
        startActivity(intent_MP);

        /*Intent group_activity = new Intent(this, GroupeActivity.class);
        group_activity.putExtra("groupe",2);
        startActivity(group_activity);*/

    }
}