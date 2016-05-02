package ucloveproject.uclove.DB;

/**
 * Created by Céline on 02-05-16.
 */
public class Relation {
    private int id;
    private int idFirst;
    private int idSecond;
    private boolean favoris;

    public Relation(int id, int firstUser, int secondUser){
        this.id = id;
        this.idFirst = firstUser;
        this.idSecond = secondUser;
        this.favoris = false;
    }

    public int getId(){
        return this.id;
    }

    public int getFirstUser(){
        return this.idFirst;
    }

    public int getSecondUser(){
        return this.idSecond;
    }

    public boolean getFav(){
        return this.favoris;
    }

    public void setFav(boolean fav){
        this.favoris = fav;
    }
}
