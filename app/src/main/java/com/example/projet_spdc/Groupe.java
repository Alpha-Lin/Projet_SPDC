package com.example.projet_spdc;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

public class Groupe {
    static ArrayList<Groupe> listeGroupe = new ArrayList<>();
    int id;
    String slug;
    String nom;
    String acronyme;
    Boolean isActive;
    Color color;
    int order;
    String link;

    ArrayList<Depute> listDepute = new ArrayList<>();


    Boolean currentlyExist;

    public Boolean getCurrentlyExist() {
        return currentlyExist;
    }

    public void setCurrentlyExist(Boolean currentlyExist) {
        this.currentlyExist = currentlyExist;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public Groupe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    //TODO
    public void confirmGroup(){
        Log.d("Groupe ajout", "fait");
        listeGroupe.add(this);
    }
    //TODO
    public void setColor(String color){

    }

    public ArrayList<Depute> getListDepute() {
        return listDepute;
    }

    public void setListDepute(ArrayList<Depute> listDepute) {
        this.listDepute = listDepute;
    }

    public void addDepute(Depute d){
        listDepute.add(d);
    }


}
