package ucloveproject.uclove.DB;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Céline on 28-04-16.
 */
public class User {
    private int id;
    private String login;
    private String mdp;//hash
    private String nom;
    private String genre;
    private int age;//Changer en date pour la calculer en permanence ?
    private String styleCapilaire;
    private String couleurYeux;
    private String villeResidence;
    private String orientationSexuelle;
    private String[] preference;
    private String[] photo;//Trouver comment stocker des images
    ArrayList<User> amis;
    ArrayList<User> favoris;

    public User(int id, String login, String mdp, String nom, int age, String genre, String orientation, String style, String yeux, String ville) {
        this.id = id;
        this.login = login;
        this.mdp = mdp;
        this.nom = nom;
        this.genre = genre;
        this.age=age;
        this.styleCapilaire = style;
        this.couleurYeux = yeux;
        this.villeResidence = ville;
        this.orientationSexuelle = orientation;
        this.preference = new String[3];
        //Fetch les amis, les dispos, comment retrouver les favs
    }

    public void addFriend(User user){
        DatabaseHandler db = new DatabaseHandler(this);
        Date today = new Date();//Se crée au moment courant
        Requete toSend = new Requete(0, this.getId(), user.getId(), today);
        db.ajouterRequete(toSend);
    }

    public void acceptRequest(Requete requete){
        DatabaseHandler db = new DatabaseHandler(this);
        User toAdd = db.getUser(requete.getExpediteur());//Récupérer l'utilisateur qui a envoyé la requete
        requete.setStatut(true);//Passer le statut de la requête à true
        db.modifierRequete(requete);//Enregistrer dans la base de donnée
        amis.add(toAdd);//Ajouter à la liste d'amis
    }

    public void addFav(User user){
        favoris.add(user);
    }

    public void removeFriend(User user){
        amis.remove(user);
        favoris.remove(user);
    }

    /**
     * Update les infos d'un utilisateur
     *
     * @param infos contient MDP, nom, genre, age, styleCapillaire, couleurYeux, villeResidence,
     *              orientationSexuelle, dans cette ordre
     */
    public void setInfos(String[] infos){
        DatabaseHandler db = new DatabaseHandler(this);
        if(infos.length != 8){
            //Un problème, le gérer
        }
        else{
            this.setMdp(infos[0]);
            this.setNom(infos[1]);
            this.setGenre(infos[2]);
            this.setAge(Integer.parseInt(infos[3]));
            this.setCheveux(infos[4]);
            this.setYeux(infos[5]);
            this.setVille(infos[6]);
            this.setOrientation(infos[7]);
            db.modifierUser(this);
        }
    }

    public void setPrivate(String[] infos){
        //TODO : Trouver comment différencier ça dans la db
    }

    public boolean connect(String mdp){
        if(mdp == this.getMdp()){
            return true;
        }
        return false;
    }

    public boolean match(User toMatch){//Faire une sélection qui évite les requêtes déjà acceptées ou refusées
        switch (this.getOrientation()){
            case "Hétérosexuel" :
                if(toMatch.getGenre().equals("Homme")){
                    return false;
                }
                break;
            case "Homosexuel" :
                if(!toMatch.getGenre().equals("Homme")){
                    return false;
                }
                break;
            case "Hétérosexuelle" :
                if(!toMatch.getGenre().equals("Homme")){
                    return false;
                }
                break;
            case "Homosexuelle" :
                if(toMatch.getGenre().equals("Homme")){
                    return false;
                }
                break;
            default://Gère les deux cas bisexuel, où les deux genre sont bons
                break;
        }

        String[] prefs = this.getPreference();
        if(!prefs[0].equals("") && toMatch.getAge()>Integer.parseInt(prefs[0])){//Trop agé
            return false;
        }
        if(!prefs[0].equals("") && !toMatch.getCheveux().equals(prefs[1])){//Cheveux
            return false;
        }
        if(!prefs[0].equals("") && !toMatch.getYeux().equals(prefs[2])){//Yeux
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String newMdp) {
        this.mdp = newMdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getYeux() {
        return couleurYeux;
    }

    public void setYeux(String yeux) {
        this.couleurYeux = yeux;
    }

    public String getCheveux() {
        return styleCapilaire;
    }

    public void setCheveux(String cheveux) {
        this.styleCapilaire = cheveux;
    }

    public String getOrientation() {
        return orientationSexuelle;
    }

    public void setOrientation(String orientation) {
        this.orientationSexuelle = orientation;
    }

    public String getVille() {
        return villeResidence;
    }

    public void setVille(String ville){
        this.villeResidence=ville;
    }

    public String getPhoto() {
        return photo[0];
    }

    public String[] getPreference(){
        return preference;
    }

    /**
     * Update les infos d'un utilisateur
     *
     * @param pref contient age, styleCapillaire, couleurYeux dans cette ordre
     */
    public void setPreference(String[] pref){
        DatabaseHandler db = new DatabaseHandler(this);
        if(pref.length != 3){
            //Un problème, le gérer
        }
        else{
            this.preference=pref;
        }
    }

    public void addPhoto(String photo){
        //Add, arraylist ?
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User temp = (User) obj;
            return(temp.getId()== this.id);
        }
        return false;
    }
}
