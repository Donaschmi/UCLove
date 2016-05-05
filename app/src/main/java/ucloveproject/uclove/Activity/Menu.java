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

    private Button btnProfile;
    private Button btnPreferences;
    private Button btnPeople;
    private Button btnFriends;
    private Button btnRequests;
    private Button btnChat;
    private Button btnMeet;
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
    private void addListener() {
        btnProfile =(Button) findViewById(R.id.btn_menu_profile);
        btnProfile.setOnClickListener(this);

        btnPreferences = (Button)findViewById(R.id.btn_menu_preferences);
        btnPreferences.setOnClickListener(this);

        btnPeople = (Button) findViewById(R.id.btn_menu_people);
        btnPeople.setOnClickListener(this);

        btnFriends = (Button) findViewById(R.id.btn_menu_friends);
        btnFriends.setOnClickListener(this);

        btnRequests= (Button) findViewById(R.id.btn_menu_requests);
        btnRequests.setOnClickListener(this);

        btnChat = (Button) findViewById(R.id.btn_menu_chat);
        btnChat.setOnClickListener(this);

        btnMeet= (Button) findViewById(R.id.btn_menu_meet);
        btnMeet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_menu_profile:
                Intent i = new Intent(this, UserProfil.class);
                i.putExtra("username", username);
                startActivity(i);
                break;
            case R.id.btn_menu_preferences:
                Intent j = new Intent(this, Preference.class);
                j.putExtra("username", username);
                startActivity(j);
                break;
            case R.id.btn_menu_people:
                Intent k = new Intent(this, Match.class);
                k.putExtra("username", username);
                startActivity(k);
                break;
            case R.id.btn_menu_friends:
                Intent l = new Intent(this, Friends.class);
                l.putExtra("username", username);
                startActivity(l);
                break;
            case R.id.btn_menu_requests:
                Intent m = new Intent(this, FriendRequests.class);
                m.putExtra("username", username);
                startActivity(m);
                break;
            case R.id.btn_menu_chat:
                //Intent n = new Intent(this, Chat.class);
                //n.putExtra("username", username);
                //startActivity(n);
                break;
            case R.id.btn_menu_meet:
                //Intent o = new Intent(this,Meet.class);
                //o.putExtra("username", username);
                //startActivity(o);
                break;
        }
    }



}
