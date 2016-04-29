package ucloveproject.uclove.DB;

import java.util.Date;

/**
 * Created by Céline on 28-04-16.
 */
public class Message {
    private int id;
    private String contenu;
    private int idExpediteur;
    private int idDestinataire;
    private Date date;//Contient egalement l'heure

    public Message(int id, String texte, int expediteur, int destinataire, Date jour){
        this.id=id;
        this.contenu=texte;
        this.idExpediteur=expediteur;
        this.idDestinataire=destinataire;
        this.date=jour;
    }

    //Pas besoin de setters si on ne modifie pas les messages
    public int getId(){
        return id;
    }

    public String  getContenu(){
        return contenu;
    }

    public int getExpediteur(){
        return idExpediteur;
    }

    public int getDestinataire(){
        return idDestinataire;
    }

    public Date getDate(){
        return date;
    }
}
