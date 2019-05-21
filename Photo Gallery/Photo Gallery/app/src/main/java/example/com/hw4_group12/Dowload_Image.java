package example.com.hw4_group12;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;

public class Dowload_Image extends AsyncTask<String, Void, Bitmap> {

    IImage iImage;

    public Dowload_Image(IImage iImage) {
        this.iImage = iImage;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        HttpURLConnection connection = null;
        Bitmap image = null;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                image = BitmapFactory.decodeStream(connection.getInputStream());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();

            }
        }


        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        iImage.setImage(bitmap);
    }


    public static interface IImage {
        public void setImage(Bitmap bitmap);
    }
}
