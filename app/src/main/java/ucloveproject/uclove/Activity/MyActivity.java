package ucloveproject.uclove.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Principal;

import ucloveproject.uclove.R;

/**
 * Created by Donatien on 25/04/2016.
 */
public abstract class MyActivity extends Activity {

    private String idUser;
    private String hash;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    /**
     * Fonction appelée après la validation de la connexion ou de l'inscription
     * @param username le pseudo de la personne qui se connecte
     *
     */
    protected void validConnexion(String username){
        SharedPreferences log= getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor edit =log.edit();
        edit.putString("username", username);
        edit.putString("hash",this.hash);
        edit.putString("idUser", idUser);
        edit.apply();

        SharedPreferences prefs = getSharedPreferences("SIP", MODE_PRIVATE);
        SharedPreferences.Editor editSip = prefs.edit();
        editSip.putString("username", Integer.toString(6000 + (Integer.parseInt(this.idUser)) ));
        editSip.putString("password", this.idUser);
        editSip.putString("idUser", this.idUser);

        Intent i1= new Intent(this, Principal.class);
        startActivity(i1);
    }

    public void errorMessage(String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Erreur !");
        alertDialog.setMessage(message);
        alertDialog.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    /**
     * Affiche un toast avec message pour texte
     * @param message Texte affiché
     */
    public void printToast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.linear_toast_layout)); //Utilise le layout custom_toast pour être display
        Toast toast = new Toast(this);
        TextView txt = (TextView) layout.findViewById(R.id.textView_toast);
        txt.setText(message);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void finish(){
        super.finish();
    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    public void setHash(String hash){
        this.hash=hash;
    }

    public void setIdUser(String idUser){
        this.idUser=idUser;
    }
}
