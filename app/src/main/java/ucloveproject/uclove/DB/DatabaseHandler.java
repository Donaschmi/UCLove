package ucloveproject.uclove.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Céline on 28-04-16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //Version de la base de données et son npm
    protected final static int VERSION = 1;//Changer si on opère un gros changement dans la db
    protected final static String DB_NOM = "uclove.sqlite";

    //Tables
    private static final String TABLE_USERS = "UTILISATEUR";
    private static final String TABLE_MESSAGES = "MESSAGES";
    private static final String TABLE_REQUETES = "REQUETES";
    private static final String TABLE_DISPOS = "DISPONIBILITES";
    private static final String TABLE_PHOTOS = "PHOTOS";
    //Champs de la table users
    public static final String U_KEY = "_id";
    public static final String LOGIN = "Login";
    public static final String MDP = "Mdp";
    public static final String NOM = "Nom";
    public static final String AGE = "Age";
    public static final String GENRE = "Genre";
    public static final String ORIENTATION = "Orientation sexuelle";
    public static final String STYLE = "Style capillaire";
    public static final String YEUX = "Couleur des yeux";
    public static final String VILLE = "Ville de residence";
    //Champs de la table messages
    public static final String M_KEY = "_id";
    public static final String M_EXP = "Expediteur";
    public static final String M_DEST = "Destinataire";
    public static final String CONTENU = "Contenu";
    public static final String M_DATE = "Date";
    //Champs de la table requetes
    public static final String R_KEY = "_id";
    public static final String R_EXP = "Expediteur";
    public static final String R_DEST = "Destinataire";
    public static final String STATUT = "Statut";
    public static final String R_DATE = "Date";
    //Champs de la table disponibilites
    public static final String D_KEY = "_id";
    public static final String PROP = "Proprietaire";
    public static final String D_DATE = "Date";
    //Champs de la table photos
    public static final String P_KEY = "_id";
    public static final String IDUSER = "Proprietaire";
    public static final String IMAGE = "Image";

    public DatabaseHandler(Context context) {
        super(context, DB_NOM, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Contruction de la table users
        String USERS_CREATE =
                "CREATE TABLE " + TABLE_USERS + " ("
                        + U_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + LOGIN + " TEXT, "
                        + MDP + "TEXT, "
                        + NOM + "TEXT, "
                        + AGE + "INTEGER, "
                        + GENRE + "TEXT, "
                        + ORIENTATION + "TEXT, "
                        + STYLE + "TEXT, "
                        + YEUX + "TEXT, "
                        + VILLE + "TEXT, "
                        + IMAGE + "BLOB);";
        db.execSQL(USERS_CREATE);

        //Contruction de la table messages
        String MESSAGES_CREATE =
                "CREATE TABLE " + TABLE_MESSAGES + " ("
                        + M_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + M_EXP + " TEXT, "
                        + M_DEST + "TEXT, "
                        + CONTENU + "TEXT, "
                        + M_DATE + "TEXT);";
        db.execSQL(MESSAGES_CREATE);

        //Contruction de la table requêtes
        String REQUETES_CREATE =
                "CREATE TABLE " + TABLE_REQUETES + " ("
                        + R_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + R_EXP + " TEXT, "
                        + R_DEST + "TEXT, "
                        + STATUT + "TEXT, "
                        + R_DATE + "TEXT);";
        db.execSQL(REQUETES_CREATE);

        //Contruction de la table disponibilites
        String DISPOS_CREATE =
                "CREATE TABLE " + TABLE_DISPOS + " ("
                        + D_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + PROP + " TEXT, "
                        + R_DATE + "TEXT);";
        db.execSQL(DISPOS_CREATE);

        //Contruction de la table photos
        String PHOTOS_CREATE =
                "CREATE TABLE " + TABLE_PHOTOS + " ("
                        + P_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + IDUSER + " TEXT, "
                        + IMAGE + "BLOB);";
        db.execSQL(PHOTOS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String USERS_DROP =  "DROP TABLE IF EXISTS " + TABLE_USERS + ";";
        db.execSQL(USERS_DROP);
        String MESSAGES_DROP =  "DROP TABLE IF EXISTS " + TABLE_MESSAGES + ";";
        db.execSQL(MESSAGES_DROP);
        String REQUETES_DROP =  "DROP TABLE IF EXISTS " + TABLE_REQUETES + ";";
        db.execSQL(REQUETES_DROP);
        String DISPOS_DROP =  "DROP TABLE IF EXISTS " + TABLE_DISPOS + ";";
        db.execSQL(DISPOS_DROP);
        String PHOTOS_DROP =  "DROP TABLE IF EXISTS " + TABLE_PHOTOS + ";";
        db.execSQL(PHOTOS_DROP);
        onCreate(db);
    }

    //CRUD table USERS
    public void ajouterUser(User n){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(LOGIN, n.getLogin());
        value.put(MDP, n.getMdp());
        value.put(NOM, n.getNom());
        value.put(AGE, n.getAge());
        value.put(GENRE, n.getGenre());
        value.put(ORIENTATION, n.getOrientation());
        value.put(STYLE, n.getCheveux());
        value.put(VILLE, n.getVille());
        db.insert(TABLE_USERS, null, value);//Insérer la nouvelle ligne
        db.close();//Fermer le flux
    }

    public User getUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {U_KEY, LOGIN,
                        MDP, NOM, AGE, GENRE, ORIENTATION, STYLE,
                        YEUX, VILLE }, LOGIN + "=?",
                new String[] { username }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            User found = new User(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getString(9));
            cursor.close();
            return found;
        }
        else
            return null;//On a pas trouvé d'user avec ce nom là
    }

    public User getUserById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {U_KEY, LOGIN,
                        MDP, NOM, AGE, GENRE, ORIENTATION, STYLE,
                        YEUX, VILLE }, U_KEY + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            User found = new User(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getString(9));
            cursor.close();
            return found;
        }
        else
            return null;//On a pas trouvé d'user avec ce nom là
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<User>();
        String selectAll = "SELECT * FROM" + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAll, null);
        if (cursor.moveToFirst()) {
            do {
                User toAdd = new User(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)),cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getString(9));
                result.add(toAdd);//Ajouter à l'arraylist
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * @param idToDelete, l'id de l'utilisateur qu'on veut supprimer (peut se faire via l'objet également, à voir)
     */
    public void supprimerUser(int idToDelete){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, U_KEY + " = ?", new String[]{String.valueOf(idToDelete)});
        db.close();
    }

    /**
     * @param toAlter, utilisateur dont on veut modifier les champs
     * @return Le nombre de colonnes modifiées sous forme de int
     */
    public int modifierUser(User toAlter){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOGIN, toAlter.getLogin());
        values.put(MDP, toAlter.getMdp());
        values.put(NOM, toAlter.getNom());
        values.put(AGE, toAlter.getAge());
        values.put(GENRE, toAlter.getGenre());
        values.put(ORIENTATION, toAlter.getOrientation());
        values.put(STYLE, toAlter.getCheveux());
        values.put(VILLE, toAlter.getVille());

        // Mettre à jour
        return db.update(TABLE_USERS, values, U_KEY + " = ?",
                new String[] { String.valueOf(toAlter.getId()) });
    }

    //CRUD table MESSAGES
    public void ajouterMessage(Message m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(M_EXP, m.getExpediteur());
        value.put(M_DEST, m.getDestinataire());
        value.put(CONTENU, m.getContenu());
        value.put(M_DATE, String.valueOf(m.getDate()));
        db.insert(TABLE_MESSAGES, null, value);//Insérer la nouvelle ligne
        db.close();//Fermer le flux
    }

    public Message getMessage(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Cursor cursor = db.query(TABLE_MESSAGES, new String[] {M_KEY, CONTENU, M_EXP,
                        M_DEST, M_DATE}, M_KEY + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            try {
                Message found = new Message(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)),
                        dateFormat.parse(cursor.getString(4)));
                cursor.close();
                return found;
            }
            catch (java.text.ParseException e){
                return null;
            }
        }
        return null;
    }

    /**
     * @param idToDelete, l'id du message qu'on veut supprimer
     */
    public void supprimerMessage(int idToDelete){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGES, M_KEY + " = ?", new String[]{String.valueOf(idToDelete)});
        db.close();
    }

    //Un update de message ne me semble pas nécessaire

    //CRUD table REQUETES
    public void ajouterRequete(Requete r){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(R_EXP, r.getExpediteur());
        value.put(R_DEST, r.getDestinataire());
        value.put(STATUT, r.getStatut());
        value.put(R_DATE, String.valueOf(r.getDate()));
        db.insert(TABLE_REQUETES, null, value);//Insérer la nouvelle ligne
        db.close();//Fermer le flux
    }

    public Requete getRequete(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Cursor cursor = db.query(TABLE_REQUETES, new String[] {R_KEY, R_EXP,
                        R_DEST, STATUT, R_DATE}, R_KEY + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            try {
                Requete found = new Requete(Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)),
                        dateFormat.parse(cursor.getString(4)));
                found.setStatut(Boolean.parseBoolean(cursor.getString(3)));
                cursor.close();
                return found;
            }
            catch (java.text.ParseException e){
                return null;
            }
        }
        return null;
    }

    public ArrayList<Requete> getRequeteByExp(int idExp){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Requete> result = new ArrayList<Requete>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Cursor cursor = db.query(TABLE_REQUETES, new String[] {R_KEY, R_EXP,
                        R_DEST, STATUT, R_DATE}, R_EXP + "=?",
                new String[] { String.valueOf(idExp) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    Requete found = new Requete(Integer.parseInt(cursor.getString(0)),
                            Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)),
                            dateFormat.parse(cursor.getString(4)));
                    found.setStatut(Boolean.parseBoolean(cursor.getString(3)));
                    result.add(found);
                }
                catch (java.text.ParseException e){
                    //Just ignore
                }
            } while (cursor.moveToNext());
            cursor.close();
            return result;
        }
        return null;
    }

    public ArrayList<Requete> getRequeteByDest(int idDest){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Requete> result = new ArrayList<Requete>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Cursor cursor = db.query(TABLE_REQUETES, new String[] {R_EXP,
                        R_DEST, STATUT, R_DATE}, R_EXP + "=?",
                new String[] { String.valueOf(idDest) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    Requete found = new Requete(Integer.parseInt(cursor.getString(0)),
                            Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)),
                            dateFormat.parse(cursor.getString(3)));
                    found.setStatut(Boolean.parseBoolean(cursor.getString(3)));
                    result.add(found);
                }
                catch (java.text.ParseException e){
                    //Juste ignore
                }
            } while (cursor.moveToNext());
            cursor.close();
            return result;
        }
        return null;
    }

    /**
     * @param idToDelete, l'id du message qu'on veut supprimer
     */
    public void supprimerRequete(int idToDelete){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REQUETES, R_KEY + " = ?", new String[]{String.valueOf(idToDelete)});
        db.close();
    }

    public int modifierRequete(Requete toAlter){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(R_EXP, toAlter.getExpediteur());
        value.put(R_DEST, toAlter.getDestinataire());
        value.put(STATUT, toAlter.getStatut());
        value.put(R_DATE, String.valueOf(toAlter.getDate()));

        // Mettre à jour
        return db.update(TABLE_REQUETES, value, R_KEY + " = ?",
                new String[] { String.valueOf(toAlter.getId()) });
    }

    //CRUD table DISPOS
    public void ajouterDispo(Disponibilite d){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(PROP, d.getProprietaire());
        value.put(STATUT, d.getStatut());
        value.put(D_DATE, String.valueOf(d.getDate()));
        db.insert(TABLE_DISPOS, null, value);//Insérer la nouvelle ligne
        db.close();//Fermer le flux
    }

    public Disponibilite getDispo(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Cursor cursor = db.query(TABLE_DISPOS, new String[] {D_KEY, PROP,
                        STATUT, D_DATE}, D_KEY + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            try{
                Disponibilite found = new Disponibilite(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)), dateFormat.parse(cursor.getString(3)));
                found.setStatut(Boolean.parseBoolean(cursor.getString(2)));
                cursor.close();
                return found;
            }
            catch (java.text.ParseException e){
                return null;
            }
        }
        return null;
    }

    /**
     * @param idToDelete, l'id du message qu'on veut supprimer
     */
    public void supprimerDispo(int idToDelete){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISPOS, D_KEY + " = ?", new String[]{String.valueOf(idToDelete)});
        db.close();
    }

    public int modifierDispo(Disponibilite toAlter){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(PROP, toAlter.getProprietaire());
        value.put(STATUT, toAlter.getStatut());
        value.put(D_DATE, String.valueOf(toAlter.getDate()));

        // Mettre à jour
        return db.update(TABLE_DISPOS, value, D_KEY + " = ?",
                new String[] { String.valueOf(toAlter.getId()) });
    }

    //CRUD table PHOTOS
    public void ajouterPhoto(Photo p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(IDUSER, p.getUserId());
        value.put(IMAGE, p.getImageBytes());
        db.insert(TABLE_PHOTOS, null, value);//Insérer la nouvelle ligne
        db.close();//Fermer le flux
    }

    public ArrayList<Photo> getPhotoByUserId(int idUser){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Photo> result = new ArrayList<Photo>();
        Cursor cursor = db.query(TABLE_PHOTOS, new String[] {P_KEY, IDUSER,
                        IMAGE}, IDUSER + "=?",
                new String[] { String.valueOf(idUser) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Photo found = new Photo(Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)), cursor.getBlob(2));
                result.add(found);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * @param idToDelete, l'id du message qu'on veut supprimer
     */
    public void supprimerPhoto(int idToDelete){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PHOTOS, P_KEY + " = ?", new String[]{String.valueOf(idToDelete)});
        db.close();
    }
    //Pas besoin de modifier les photos
}
