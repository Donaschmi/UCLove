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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;

/**
 * Created by Robin on 05/03/2016.
 */

public class Disponibilities extends MyActivity implements View.OnClickListener {

    private CheckBox btnFav = null;
    private String username = null;
    private ImageButton back;
    private Button add_dispo = null;
    private ListView listview = null;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilite);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            username = extras.getString("username");

        btnFav = (CheckBox) findViewById(R.id.btn_unmatched);

        listview = (ListView) findViewById(R.id.list_dispo);
        DatabaseHandler db = new DatabaseHandler(this);
        User current = db.getUser(username);
        list = current.getFriendNames(db);
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Intent k = new Intent(Disponibilities.this, FriendMenu.class);
                k.putExtra("username", username);
                k.putExtra("friend", item);
                startActivity(k);
            }

        });
        this.addListener();
    }
    private void addListener() {
        btnFav = (CheckBox) findViewById(R.id.btn_unmatched);
        btnFav.setOnClickListener(this);

        add_dispo = (Button) findViewById(R.id.add_dispo);
        add_dispo.setOnClickListener(this);

        back = (ImageButton) findViewById(R.id.btn_dispo_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Is the view now checked?

        // Check which checkbox was clicked
        switch (v.getId()) {
            case R.id.btn_unmatched:
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    DatabaseHandler db = new DatabaseHandler(this);
                    User current = db.getUser(username);

                    final StableArrayAdapter adapter = new StableArrayAdapter(this,
                            android.R.layout.simple_list_item_1, current.getFriendNames(db));
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            final String item = (String) parent.getItemAtPosition(position);
                            Intent k = new Intent(Disponibilities.this, FriendMenu.class);
                            k.putExtra("username", username);
                            k.putExtra("friend", item);
                            startActivity(k);
                        }

                    });

                    String aboutUs="Favoris";
                    super.printToast(aboutUs);
                    break;

                } else {
                    DatabaseHandler db = new DatabaseHandler(this);
                    User current = db.getUser(username);

                    final StableArrayAdapter adapter = new StableArrayAdapter(this,
                            android.R.layout.simple_list_item_1, current.getFavNames(db));
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            final String item = (String) parent.getItemAtPosition(position);
                            Intent k = new Intent(Disponibilities.this, FriendMenu.class);
                            k.putExtra("username", username);
                            k.putExtra("friend", item);
                            startActivity(k);
                        }

                    });

                    String aboutUs="All Friends";
                    super.printToast(aboutUs);
                    break;

                }
            case R.id.add_dispo:
                Intent i = new Intent(this, Menu.class);
                i.putExtra("username", username);
                startActivity(i);

            case R.id.btn_dispo_back:
                Intent j = new Intent(this, Menu.class);
                j.putExtra("username", username);
                startActivity(j);
                break;
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
