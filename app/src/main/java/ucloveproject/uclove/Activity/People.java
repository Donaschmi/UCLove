package ucloveproject.uclove.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import ucloveproject.uclove.R;
import android.content.Intent;
/**
 * Created by Donatien on 05/05/2016.
 */
public class People extends MyActivity implements View.OnClickListener {

    private ImageButton back;
    private ImageButton refresh;
    private ImageButton ajout;
    private ImageButton refus;
    private ImageButton right;
    private ImageButton left;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Bundle extras =getIntent().getExtras();
        if (extras != null)
            username = extras.getString("username");

        this.addListener();

    }


    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_back:
                Intent i = new Intent(this, Menu.class);
                i.putExtra("username", username);
                startActivity(i);
                break;
            case R.id.button_filtre:
                //TODO méthode qui trie les USER
                break;
            case R.id.button_random:
                //TODO Renvoyer un autre USER
                break;
            case R.id.button_add:
                //TODO envoyer une requete et retirer de la liste des user dans PEOPLE
                break;
            case R.id.button_next:
                //TODO enlever le USER de PEOPLE et rechercher une autre personne
                break;
            case R.id.img_prev:
                //TODO met la photo précédente si il y'en a une
                break;
            case R.id.img_next:
                //TODO met la photo suivante si il y'en a une
                break;
        }
    }

    private void addListener(){

    }
}
