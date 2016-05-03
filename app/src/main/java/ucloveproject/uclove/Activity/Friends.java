package ucloveproject.uclove.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Created by Robin on 05/03/2016.
 */

public class Friends extends MyActivity implements View.OnClickListener {

    private CheckBox btnFav = null;
    private String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            username = extras.getString("username");

        btnFav = (CheckBox) findViewById(R.id.show_fav);

    }
}