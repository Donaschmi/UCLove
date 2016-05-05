package ucloveproject.uclove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.Requete;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Created by Céline on 05-05-16.
 */
public class Match extends MyActivity implements View.OnClickListener {

    private Button oui;
    private Button non;
    private Button back;
    private User current;
    private User matched;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Bundle extras = getIntent().getExtras();
        db = new DatabaseHandler(this);
        if (extras != null) {
            String username = extras.getString("username");
            this.current = db.getUser(username);
        }
        showPeople();
        this.addListener();
    }

    @Override
    public void onClick(View v) {
        Date today = new Date();
        switch (v.getId()) {
            case R.id.yes:
                Requete req = new Requete(0, current.getId(), matched.getId(), String.valueOf(today));
                db.ajouterRequete(req);
                //Goto user next
                break;
            case R.id.noo:
                Requete reqNon = new Requete(0, current.getId(), matched.getId(), String.valueOf(today));
                reqNon.setStatut("rejet");
                db.ajouterRequete(reqNon);
                //Goto user next
                break;
            case R.id.btn_back:
                Intent j = new Intent(this, Menu.class);
                j.putExtra("username", current.getLogin());
                startActivity(j);
                break;
        }
    }

    /**
     * Ajoute aux boutons du profil leur fonction
     */
    public void addListener() {
        oui = (Button) findViewById(R.id.yes);
        oui.setOnClickListener(this);

        non = (Button) findViewById(R.id.noo);
        non.setOnClickListener(this);

        back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(this);
    }


    /**
     * Affiche le profil en utilisant le layout activity_profil
     */
    public void showPeople() {
        ArrayList<User> users = db.getAllUsers();
        Iterator<User> iter = users.iterator();
        TextView pseudo = (TextView) findViewById(R.id.textView10);
        TextView age = (TextView) findViewById(R.id.textView12);
        while (iter.hasNext()){
            User temp = iter.next();
            Log.d("User found : ", temp.getLogin());
            if(current.match(temp)){
                this.matched = temp;
                pseudo.setText("Pseudo : " + temp.getLogin());
                age.setText("Age : "+temp.getAge());
                return;
            }
        }
        pseudo.setText("Désolé, aucun match");
        age.setText("");
    }
}
