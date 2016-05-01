package ucloveproject.uclove.Activity;

import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Created by Donatien on 30/04/2016.
 */
public class Inscription extends MyActivity implements View.OnClickListener{

    private Button btnIns=null;
    private String username=null;
    private String password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        SharedPreferences log = getSharedPreferences("Login", MODE_PRIVATE);
        if(log.getString("username","n/a")!="n/a"){
            Intent i =new Intent(this, Login.class);
            startActivity(i);
        }

        btnIns = (Button) findViewById(R.id.btn_Inscription);
        btnIns.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_Inscription:
                this.verificationInscr();
                Intent i = new Intent(this, UserProfil.class);//Plus tard, rediriger vers le menu
                startActivity(i);
                break;
        }
    }

    private void verificationInscr(){
        EditText editUser=(EditText) findViewById(R.id.login_field);
        EditText editPassword=(EditText) findViewById(R.id.password_field);

        username=editUser.getText().toString();
        password=editPassword.getText().toString();

        if(this.verifChamp()){
            User newUser = new User(username, password);
            DatabaseHandler db = new DatabaseHandler(this);
            db.ajouterUser(newUser);
        }
    }


    private boolean verifChamp(){
        boolean ok = false;

        if(isUserValid()){

            if(isPasswordValid()){
                ok=true;
            }
            else{
                String errorMessage="Password must be between 6 and 40 characters";
                super.printToast(errorMessage);
            }
        }
        else{
            String errorMessage="Username must be between 4 and 50 characters";
            super.printToast(errorMessage);
        }
        return ok;
    }

    private boolean isUserValid(){
        return (this.username.length() >=4)&&(this.username.length()<=50);
    }

    private boolean isPasswordValid(){
        return (this.password.length() >=6)&&(this.password.length()<=40);
    }



}
