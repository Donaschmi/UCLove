package ucloveproject.uclove.DB;

import java.util.Date;

/**
 * Created by Céline on 28-04-16.
 */
public class Disponibilite {
    private int id;
    private boolean statut;
    private Date date;//Contient également l'heure
    private int idProprietaire;

    public Disponibilite(int id, int proprietaire, Date date){
        this.id=id;
        this.date=date;
        this.statut=false;
        this.idProprietaire=proprietaire;
    }

    public int getId() {
        return id;
    }

    public void setStatut(boolean statut){
        this.statut=statut;
    }

    public boolean getStatut(){
        return statut;
    }

    public Date getDate(){
        return date;
    }

    public int getProprietaire(){
        return idProprietaire;
    }

    public boolean equals(Object obj) {
        if(obj instanceof Disponibilite){
            Disponibilite temp = (Disponibilite) obj;
            return(temp.getId()== this.id);
        }
        return false;
    }
}
