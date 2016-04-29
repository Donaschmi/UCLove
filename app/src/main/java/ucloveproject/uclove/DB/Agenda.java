package ucloveproject.uclove.DB;

import java.util.ArrayList;

/**
 * Created by Céline on 29-04-16.
 */
public class Agenda {
    private ArrayList<Disponibilite> dispos;
    private int idProprietaire;

    public Agenda(int proprietaire){
        this.idProprietaire=proprietaire;
    }

    public int getIdProprietaire(){
        return idProprietaire;
    }

    public ArrayList<Disponibilite> getDispos(){
        return dispos;
    }

    public void addDispo(Disponibilite dispo){
        dispos.add(dispo);
    }

    public void removeDispo(Disponibilite dispo){
        dispos.remove(dispo);
    }

    public void sendRdv(User user, Disponibilite date, String lieu){//Un objet rendez-vous, ou un arrayList ici ?
        //TODO, envoyer la dispo à l'user en question
    }

    public void acceptRdv(User user, Disponibilite date, String lieu){//Le lieu ne me semble pas indispensable
        //TODO, accepter le rendez-vous, enlever les deux dispos, stocker ça quelque part
    }

    public void removeRdv(User user, Disponibilite date){
        //TODO remove le rdv, remettre les deux dispos up, notifier l'autre user
    }

    public Disponibilite[] calculate(User user){
        //TODO Calcul des dispos respectives
        return null;
    }
}
