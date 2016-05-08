package ucloveproject.uclove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.Relation;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 *
 * Created by Céline on 06-05-16.
 */
public class FriendMenu extends MyActivity implements View.OnClickListener {

    private Button btnFav;
    private Button btnChat;
    private Button btnMeet;
    private Button btnDelete;
    private String friend;
    private String username;
    private User current;
    private User other;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.friend_menu);
        this.db = new DatabaseHandler(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            username = extras.getString("username");
            current = db.getUser(username);
            friend = extras.getString("friend");
            other = db.getUser(friend);
        }
        TextView nomAmi = (TextView) findViewById(R.id.login_ami) ;
        nomAmi.setText(other.getLogin());
        this.addListener();
    }

    private void addListener() {
        btnFav =(Button) findViewById(R.id.btn_fav);
        btnFav.setOnClickListener(this);

        btnDelete = (Button)findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);

        btnChat = (Button) findViewById(R.id.btn_chat);
        btnChat.setOnClickListener(this);

        btnMeet= (Button) findViewById(R.id.btn_rdv);
        btnMeet.setOnClickListener(this);
        }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_fav:
                Relation toUpdate = db.getOneFriend(current.getId(), other.getId());
                if(toUpdate.getFav()){
                    printToast("Cet utilisateur figure déjà dans vos favoris");
                }
                else {
                    toUpdate.setFav(true);
                }
                db.modifierRelation(toUpdate);
                break;
            case R.id.btn_delete:
                Relation toDelete = db.getOneFriend(current.getId(), other.getId());
                Relation toDelete2 = db.getOneFriend(other.getId(), current.getId());
                db.supprimerRelation(toDelete.getId());
                db.supprimerRelation(toDelete2.getId());
                Intent j = new Intent(this, Friends.class);
                j.putExtra("username", username);
                startActivity(j);
                break;
            case R.id.btn_chat:
                Intent k = new Intent(this, ChatWith.class);
                k.putExtra("username", username);
                k.putExtra("correspondant", friend);
                startActivity(k);
                break;
            case R.id.btn_back:
                Intent l = new Intent(this, Friends.class);
                l.putExtra("username", username);
                startActivity(l);
                break;
            case R.id.btn_rdv:
                //TODO
                //Intent l = new Intent(this, Friends.class);
                //l.putExtra("username", username);
                //startActivity(l);
                break;

        }
    }
}
