package com.example.projet_spdc;

import android.graphics.Color;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Groupe {
    static ArrayList<Groupe> listeGroupe;
    int id;
    String slug;
    String nom;
    String acronyme;
    Boolean isActive;
    Color color;
    int order;
    String link;

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
