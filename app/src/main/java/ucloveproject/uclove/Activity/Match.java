package ucloveproject.uclove.Activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.Requete;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Classe montrant les personne ayant ajouté le User
 * Created by Céline on 05-05-16.
 */
public class Match extends MyActivity implements View.OnClickListener {

    private ImageButton oui;
    private ImageButton non;
    private ImageButton back;
    private Button search;
    private Spinner filtre;
    private User current;
    private User matched;
    private ArrayList<User> users;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Bundle extras = getIntent().getExtras();
        db = new DatabaseHandler(this);
        if (extras != null) {
            String username = extras.getString("username");
            this.current = db.getUser(username);
        }
        setSpinner();
        getPossibleMatchs();
        this.addListener();
    }

    @Override
    public void onClick(View v) {
        Date today = new Date();
        switch (v.getId()) {
            case R.id.button_add:
                Requete req = new Requete(0, current.getId(), matched.getId(), String.valueOf(today));
                db.ajouterRequete(req);
                users.remove(matched);
                showPeople();
                break;
            case R.id.button_next:
                Requete reqNon = new Requete(0, current.getId(), matched.getId(), String.valueOf(today));
                reqNon.setStatut("rejet");
                db.ajouterRequete(reqNon);
                users.remove(matched);
                showPeople();
                break;
            case R.id.btn_back:
                Intent j = new Intent(this, Menu.class);
                j.putExtra("username", current.getLogin());
                startActivity(j);
                break;
            case R.id.button_search:
                Log.d("Search", "");
                EditText search = (EditText) findViewById(R.id.hint_value);
                if(!search.getText().toString().equals(null)){
                    String filterSt = search.getText().toString();
                    String choix = (String)filtre.getSelectedItem();
                    switch (choix){
                        case "Eyes":
                            current.setPreference("", "", filterSt);
                            break;
                        case "Hair":
                            current.setPreference("", filterSt, "");
                            break;
                        case "Age":
                            current.setPreference(filterSt,"", "");
                            break;
                    }
                }
                showPeople();
                break;
        }
    }

    /**
     * Ajoute aux boutons du profil leur fonction
     */
    public void addListener() {
        oui = (ImageButton) findViewById(R.id.button_next);
        oui.setOnClickListener(this);

        non = (ImageButton) findViewById(R.id.button_add);
        non.setOnClickListener(this);

        back = (ImageButton) findViewById(R.id.btn_back);
        back.setOnClickListener(this);

        search = (Button) findViewById(R.id.button_search);
        search.setOnClickListener(this);
    }

    public void getPossibleMatchs(){
        users = db.getAllUsers();//Tous les users
        users.remove(current);//Enlever l'user courant
        ArrayList<String> friends = current.getFriendNames(db);
        Iterator<String> friendIter = friends.iterator();
        while (friendIter.hasNext()){//Enlever les amis
            String temp = friendIter.next();
            User toRemove = db.getUser(temp);
            users.remove(toRemove);
        }
        ArrayList<Requete> requetes = db.getAllRequetes(current.getId());
        Iterator<Requete> requeteIter = requetes.iterator();
        while (requeteIter.hasNext()) {//Enlever les gens qui ont déjà des requêtes en cours
            Requete temp = requeteIter.next();
            if (temp.getDestinataire() != current.getId()) {
                User toRemove = db.getUserById(temp.getDestinataire());
                users.remove(toRemove);
            } else {
                User toRemove = db.getUserById(temp.getExpediteur());
                users.remove(toRemove);
            }
        }
    }

    public void setSpinner(){
        filtre = (Spinner) findViewById(R.id.spinner_keyword);
        List<String> categoriesH = new ArrayList<String>();
        categoriesH.add("Hair");
        categoriesH.add("Eyes");
        categoriesH.add("Age");
        ArrayAdapter<String> dataAdapterH = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesH);
        dataAdapterH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtre.setAdapter(dataAdapterH);
    }


    /**
     * Affiche le profil en utilisant le layout activity_profil
     */
    public void showPeople() {
        ArrayList<User> copy = (ArrayList<User>) users.clone();
        Iterator<User> iter = copy.iterator();
        TextView pseudo = (TextView) findViewById(R.id.user_login);
        TextView genre = (TextView) findViewById(R.id.user_gender);
        TextView cheveux = (TextView) findViewById(R.id.user_hair);
        TextView location = (TextView) findViewById(R.id.user_location);
        TextView age = (TextView) findViewById(R.id.user_age);
        TextView yeux = (TextView)findViewById(R.id.user_eyes);
        TextView orientation = (TextView) findViewById(R.id.user_inclination);
        ImageView Photo = (ImageView) findViewById(R.id.img_user);
        while (iter.hasNext()){
                User temp = iter.next();
                if (current.match(temp)) {
                    this.matched = temp;
                    pseudo.setText(temp.getLogin());
                    genre.setText("Genre : " + temp.getGenre());
                    cheveux.setText("Hair : " + temp.getCheveux());
                    location.setText("Location : " + temp.getVille());
                    age.setText("Age : " + temp.getAge());
                    yeux.setText("Eyes : " + temp.getYeux());
                    orientation.setText("Inclinaison : " + temp.getOrientation());
                    ArrayList<ucloveproject.uclove.DB.Photo> pics = db.getPhotoByUserId(temp.getId());
                    if (pics.size()!=0) {
                        Photo.setImageBitmap(db.getPhotoByUserId(temp.getId()).get(0).getImage());
                        printToast("dans le if !");
                    }
                    return;
                } else {
                    users.remove(temp);
                }
        }
        pseudo.setText("Sorry, no match");
        age.setText("");
        genre.setText("");
        cheveux.setText("");
        location.setText("");
        yeux.setText("");
        orientation.setText("");
        oui.setVisibility(View.INVISIBLE);
        non.setVisibility(View.INVISIBLE);
        Photo.setVisibility(View.INVISIBLE);
    }
}
