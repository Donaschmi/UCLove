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
import java.util.HashMap;
import java.util.List;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Created by Céline on 05-05-16.
 */
public class ChatWith extends MyActivity implements View.OnClickListener{

    private Button send;
    private Button back;
    private String username;
    private String correspondant;
    private ListView msgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_m);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            correspondant = extras.getString("correspondant");
        }
        buildList();
        this.addListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                Intent i = new Intent(this, EditProfile.class);
                i.putExtra("username", username);
                startActivity(i);
                break;
            case R.id.btn_back:
                Intent j = new Intent(this, Menu.class);//On ne devrait pas revenir au menu
                j.putExtra("username", username);
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
        DatabaseHandler db = new DatabaseHandler(this);
        User current = db.getUser(username);
        User corr = db.getUser(correspondant);
        msgList = (ListView) findViewById(R.id.msgview);
        ArrayList<String> list = current.getConvWith(db, corr.getId());
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
