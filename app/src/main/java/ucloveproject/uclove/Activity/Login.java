package ucloveproject.uclove.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * classe Login permettant de de se connecter et dans le cas où l'utilisateur n'aurait pas de compte, en créer un
 * Created by Donatien on 30/04/2016.
 */
public class Login extends MyActivity implements View.OnClickListener {

    private Button btnInscr = null;
    private Button btnConnecter = null;
    private String username = null;
    private String password= null;

    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.addListener();
    }


    private void addListener(){
        btnInscr = (Button) findViewById(R.id.btn_A_Inscription);
        btnInscr.setOnClickListener(this);
        btnConnecter = (Button) findViewById(R.id.btn_Connexion);
        btnConnecter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_A_Inscription:
                Intent i = new Intent(this, Inscription.class);
                startActivity(i);
                break;
            case R.id.btn_Connexion:
                this.launchConnexion();
        }
    }


    private void launchConnexion(){
        EditText editUser  = (EditText)findViewById(R.id.login_field);
        EditText editPassword   = (EditText)findViewById(R.id.password_field);

        username = editUser.getText().toString();
        password = editPassword.getText().toString();
        DatabaseHandler db = new DatabaseHandler(this);
        User toConnect= db.getUser(username);
        if(toConnect != null) {
            boolean isValid = toConnect.connect(password);
            if (isValid) {
                Intent i = new Intent(this, Menu.class);//Plus tard, rediriger vers le menu
                i.putExtra("username", username);
                startActivity(i);
            } else {
                printToast("Wrong password");
            }
        }
        else{
            printToast("Wrong username");
        }
    }



}
