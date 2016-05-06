package ucloveproject.uclove.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ucloveproject.uclove.DB.DatabaseHandler;
import ucloveproject.uclove.DB.Photo;
import ucloveproject.uclove.DB.User;
import ucloveproject.uclove.R;


/**
 * Classe User, cette activité permet de montrer le profil d'un utilisateur
 * Created by Donatien on 25/04/2016.
 */
public class UserProfil extends MyActivity implements View.OnClickListener {

    private Button edit;
    private ImageButton back;
    private String username;
    private ImageView imageView;
    private Button changePic;


    //YOU CAN EDIT THIS TO WHATEVER YOU WANT
    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            username = extras.getString("username");
        showProfil();
        this.addListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                Intent i = new Intent(this, EditProfile.class);
                i.putExtra("username", username);
                startActivity(i);
                break;
            case R.id.btn_back:
                Intent j = new Intent(this, Menu.class);
                j.putExtra("username", username);
                startActivity(j);
                break;
            case R.id.btn_upload:

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);

        }
    }

    //UPDATED
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                try {
                    imageView.setImageBitmap(getBitmapFromUri(selectedImageUri));
                    DatabaseHandler db = new DatabaseHandler(this);
                    User current = db.getUser(username);
                    Photo picture = new Photo(0, current.getId(), getBitmapFromUri(selectedImageUri));
                    db.ajouterPhoto(picture);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    /**
     * Ajoute aux boutons du profil leur fonction
     */
    public void addListener() {
        edit = (Button) findViewById(R.id.btn_edit);
        edit.setOnClickListener(this);

        back = (ImageButton) findViewById(R.id.btn_back);
        back.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.img_profile);
        imageView.setOnClickListener(this);

        changePic = (Button) findViewById(R.id.btn_upload);
        changePic.setOnClickListener(this);
    }


    /**
     * Affiche le profil en utilisant le layout activity_profil
     */
    public void showProfil() {
        DatabaseHandler db = new DatabaseHandler(this);
        User current = db.getUser(username);
        if (current != null) {
            TextView usernam = (TextView) findViewById(R.id.username);
            usernam.setText(username);
            TextView nom = (TextView) findViewById(R.id.nom);
            nom.setText(current.getNom());
            TextView genre = (TextView) findViewById(R.id.gender);
            genre.setText(current.getGenre());
            TextView age = (TextView) findViewById(R.id.age);
            age.setText(String.valueOf(current.getAge()));
            TextView cheveux = (TextView) findViewById(R.id.hair);
            cheveux.setText(current.getCheveux());
            TextView yeux = (TextView) findViewById(R.id.eyes);
            yeux.setText(current.getYeux());
            TextView ville = (TextView) findViewById(R.id.location);
            ville.setText(current.getVille());
            TextView orientation = (TextView) findViewById(R.id.inclination);
            orientation.setText(current.getOrientation());
            ImageView pic = (ImageView) findViewById(R.id.img_profile);
            ArrayList<Photo> pics = db.getPhotoByUserId(current.getId());
            if (pics.size()!=0) {
                pic.setImageBitmap(db.getPhotoByUserId(current.getId()).get(0).getImage());
            }
        }
    }
}