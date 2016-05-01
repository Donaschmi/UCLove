package ucloveproject.uclove.DB;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Céline on 01-05-16.
 */
public class Photo {
    private int id;
    private int idUser;
    private Bitmap image;

    public Photo(int id, int idUser, byte[] image){
        this.id = id;
        this.idUser = idUser;
        this.image = BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public int getId(){
        return this.id;
    }

    public int getUserId(){
        return this.idUser;
    }

    public Bitmap getImage(){
        return this.image;
    }

    public byte[] getImageBytes(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        this.image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


}
