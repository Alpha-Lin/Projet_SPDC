package com.example.projet_spdc;

import java.util.ArrayList;
import java.util.Objects;

public class Depute {
    static ArrayList<Depute> listDepute;
    int id;

    @Override
    public String toString() {
        return "Depute{" +
                "id=" + id +
                '}';
    }
    public void findGroupAndAddIt(String accronymeGroupe){
        for(Groupe groupe: Groupe.listeGroupe){
            if(Objects.equals(groupe.acronyme, accronymeGroupe)){
                groupe.addDepute(this);
            }
        }
    }
}
