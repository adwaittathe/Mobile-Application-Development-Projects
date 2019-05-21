package example.com.hw4_group12;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements Dowload_Image.IImage {

    private String keyword = "";
    LinkedList<String> movieURL;
    int index;
    ProgressBar pb;
    private int size=0;
    TextView txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btnGo = (Button) findViewById(R.id.btnGo);

        txtLoading = (TextView) findViewById(R.id.txtLoading);
        txtLoading.setText("Loading Dictionary....");
        txtLoading.setBackgroundColor(Color.WHITE);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setBackgroundColor(Color.WHITE);
        pb.setVisibility(View.INVISIBLE);
        txtLoading.setVisibility(View.INVISIBLE);
        movieURL = new LinkedList<String>();
        final ImageView img_next = (ImageView) findViewById(R.id.img_next);
        final ImageView img_prev = (ImageView) findViewById(R.id.img_previous);
        img_next.setVisibility(View.INVISIBLE);
        img_prev.setVisibility(View.INVISIBLE);
        img_next.setEnabled(false);
        img_prev.setEnabled(false);


        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {
                    if (index == movieURL.size() - 1) {
                        index = 0;
                    } else {
                        index++;
                    }
                    String str = (String) movieURL.get(index);
                    final ImageView img_next = (ImageView) findViewById(R.id.img_next);
                    final ImageView img_prev = (ImageView) findViewById(R.id.img_previous);
                    pb.setVisibility(View.VISIBLE);
                    txtLoading.setVisibility(View.VISIBLE);
                    txtLoading.setText("Loading Photo....");
                    new Dowload_Image(MainActivity.this).execute(movieURL.get(index));
                } else {
                    Toast.makeText(MainActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        img_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {
                    if (movieURL.size() != 0) {
                        if (index == 0) {
                            index = movieURL.size() - 1;
                        } else {
                            index--;
                        }
                        String str = (String) movieURL.get(index);
                        pb.setVisibility(View.VISIBLE);
                        txtLoading.setVisibility(View.VISIBLE);
                        txtLoading.setText("Loading Photo....");
                        new Dowload_Image(MainActivity.this).execute(movieURL.get(index));
                    }

                } else {
                    Toast.makeText(MainActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {
                    new GetAsyncText().execute("http://dev.theappsdr.com/apis/photos/keywords.php");
                } else {
                    Toast.makeText(MainActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private Boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;

        }
        return true;
    }

    @Override
    public void setImage(Bitmap bitmap) {

        ImageView image = (ImageView) findViewById(R.id.image_main);
        if (bitmap != null) {
            image.setImageBitmap(bitmap);
            final ImageView img_next = (ImageView) findViewById(R.id.img_next);
            final ImageView img_prev = (ImageView) findViewById(R.id.img_previous);
            pb = (ProgressBar) findViewById(R.id.progressBar);
            pb.setVisibility(View.INVISIBLE);
            txtLoading.setVisibility(View.INVISIBLE);
            if(size>1) {
                img_next.setEnabled(true);
                img_prev.setEnabled(true);
                img_next.setVisibility(View.VISIBLE);
                img_prev.setVisibility(View.VISIBLE);

            }

        }
    }

    public class GetAsyncText extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            if (s != null) {

                final String[] keywords = s.split(";");
                AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
                build.setTitle("Choose a keyword")
                        .setItems(keywords, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                keyword = keywords[which];
                                TextView txtImage = (TextView) findViewById(R.id.txtImageName);
                                txtImage.setText(keyword);
                                if (!keyword.equals("")) {
                                    String URL = "http://dev.theappsdr.com/apis/photos/index.php?keyword=" + keyword;
                                    pb = (ProgressBar) findViewById(R.id.progressBar);
                                    movieURL.clear();
                                    txtLoading.setText("Loading Dictionary....");
                                    new GetImageAsync().execute(URL);
                                }
                            }
                        });


                AlertDialog alertDialog = build.create();
                alertDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String result = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (isConnected()) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                }
                result = stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public class GetImageAsync extends AsyncTask<String, Void, LinkedList<String>> {

        @Override
        protected void onPostExecute(LinkedList<String> list) {

            size=list.size();

            if (list.size() != 0) {
                movieURL = list;
                index = 0;
                pb.setVisibility(View.VISIBLE);
                txtLoading.setVisibility(View.VISIBLE);

                String image_url = list.get(0);
                new Dowload_Image(MainActivity.this).execute(list.get(0));
            } else {
                ImageView image = (ImageView) findViewById(R.id.image_main);
                image.setImageResource(0);
                Toast.makeText(MainActivity.this, "No Image found", Toast.LENGTH_SHORT).show();

            }
            if(list.size()<=1)
            {
                final ImageView img_next = (ImageView) findViewById(R.id.img_next);
                final ImageView img_prev = (ImageView) findViewById(R.id.img_previous);
                img_next.setEnabled(false);
                img_prev.setEnabled(false);
                img_next.setVisibility(View.INVISIBLE);
                img_prev.setVisibility(View.INVISIBLE);

            }

        }

        @Override
        protected LinkedList<String> doInBackground(String... strings) {

            StringBuilder stringBuilder = new StringBuilder();
            LinkedList<String> list= new LinkedList<>();
            HttpURLConnection connection=null;
            BufferedReader reader=null;
            String result=null;
            try {
                String str=strings[0];
                URL url=new URL(strings[0]);
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                if(isConnected())
                {
                    reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line="";

                    while ((line=reader.readLine())!=null)
                    {
                        list.add(line);

                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
}




