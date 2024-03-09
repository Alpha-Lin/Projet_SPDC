package com.example.projet_spdc;

import java.util.ArrayList;
import java.util.Objects;

public class Depute {
    static ArrayList<Depute> listDepute;
    int id;
    String nom_de_famille;
    String prenom;
    String sexe;
    String date_naissance;
    String lieu_naissance;
    int departement;
    String nom_departement;
    int num_circo;
    String mandat_debut;
    Groupe groupe;
    String parti_financier;
    String profession;
    int place_en_hemycicle;
    String url_an;
    int id_an;
    String slug;
    String url_nosdeputes;
    int nbmandat;
    String twitter;

    ArrayList<String> websites = new ArrayList<>();
    ArrayList<String> emails = new ArrayList<>();
    ArrayList<String> adresses = new ArrayList<>();
    ArrayList<String> collab = new ArrayList<>();

    ArrayList<String> otherMandates = new ArrayList<>();
    ArrayList<String> otherOlderMandates = new ArrayList<>();
    ArrayList<String> olderMandates = new ArrayList<>();
    public void addOtherMandate(String str){
        otherMandates.add(str);
    }
    public void addOtherOlderMandate(String str){
        otherOlderMandates.add(str);
    }
    public void olderMandates(String str){
        olderMandates.add(str);
    }

    public void addWebsite(String str){
        websites.add(str);
    }
    public void addEmail(String str){
        emails.add(str);
    }
    public void addAdress(String str){
        adresses.add(str);
    }
    public void addCollab(String str){
        collab.add(str);
    }
    public static ArrayList<Depute> getListDepute() {
        return listDepute;
    }

    public static void setListDepute(ArrayList<Depute> listDepute) {
        Depute.listDepute = listDepute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_de_famille() {
        return nom_de_famille;
    }

    public void setNom_de_famille(String nom_de_famille) {
        this.nom_de_famille = nom_de_famille;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getLieu_naissance() {
        return lieu_naissance;
    }

    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public int getDepartement() {
        return departement;
    }

    public void setDepartement(int departement) {
        this.departement = departement;
    }

    public String getNom_departement() {
        return nom_departement;
    }

    public void setNom_departement(String nom_departement) {
        this.nom_departement = nom_departement;
    }

    public int getNum_circo() {
        return num_circo;
    }

    public void setNum_circo(int num_circo) {
        this.num_circo = num_circo;
    }

    public String getMandat_debut() {
        return mandat_debut;
    }

    public void setMandat_debut(String mandat_debut) {
        this.mandat_debut = mandat_debut;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
    public void setGroupe(String str){
       findGroupAndAddIt(str);
    }

    public String getParti_financier() {
        return parti_financier;
    }

    public void setParti_financier(String parti_financier) {
        this.parti_financier = parti_financier;
    }

    public ArrayList<String> getWebsites() {
        return websites;
    }

    public void setWebsites(ArrayList<String> websites) {
        this.websites = websites;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public ArrayList<String> getAdresses() {
        return adresses;
    }

    public void setAdresses(ArrayList<String> adresses) {
        this.adresses = adresses;
    }

    public ArrayList<String> getCollab() {
        return collab;
    }

    public void setCollab(ArrayList<String> collab) {
        this.collab = collab;
    }

    public ArrayList<String> getOtherMandates() {
        return otherMandates;
    }

    public void setOtherMandates(ArrayList<String> otherMandates) {
        this.otherMandates = otherMandates;
    }

    public ArrayList<String> getOtherOlderMandates() {
        return otherOlderMandates;
    }

    public void setOtherOlderMandates(ArrayList<String> otherOlderMandates) {
        this.otherOlderMandates = otherOlderMandates;
    }

    public ArrayList<String> getOlderMandates() {
        return olderMandates;
    }

    public void setOlderMandates(ArrayList<String> olderMandates) {
        this.olderMandates = olderMandates;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getPlace_en_hemycicle() {
        return place_en_hemycicle;
    }

    public void setPlace_en_hemycicle(int place_en_hemycicle) {
        this.place_en_hemycicle = place_en_hemycicle;
    }

    public String getUrl_an() {
        return url_an;
    }

    public void setUrl_an(String url_an) {
        this.url_an = url_an;
    }

    public int getId_an() {
        return id_an;
    }

    public void setId_an(int id_an) {
        this.id_an = id_an;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl_nosdeputes() {
        return url_nosdeputes;
    }

    public void setUrl_nosdeputes(String url_nosdeputes) {
        this.url_nosdeputes = url_nosdeputes;
    }

    public int getNbmandat() {
        return nbmandat;
    }

    public void setNbmandat(int nbmandat) {
        this.nbmandat = nbmandat;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }


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
                this.groupe = groupe;
            }
        }
    }
    //TODO
    public void confirmDepute(){
        listDepute.add(this);
    }

    public Depute() {
        confirmDepute();
    }
}
