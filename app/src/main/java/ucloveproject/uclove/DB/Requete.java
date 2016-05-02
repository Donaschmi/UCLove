package ucloveproject.uclove.DB;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Céline on 28-04-16.
 */
public class Requete {
    private int id;
    private int idExpediteur;
    private int idDestinataire;
    private String statut;
    private Date date;//Contient également l'heure

    public Requete(int id, int expediteur, int destinataire, Date jour){
        this.id=id;
        this.idExpediteur=expediteur;
        this.idDestinataire=destinataire;
        this.statut="attente";//Si on la crée, c'est probablement pas encore accepté
        this.date=jour;
    }

    public int getId(){
        return id;
    }

    public int getExpediteur(){
        return idExpediteur;
    }

    public int getDestinataire(){
        return idDestinataire;
    }

    public String getStatut(){
        return statut;
    }

    /**
     *
     * @param statut ne peut être que "attente", "valide" ou "rejet"
     */
    public void setStatut(String statut){
        this.statut=statut;
    }

    public Date getDate() {
        return date;
    }
}
