package com.example.projet_spdc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.projet_spdc.db.DBHandler;
import com.example.projet_spdc.object.Depute;
import com.example.projet_spdc.R;
import com.example.projet_spdc.object.Groupe;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GroupeActivity extends AppCompatActivity implements View.OnClickListener {
    Groupe gr;

    DBHandler handler;

    Button favGroupButtonAdd;
    Button favGroupButtonDel;


    private SearchView searchBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchBar = findViewById(R.id.search_bar);

        handler = new DBHandler(this);

        favGroupButtonAdd = findViewById(R.id.favGroupButtonAdd);
        favGroupButtonDel = findViewById(R.id.favGroupButtonDel);

        gr = Groupe.listeGroupe.get(getIntent().getIntExtra("groupe",0));
        if(handler.selectAllFavGroup().contains(gr))
            favGroupButtonDel.setVisibility(View.VISIBLE);
        else
            favGroupButtonAdd.setVisibility(View.VISIBLE);

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
        toolbar.setTitle("Groupe: "+gr.getNom());

        accro.setText("Acronyme : " + gr.getAcronyme());
        nb.setText("Nombre de parlementaires : "+ gr.getListDepute().size());
        Context c = this;
        TextView txt = findViewById(R.id.toolbarTXT);
        txt.setText("Groupe: "+gr.getNom());
        //CLiquer sur un député permet d'aller sur ca page
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Depute d = gr.listDepute.get(position);
                Intent intent_MP = new Intent(c, MP_Activity.class);
                intent_MP.putExtra("MP", d.getId() - 1);
                startActivity(intent_MP);
            }
        });
    }

    public void goToHome(){
        Intent favIntent = new Intent(this, MainActivity.class);
        startActivity(favIntent);
    }

    /**
     *
     * @param v le bouton favori
     */
    @Override
    public void onClick(View v) {
        Log.d("Mon id gr : ", gr.getId() +"");

        if(v.getId() == R.id.favGroupButtonAdd) {
            handler.insertFavGroup(gr.getId());
            favGroupButtonAdd.setVisibility(View.GONE);
            favGroupButtonDel.setVisibility(View.VISIBLE);
        }
        else {
            handler.deleteFavGroup(gr.getId());
            favGroupButtonAdd.setVisibility(View.VISIBLE);
            favGroupButtonDel.setVisibility(View.GONE);
        }
    }

    /**
     *
     * @param menu The options menu in which you place your items.
     *
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     *
     * @param item The menu item that was selected.
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.favBtn){
            Intent favIntent = new Intent(this, FavoriActivity.class);
            startActivity(favIntent);
        }else if(item.getItemId() == R.id.aproposBTN){
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        }

        return true;
    }
}