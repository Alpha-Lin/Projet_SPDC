package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GroupeLoader gr = new GroupeLoader(this);
        gr.research();
    }

    public void onGroupeLoaded() {
        DeputeLoader mp = new DeputeLoader(this);
        mp.research();

        Button loadGroup = findViewById(R.id.loadGroup);
        loadGroup.setVisibility(View.VISIBLE);
    }

    public void onMPsLoaded(){
        Button loadMP = findViewById(R.id.loadMP);
        loadMP.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loadMP) {
            Intent intent_MP = new Intent(this, MP_Activity.class);
            intent_MP.putExtra("MP", 0);
            startActivity(intent_MP);
        }
        else if(v.getId() == R.id.loadGroup) {
            Intent group_activity = new Intent(this, GroupeActivity.class);
            group_activity.putExtra("groupe", 2);
            startActivity(group_activity);
        }
    }
}