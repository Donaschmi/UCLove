package ucloveproject.uclove.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;


/**
 * Classe User, cette activité permet de montrer le profil d'un utilisateur
 * Created by Donatien on 25/04/2016.
 */
public class UserProfil extends MyActivity implements View.OnClickListener {

    private Button edit;
    private ImageButton back;
    private String username;
    private ImageView imageView;
    private Button changePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            username = extras.getString("username");
        showProfil();
        this.addListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                Intent i = new Intent(this, EditProfile.class);
                i.putExtra("username", username);
                startActivity(i);
                break;
            case R.id.btn_back:
                Intent j = new Intent(this, Menu.class);
                j.putExtra("username", username);
                startActivity(j);
                break;
            case R.id.button_Chg_Picture:
                imageView.setImageResource(R.mipmap.ajout);
        }
    }

    /**
     * Ajoute aux boutons du profil leur fonction
     */
    public void addListener() {
        edit = (Button) findViewById(R.id.btn_edit);
        edit.setOnClickListener(this);

        back = (ImageButton) findViewById(R.id.btn_back);
        back.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.img_profile);
        imageView.setOnClickListener(this);

        changePicture = (Button) findViewById(R.id.button_Chg_Picture);
        changePicture.setOnClickListener(this);
    }


    /**
     * Affiche le profil en utilisant le layout activity_profil
     */
    public void showProfil() {
        DatabaseHandler db = new DatabaseHandler(this);
        User current = db.getUser(username);
        if (current != null) {
            TextView usernam = (TextView) findViewById(R.id.username);
            usernam.setText(username);
            TextView nom = (TextView) findViewById(R.id.nom);
            nom.setText(current.getNom());
            TextView genre = (TextView) findViewById(R.id.gender);
            genre.setText(current.getGenre());
            TextView age = (TextView) findViewById(R.id.age);
            age.setText(String.valueOf(current.getAge()));
            TextView cheveux = (TextView) findViewById(R.id.hair);
            cheveux.setText(current.getCheveux());
            TextView yeux = (TextView) findViewById(R.id.eyes);
            yeux.setText(current.getYeux());
            TextView ville = (TextView) findViewById(R.id.location);
            ville.setText(current.getVille());
            TextView orientation = (TextView) findViewById(R.id.inclination);
            orientation.setText(current.getOrientation());
        }
    }

}