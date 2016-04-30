package ucloveproject.uclove.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;


/**
 * Classe User, cette activité permet de montrer le profil d'un utilisateur
 * Created by Donatien on 25/04/2016.
 */
public class UserProfil extends MyActivity implements View.OnClickListener {

    private Button chgGender;
    private Button chgAge;
    private Button chgHair;
    private Button chgEyes;
    private Button chgLocation;
    private Button chgInclination;
    private Button chgPicture;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        this.addListener();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Chg_Gender:
                this.messBtnChgGender();
                break;
            case R.id.button_Chg_Age:
                this.messBtnChgAge();
                break;
            case R.id.button_Chg_Hair:
                this.messBtnChgHair();
                break;
            case R.id.button_Chg_Eyes:
                this.messBtnChgEyes();
                break;
            case R.id.button_Chg_Location:
                this.messBtnChgLocation();
                break;
            case R.id.button_Chg_Inclination:
                this.messBtnChgInclination();
                break;
            case R.id.button_Chg_Picture:
                this.messBtnChgPicture();
                break;
            case R.id.button_back:
                finish();
                break;
        }
    }

    /**
     * Ajoute aux boutons du profil leur fonction
     */
    public void addListener() {
        chgGender = (Button) findViewById(R.id.button_Chg_Gender);
        chgGender.setOnClickListener(this);

        chgAge =(Button) findViewById(R.id.button_Chg_Age);
        chgAge.setOnClickListener(this);

        chgHair = (Button) findViewById(R.id.button_Chg_Hair);
        chgHair.setOnClickListener(this);

        chgEyes = (Button) findViewById(R.id.button_Chg_Eyes);
        chgEyes.setOnClickListener(this);

        chgLocation = (Button) findViewById(R.id.button_Chg_Location);
        chgLocation.setOnClickListener(this);

        chgInclination = (Button) findViewById(R.id.button_Chg_Inclination);
        chgInclination.setOnClickListener(this);

        chgPicture = (Button) findViewById(R.id.button_Chg_Picture);
        chgPicture.setOnClickListener(this);

        back = (Button) findViewById(R.id.button_back);
        back.setOnClickListener(this);
    }

    public void messBtnChgGender(){
        AlertDialog alertGender = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle).create();
        alertGender.setTitle("Choose my gender");
        alertGender.setMessage("Please select your gender");

        alertGender.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertGender.show();
    }

    public void messBtnChgAge(){
        AlertDialog alertAge = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle).create();
        alertAge.setTitle("Choose my age");
        alertAge.setMessage("Please enter your age");

        alertAge.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertAge.show();
    }

    public void messBtnChgHair(){
        AlertDialog alertHair = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle).create();
        alertHair.setTitle("Choose my hair type");
        alertHair.setMessage("Please enter your hair type");

        alertHair.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertHair.show();
    }

    public void messBtnChgEyes(){
        AlertDialog alertEyes = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle).create();
        alertEyes.setTitle("Choose my eyes type");
        alertEyes.setMessage("Please enter your eyes type");

        alertEyes.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertEyes.show();
    }

    public void messBtnChgLocation(){
        AlertDialog alertLocation = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle).create();
        alertLocation.setTitle("Choose my location");
        alertLocation.setMessage("Please enter your location");

        alertLocation.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertLocation.show();
    }

    public void messBtnChgInclination(){
        AlertDialog alertInclination = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle).create();
        alertInclination.setTitle("Choose my inclination");
        alertInclination.setMessage("Please choose your inclination");

        alertInclination.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertInclination.show();
    }

    public void messBtnChgPicture(){
        AlertDialog alertPicture = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle).create();
        alertPicture.setTitle("Choose my picture");
        alertPicture.setMessage("Please select a picture");

        alertPicture.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertPicture.show();
    }


    /**
     * Affiche le profil en utilisant le layout activity_profil
     */
    public void showProfil() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String username = extras.getString("username");
            DatabaseHandler db = new DatabaseHandler(this);
            User current = db.getUser(username);//Le nom ambigu force ça
            //Utiliser les get current pour l'affichage
        }
        //A compléter
    }
}
