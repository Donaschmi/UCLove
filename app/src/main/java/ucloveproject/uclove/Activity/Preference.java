package ucloveproject.uclove.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Created by Céline on 03-05-16.
 */
public class Preference extends MyActivity implements View.OnClickListener{

    private RadioButton btnFrench = null;
    private RadioButton btnEnglish = null;
    private Button about = null;
    private String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        SharedPreferences log = getSharedPreferences("Login", MODE_PRIVATE);
        if(log.getString("username","n/a")!="n/a"){
            Intent i =new Intent(this, Login.class);
            startActivity(i);
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null)
            username = extras.getString("username");

        btnFrench = (RadioButton) findViewById(R.id.radio_english);
        btnFrench.setOnClickListener(this);
        btnEnglish = (RadioButton) findViewById(R.id.radio_french);
        btnEnglish.setOnClickListener(this);
        about = (Button) findViewById(R.id.btn_about);
        about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.radio_english:
                //Set l'appli en anglais
                break;
            case R.id.radio_french:
                //Set l'appli en francais
                break;
            case R.id.btn_about:
                //Afficher le about
                break;
        }
    }

}

