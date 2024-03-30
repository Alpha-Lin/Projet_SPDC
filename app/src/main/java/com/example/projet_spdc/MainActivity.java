package com.example.projet_spdc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GroupeLoader gr = new GroupeLoader(this);
        gr.research();

        //DeputeLoader mp = new DeputeLoader(this);
        //mp.research();

        /*Intent intent_MP = new Intent(this, MP_Activity.class);
        intent_MP.putExtra("MP", );
        startActivity(intent_MP);*/




    }

    public void onMPsLoaded(){
        LinearLayout layout_deputes = findViewById(R.id.layoutMPs);
        Log.d("affichage", "nb mps : " + Depute.listDepute.size());

        for(int i = 0; i < Depute.listDepute.size(); i++){
            //Log.d("J'affiche", "");
            generateTextViewNom(Depute.listDepute.get(i).getNom_de_famille(), Integer.parseInt("1" + i), layout_deputes);
            /*generateEditTextAnswer(Integer.parseInt("2" + i), layout_questions);
            generateTextViewAnswer(questions.get(i).getReponse(), Integer.parseInt("3" + i), layout_questions);*/
        }
    }

    private void generateTextViewNom (String nom, int index, LinearLayout layout){
        TextView t;
        t = new TextView(getApplicationContext());
        t.setText(nom);
        t.setId(index);
        layout.addView(t);
    }
}