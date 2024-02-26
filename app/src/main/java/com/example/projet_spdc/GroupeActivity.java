package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class GroupeActivity extends AppCompatActivity {

    TextView groupe;
    TextView vin;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groupe = (TextView)findViewById(R.id.groupe);
    }
}