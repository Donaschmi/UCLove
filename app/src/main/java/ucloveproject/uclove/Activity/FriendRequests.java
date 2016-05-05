package ucloveproject.uclove.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.Requete;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Created by Robin on 05/03/2016.
 */

public class FriendRequests extends MyActivity implements View.OnClickListener {

    private CheckBox btnRequests = null;
    private String username = null;
    private ListView listview = null;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            username = extras.getString("username");

        btnRequests = (CheckBox) findViewById(R.id.show_in_out);
        listview = (ListView) findViewById(R.id.list_requests);
        DatabaseHandler db = new DatabaseHandler(this);
        User current = db.getUser(username);
        ArrayList<Requete> listRequete = db.getRequetesIn(current.getId());
        list = new ArrayList<String>();
        Iterator iter = listRequete.iterator();
        while (iter.hasNext()){
            Requete temp = (Requete) iter.next();
            User requested = db.getUserById(temp.getExpediteur());
            list.add(requested.getLogin());
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Intent i = new Intent(FriendRequests.this, DetailRequete.class);
                i.putExtra("username", username);
                i.putExtra("request", item);//Envoyer l'username de la request
                startActivity(i);

            }

        });
        this.addListener();
    }
    private void addListener() {
        btnRequests = (CheckBox) findViewById(R.id.show_in_out);
        btnRequests.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Is the view now checked?
        boolean checked = ((CheckBox) v).isChecked();

        // Check which checkbox was clicked
        switch (v.getId()) {
            case R.id.show_in_out:
                if (checked) {
                    DatabaseHandler db = new DatabaseHandler(this);
                    User current = db.getUser(username);
                    ArrayList<Requete> listRequete = db.getRequetesOut(current.getId());
                    list = new ArrayList<String>();
                    Iterator iter = listRequete.iterator();
                    while (iter.hasNext()){
                        Requete temp = (Requete) iter.next();
                        User requested = db.getUserById(temp.getExpediteur());
                        list.add(requested.getLogin());
                    }

                    final StableArrayAdapter adapter = new StableArrayAdapter(this,
                            android.R.layout.simple_list_item_1, list);
                    listview.setAdapter(adapter);


                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            final String item = (String) parent.getItemAtPosition(position);
                            view.animate().setDuration(2000).alpha(0)
                                    .withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            //list.remove(item);
                                            adapter.notifyDataSetChanged();
                                            view.setAlpha(1);
                                        }
                                    });
                        }

                    });

                    String aboutUs="Envoyées";
                    super.printToast(aboutUs);
                    break;

                } else {
                    DatabaseHandler db = new DatabaseHandler(this);
                    User current = db.getUser(username);

                    ArrayList<Requete> listRequete = db.getRequetesIn(current.getId());
                    list = new ArrayList<String>();
                    Iterator iter = listRequete.iterator();
                    while (iter.hasNext()){
                        Requete temp = (Requete) iter.next();
                        User requested = db.getUserById(temp.getExpediteur());
                        list.add(requested.getLogin());
                    }

                    final StableArrayAdapter adapter = new StableArrayAdapter(this,
                            android.R.layout.simple_list_item_1, list);
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            final String item = (String) parent.getItemAtPosition(position);
                            view.animate().setDuration(2000).alpha(0)
                                    .withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            //list.remove(item);
                                            adapter.notifyDataSetChanged();
                                            view.setAlpha(1);
                                        }
                                    });
                        }

                    });

                    String aboutUs="Reçues";
                    super.printToast(aboutUs);
                    break;

                }
        }
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
