package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GroupeLoader gr = new GroupeLoader();
        gr.research();
        for(Groupe g : Groupe.listeGroupe){
            Log.w("jkhfjkd",g.getNom());
        };
    }
}