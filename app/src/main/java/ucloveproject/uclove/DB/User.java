package ucloveproject.uclove.DB;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
    private boolean privacyNom;
    private boolean privacyVille;
    private boolean privacyPhoto;
    private ArrayList<Photo> photo;
    private ArrayList<Relation> amis;

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
    }

    public User(String username, String password){
        this.id = 0;
        this.login = username;
        this.mdp = password;
        this.nom = "inconnu";
        this.genre = "inconnu";
        this.age=0;
        this.styleCapilaire = "inconnu";
        this.couleurYeux = "inconnu";
        this.villeResidence = "inconnu";
        this.orientationSexuelle = "inconnu";
        this.preference = new String[3];
    }

    public void setFriendList(DatabaseHandler db){
        this.amis = db.getFriendList(this.getId());

    }

    public ArrayList<String> getFriendNames(DatabaseHandler db){
        ArrayList<String> result = new ArrayList<String>();
        Iterator<Relation> friendIterator = this.amis.iterator();
        while(friendIterator.hasNext()) {
            Relation token = friendIterator.next();
            User addToList = db.getUserById(token.getSecondUser());
            result.add(addToList.getLogin());
        }
        return result;
    }

    public ArrayList<String> getFavNames(DatabaseHandler db){
        ArrayList<String> result = new ArrayList<String>();
        Iterator<Relation> friendIterator = this.amis.iterator();
        while(friendIterator.hasNext()) {
            Relation token = friendIterator.next();
            if(token.getFav()) {
                User addToList = db.getUserById(token.getSecondUser());
                result.add(addToList.getLogin());
            }
        }
        return result;
    }

    public ArrayList<String> getConvWith(DatabaseHandler db, int corr){
        ArrayList<Message> messages = db.findConv(this.getId(), corr);
        ArrayList<String> result = new ArrayList<>();
        Iterator iter = messages.iterator();
        Message previous=null;
        while(iter.hasNext()){
            Message temp = (Message)iter.next();
            User temp2 = db.getUserById(temp.getExpediteur());
            if(previous != null) {
                if(previous.getExpediteur() != temp.getExpediteur()) {
                    result.add(temp2.getLogin() + " :");
                }
            }
            else {
                result.add(temp2.getLogin() + " :");
            }
            result.add(temp.getContenu());
            previous = temp;
        }
        return result;
    }

    public ArrayList<Relation> getFriendList(){
        return this.amis;
    }

    public void setFav(DatabaseHandler db, User user){
        Relation toUpdate = db.getOneFriend(this.getId(), user.getId());
        toUpdate.setFav(true);
        db.modifierRelation(toUpdate);
    }

    public void setPrivacy(boolean nom, boolean ville, boolean photo){
        this.privacyNom = nom;
        this.privacyVille = ville;
        this.privacyPhoto = photo;
    }

    public boolean getPrivacyNom(){
        return this.privacyNom;
    }

    public boolean getPrivacyVille(){
        return this.privacyVille;
    }

    public boolean getPrivacyPhoto(){
        return this.privacyPhoto;
    }

    public void setPhotos(DatabaseHandler db){
        this.photo = db.getPhotoByUserId(this.getId());
    }

    public void addFriend(User user, DatabaseHandler db){
        Date today = new Date();//Se crée au moment courant
        Requete toSend = new Requete(0, this.getId(), user.getId(), String.valueOf(today));
        db.ajouterRequete(toSend);
    }

    public void acceptRequest(Requete requete, DatabaseHandler db){
        User toAdd = db.getUserById(requete.getExpediteur());//Récupérer l'utilisateur qui a envoyé la requete
        requete.setStatut("valide");//Passer le statut de la requête à valide
        db.modifierRequete(requete);//Enregistrer dans la base de donnée
        Relation newRelation = new Relation(0, this.getId(), toAdd.getId());
        db.ajouterRelation(newRelation);
    }


    public void removeFriend(User user, DatabaseHandler db){
        Relation toRemove = db.getOneFriend(this.getId(), user.getId());
        db.supprimerRelation(toRemove.getId());
        //TODO : Supprimer la requete correspondante
    }

    public boolean connect(String mdp){
        if(mdp.equals(this.getMdp())){
            return true;
        }
        return false;
    }

    public boolean match(User toMatch){//Faire une sélection qui évite les requêtes déjà acceptées ou refusées
        switch (this.getOrientation()){//Prendre en compte l'orientation sexuelle
            case "Hetero" :
                switch (this.getGenre()){
                    case "Women" :
                        if(!toMatch.getGenre().equals("Men")){
                            return false;
                        }
                        break;
                    case "Men":
                        if(toMatch.getGenre().equals("Men")){
                            return false;
                        }
                        break;
                }
                break;
            case "Homo" :
                switch (this.getGenre()){
                    case "Women" :
                        if(toMatch.getGenre().equals("Men")){
                            return false;
                        }
                        break;
                    case "Men":
                        if(!toMatch.getGenre().equals("Men")){
                            return false;
                        }
                        break;
                }
                break;
            default://Gère les deux cas bisexuel, où les deux genre sont bons
                break;
        }

        String[] prefs = this.getPreference();
        if(prefs[0]!=null) {
            if (!prefs[0].equals("") && toMatch.getAge() > Integer.parseInt(prefs[0])) {//Trop agé
                return false;
            }
        }
        if(prefs[1] !=null) {
            if (!prefs[1].equals("") && !toMatch.getCheveux().equals(prefs[1])) {//Cheveux
                return false;
            }
        }
        if(prefs[2] != null) {
            if (!prefs[2].equals("") && !toMatch.getYeux().equals(prefs[2])) {//Yeux
                return false;
            }
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

    public Photo getPhoto() {
        return photo.get(0);
    }

    public String[] getPreference(){
        return preference;
    }

    /**
     * Update les infos d'un utilisateur
     *
     * @param prefAge, prefCh, prefY
     */
    public void setPreference(String prefAge, String prefCh, String prefY){
        this.preference[0] = prefAge;
        this.preference[1] = prefCh;
        this.preference[2] = prefY;
    }


    public void addPhoto(Bitmap photo, DatabaseHandler db){
        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //photo.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] image = null;//stream.toByteArray();//BitmapFactory.decodeByteArray(photo, 0, image.length);//Non fonctionnel pour le moment, faire des recherches sur les images
        Photo toAdd = new Photo(0, this.getId(), image);
        db.ajouterPhoto(toAdd);
        this.photo.add(toAdd);
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
