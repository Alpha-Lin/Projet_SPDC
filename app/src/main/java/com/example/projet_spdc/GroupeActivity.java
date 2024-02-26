package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupeActivity extends AppCompatActivity {

    TextView groupe;
    TextView nb;
    ListView list;
    Groupe gr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groupe = (TextView)findViewById(R.id.groupe);
        nb = (TextView)findViewById(R.id.nb);
        list = (ListView)findViewById(R.id.liste);
        ArrayList listMP;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_groupe, R.id.liste, listMP);
    }


}