package ucloveproject.uclove.DB;

import java.util.ArrayList;

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
        super();//Virer ?
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
    }

    public void addFriend(User user){
        //TODO, envoyer la demande
    }

    public void addtoFriendList(User user){
        amis.add(user);
    }

    public void addFav(User user){
        favoris.add(user);
    }

    public void removeFriend(User user){
        amis.remove(user);
        favoris.remove(user);
    }

    public void setInfos(String[] infos){
        //TODO : Décider du format de infos, pouv voir comment utiliser les get et set puis appeler "modifierUser" de databaseHandler
    }

    public void setPrivate(String[] infos){
        //TODO : Idem
    }

    public int connect(String login, String mdp){
        //TODO Log in
        return 0;
    }

    public boolean match(String info){//On ne matcherait pas plutôt un User ?
        //TODO : match selon les préférences
        return false;
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
