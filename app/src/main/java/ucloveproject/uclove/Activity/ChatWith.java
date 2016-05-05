package ucloveproject.uclove.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.Message;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Created by Céline on 05-05-16.
 */
public class ChatWith extends MyActivity implements View.OnClickListener{

    private Button send;
    private Button back;
    private User user;
    private User correspondant;
    private ListView msgList;
    private DatabaseHandler db:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_m);
        db = new DatabaseHandler(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("username");
            this.user = db.getUser(username);
            String correspondantL = extras.getString("correspondant");
            this.correspondant = db.getUser(correspondantL);
        }
        buildList();
        this.addListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                EditText contenuMsg = (EditText) findViewById(R.id.msg);
                String contenu = contenuMsg.getText().toString();
                Date today = new Date();
                Message toSend = new Message(0, contenu, this.user.getId(), this.correspondant.getId(), today);
                db.ajouterMessage(toSend);
                break;
            case R.id.btn_back:
                Intent j = new Intent(this, Menu.class);//On ne devrait pas revenir au menu
                j.putExtra("username", user.getLogin());
                startActivity(j);
                break;
        }
    }

    /**
     * Ajoute aux boutons du profil leur fonction
     */
    public void addListener() {
        send = (Button) findViewById(R.id.btn_send);
        send.setOnClickListener(this);

        back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(this);
    }


    /**
     * Affiche la liste des messages
     */
    public void buildList() {
        msgList = (ListView) findViewById(R.id.msgview);
        ArrayList<String> list = user.getConvWith(db, correspondant.getId());
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        msgList.setAdapter(adapter);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
