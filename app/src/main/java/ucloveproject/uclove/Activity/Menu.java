package ucloveproject.uclove.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import ucloveproject.uclove.R;

/**
 * Created by Donatien on 02/05/2016.
 */
public class Menu extends MyActivity implements View.OnClickListener {

    private Button profile;
    private Button preferences;
    private Button people;
    private Button friends;
    private Button requests;
    private Button chat;
    private Button meet;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_menu);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            username = extras.getString("username");
        this.addListener();
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu_profile:
                Intent i = new Intent(this, UserProfil.class);
                i.putExtra("username", username);
                startActivity(i);
                break;
            case R.id.btn_menu_preferences:
                //Intent j = new Intent(this, Preferences.class);
                //i.putExtra("username", username);
                //startActivity(j);
                break;
            case R.id.btn_menu_people:
                //Intent k = new Intent(this, People.class);
                //i.putExtra("username", username);
                //startActivity(k);
                break;
            case R.id.btn_menu_friends:
                //Intent l = new Intent(this, Friends.class);
                //i.putExtra("username", username);
                //startActivity(l);
                break;
            case R.id.btn_menu_requests:
                //Intent m = new Intent(this, Requests.class);
                //i.putExtra("username", username);
                //startActivity(m);
                break;
            case R.id.btn_menu_chat:
                //Intent n = new Intent(this, Chat.class);
                //i.putExtra("username", username);
                //startActivity(n);
                break;
            case R.id.btn_menu_meet:
                //Intent o = new Intent(this,Meet.class);
                //i.putExtra("username", username);
                //startActivity(o);
                break;
        }
    }

    private void addListener() {
    //TODO
    }

}
