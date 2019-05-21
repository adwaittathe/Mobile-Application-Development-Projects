package example.com.inclass04_group12;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.net.wifi.WifiConfiguration.Status.strings;

public class MainActivity extends AppCompatActivity {

    Handler hand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnThread=(Button)findViewById(R.id.btnThread);
        final Button btnAsync=(Button)findViewById(R.id.btnAsync);
        final ImageView image=(ImageView)findViewById(R.id.imageView);
        final ExecutorService es= Executors.newFixedThreadPool(5);
        final ProgressBar pb_circle=(ProgressBar)findViewById(R.id.progressBar2);
        pb_circle.setVisibility(View.INVISIBLE);

        hand=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                Bitmap myBitMap=(Bitmap) msg.getData().getParcelable(getImage_Thread.img_thread);
                image.setImageBitmap(myBitMap);
                pb_circle.setVisibility(View.INVISIBLE);
                return false;
            }
        });



        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_circle.setVisibility(View.VISIBLE);
                es.execute(new getImage_Thread());
            }
        });

        btnAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_circle.setVisibility(View.VISIBLE);
                new GetImageAsync().execute("https://cdn.pixabay.com/photo/2014/12/16/22/25/youth-570881_960_720.jpg");
            }
        });
    }


    public class GetImageAsync extends AsyncTask<String,Integer,Bitmap>
    {

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            final ProgressBar pb_circle=(ProgressBar)findViewById(R.id.progressBar2);
            if(bitmap != null){
                final ImageView image=(ImageView)findViewById(R.id.imageView);
                image.setImageBitmap(bitmap);
                pb_circle.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection connection = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //Handle the exceptions
            } catch (IOException e){
                e.printStackTrace();;
                //Close open connection
            }
            return bitmap;
        }
    }
    public class getImage_Thread implements Runnable
    {
        final static String img_thread="IMGTHREAD";

        static  final String Progress_key="progress";


        Bitmap getImageBitmap(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void run() {




            String imageURL = null;
            imageURL = "https://cdn.pixabay.com/photo/2017/12/31/06/16/boats-3051610_960_720.jpg";
            Bitmap myBitmap = getImageBitmap(imageURL);
            Message msg=new Message();
            Bundle bd= new Bundle();
            bd.putParcelable(img_thread,myBitmap);
            msg.setData(bd);
            hand.sendMessage(msg);




        }
    }
}
