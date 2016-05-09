package ucloveproject.uclove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.Photo;
import ucloveproject.uclove.DB.Relation;
import ucloveproject.uclove.DB.Requete;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Created by Céline on 06-05-16.
 */
public class DetailRequete extends MyActivity implements View.OnClickListener {
    private ImageButton oui;
    private ImageButton non;
    private ImageButton back;
    private User current;
    private User temp;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Bundle extras = getIntent().getExtras();
        db = new DatabaseHandler(this);
        if (extras != null) {
            String username = extras.getString("username");
            this.current = db.getUser(username);
            String otherName = extras.getString("request");
            temp = db.getUser(otherName);
        }
        showPeople();
        this.addListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                Requete req = db.getOneRequete(current.getId(), temp.getId());
                req.setStatut("valide");
                db.modifierRequete(req);
                Relation newRel = new Relation(0, current.getId(), temp.getId());
                db.ajouterRelation(newRel);
                Relation newRel2 = new Relation(0, temp.getId(), current.getId());
                db.ajouterRelation(newRel2);
                Intent i = new Intent(this, FriendRequests.class);
                i.putExtra("username", current.getLogin());
                startActivity(i);
                break;
            case R.id.button_next:
                Requete req2 = db.getOneRequete(current.getId(), temp.getId());
                req2.setStatut("rejet");
                db.modifierRequete(req2);
                Intent j = new Intent(this, FriendRequests.class);
                j.putExtra("username", current.getLogin());
                startActivity(j);
                break;
            case R.id.btn_back:
                Intent k = new Intent(this, FriendRequests.class);
                k.putExtra("username", current.getLogin());
                startActivity(k);
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
    }


    /**
     * Affiche le profil en utilisant le layout activity_profil
     */
    public void showPeople() {
        TextView pseudo = (TextView) findViewById(R.id.user_login);
        TextView genre = (TextView) findViewById(R.id.user_gender);
        TextView cheveux = (TextView) findViewById(R.id.user_hair);
        TextView location = (TextView) findViewById(R.id.user_location);
        TextView age = (TextView) findViewById(R.id.user_age);
        TextView yeux = (TextView) findViewById(R.id.user_eyes);
        TextView orientation = (TextView) findViewById(R.id.user_inclination);
        ImageView Photo = (ImageView) findViewById(R.id.img_user);
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
    }
}
