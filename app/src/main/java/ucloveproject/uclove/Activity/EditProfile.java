package ucloveproject.uclove.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;


/**
 * Classe User, cette activité permet de montrer le profil d'un utilisateur
 * Created by Donatien on 25/04/2016.
 */
public class EditProfile extends MyActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String username;
    private Button confirm;
    private Spinner spinnerGenre;
    private Spinner spinnerHair;
    private Spinner spinnerEyes;
    private Spinner spinnerIncli;
    //TODO : mettre les infos actuelles en selectionne de base
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            username = extras.getString("username");
        setSpinners();
        this.addListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_valider_change:
                DatabaseHandler db = new DatabaseHandler(this);
                User current = db.getUser(username);
                current.setGenre((String)spinnerGenre.getSelectedItem());
                current.setCheveux((String) spinnerHair.getSelectedItem());
                current.setYeux((String) spinnerEyes.getSelectedItem());
                current.setOrientation((String) spinnerIncli.getSelectedItem());
                EditText age = (EditText) findViewById(R.id.age_field) ;
                try {
                    current.setAge(Integer.parseInt(age.getText().toString()));
                }catch (NumberFormatException e){
                    current.setAge(0);
                }
                EditText ville = (EditText) findViewById(R.id.edit_location) ;
                current.setVille(ville.getText().toString());
                db.modifierUser(current);
                Intent i = new Intent(this, UserProfil.class);
                i.putExtra("username", username);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * Ajoute aux boutons du profil leur fonction
     */
    public void addListener() {
        confirm = (Button) findViewById(R.id.btn_valider_change);
        confirm.setOnClickListener(this);
    }


    public void setSpinners(){
        // Spinner pour genre
        spinnerGenre = (Spinner) findViewById(R.id.spinner_gender);
        // Spinner Drop down elements
        List<String> categoriesG = new ArrayList<String>();
        categoriesG.add("Women");
        categoriesG.add("Men");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesG);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerGenre.setAdapter(dataAdapter);

        // Spinner pour Hair
        spinnerHair = (Spinner) findViewById(R.id.spinner_hair);
        List<String> categoriesH = new ArrayList<String>();
        categoriesH.add("Brown");
        categoriesH.add("Black");
        categoriesH.add("Blond");
        categoriesH.add("Red");
        ArrayAdapter<String> dataAdapterH = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesH);
        dataAdapterH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHair.setAdapter(dataAdapterH);

        // Spinner pour Eyes
        spinnerEyes = (Spinner) findViewById(R.id.spinner_eye);
        List<String> categoriesE = new ArrayList<String>();
        categoriesE.add("Brown");
        categoriesE.add("Blue");
        categoriesE.add("Green");
        categoriesE.add("Gray");
        categoriesE.add("Black");
        ArrayAdapter<String> dataAdapterE = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesE);
        dataAdapterE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEyes.setAdapter(dataAdapterE);

        // Spinner pour Eyes
        spinnerIncli = (Spinner) findViewById(R.id.spinner_inclinaison);
        List<String> categoriesI = new ArrayList<String>();
        categoriesI.add("Heterosexual");
        categoriesI.add("Homosexual");
        categoriesI.add("Bisexual");
        ArrayAdapter<String> dataAdapterI = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesI);
        dataAdapterI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIncli.setAdapter(dataAdapterI);
    }
}