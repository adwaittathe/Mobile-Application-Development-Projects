package example.com.inclassassignment05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public ArrayList<News>arrayList_news;
    public int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtHeadline=(TextView)findViewById(R.id.txtHeadline);
        final TextView publishedAt=(TextView)findViewById(R.id.txtPublishedOn_main);
        final TextView publishedAt_display=(TextView)findViewById(R.id.txtPublishedOn);
        final ImageView img=(ImageView)findViewById(R.id.img_main);
        final TextView pb_text=(TextView)findViewById(R.id.txtPB);
        final ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar);
        final TextView txtDescription=(TextView)findViewById(R.id.txtDesciption_main);
        final TextView txtDescription_display=(TextView)findViewById(R.id.txtDescription);
        final ImageView img_next=(ImageView)findViewById(R.id.img_next);
        final ImageView img_prev=(ImageView)findViewById(R.id.img_prev);
        final Button btnQuit=(Button)findViewById(R.id.btnQuit);

        img.setVisibility(View.INVISIBLE);
        publishedAt_display.setVisibility(View.INVISIBLE);
        txtHeadline.setVisibility(View.INVISIBLE);
        publishedAt.setVisibility(View.INVISIBLE);
        txtDescription.setVisibility(View.INVISIBLE);
        img_next.setVisibility(View.INVISIBLE);
        img_prev.setVisibility(View.INVISIBLE);
        btnQuit.setVisibility(View.INVISIBLE);
        txtDescription_display.setVisibility(View.INVISIBLE);
        publishedAt.setVisibility(View.INVISIBLE);
        if(isConnected()) {


            new GetDataAsync().execute(" https://newsapi.org/v2/top-headlines?sources=buzzfeed&apiKey=8f19938c3bfb43b785fdd8741555427f");
        }
        else
        {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_LONG).show();
        }
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetDataAsync extends AsyncTask<String,Void,ArrayList>
    {

        @Override
        protected void onPostExecute(ArrayList arrayList) {


            final TextView txtHeadline=(TextView)findViewById(R.id.txtHeadline);
            final TextView publishedAt=(TextView)findViewById(R.id.txtPublishedOn_main);
            final TextView publishedAt_display=(TextView)findViewById(R.id.txtPublishedOn);
            final ImageView img=(ImageView)findViewById(R.id.img_main);
            final TextView pb_text=(TextView)findViewById(R.id.txtPB);
            final ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar);
            final TextView txtDescription=(TextView)findViewById(R.id.txtDesciption_main);
            final TextView txtDescription_display=(TextView)findViewById(R.id.txtDescription);

            final ImageView img_next=(ImageView)findViewById(R.id.img_next);
            final ImageView img_prev=(ImageView)findViewById(R.id.img_prev);
            final Button btnQuit=(Button)findViewById(R.id.btnQuit);
            img.setVisibility(View.VISIBLE);
            publishedAt_display.setVisibility(View.VISIBLE);
            txtHeadline.setVisibility(View.VISIBLE);
            publishedAt.setVisibility(View.VISIBLE);
            txtDescription.setVisibility(View.VISIBLE);
            img_next.setVisibility(View.VISIBLE);
            img_prev.setVisibility(View.VISIBLE);
            btnQuit.setVisibility(View.VISIBLE);
            txtDescription_display.setVisibility(View.VISIBLE);
            publishedAt.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
            pb_text.setVisibility(View.INVISIBLE);
            arrayList_news=arrayList;

            Log.d("str",arrayList.toString());
            News news_object=new News();
            index=0;
            news_object= (News) arrayList.get(index);

            img_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnected()) {


                        if (index >= arrayList_news.size() - 1) {
                            Toast.makeText(MainActivity.this, "You are on Last Image", Toast.LENGTH_SHORT).show();
                        } else {
                            index++;
                            int size = arrayList_news.size();

                            News news_object_next = new News();
                            news_object_next = (News) arrayList_news.get(index);
                            Picasso.get().load(news_object_next.urlToImage.trim()).into(img);
                            txtHeadline.setText(news_object_next.news_title.toString().trim());
                            publishedAt.setText(String.valueOf(news_object_next.publishedAt.toString().split("T")[0].trim()));
                            txtDescription.setText(String.valueOf(news_object_next.news_description.toString().trim()));
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            img_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnected()) {


                        if (index ==0) {
                            Toast.makeText(MainActivity.this, "You are on First Image", Toast.LENGTH_SHORT).show();
                        } else {
                            index--;
                            int size = arrayList_news.size();

                            News news_object_next = new News();
                            news_object_next = (News) arrayList_news.get(index);
                            Picasso.get().load(news_object_next.urlToImage.trim()).into(img);
                            txtHeadline.setText(news_object_next.news_title.toString().trim());
                            publishedAt.setText(String.valueOf(news_object_next.publishedAt.toString().split("T")[0].trim()));
                            txtDescription.setText(String.valueOf(news_object_next.news_description.toString().trim()));
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            Picasso.get().load(news_object.urlToImage.trim()).into(img);
            txtHeadline.setText(news_object.news_title.toString().trim());
            publishedAt.setText(String.valueOf(news_object.publishedAt.toString().split("T")[0].trim()));
            txtDescription.setText(String.valueOf(news_object.news_description.toString().trim()));
            //publishedAt.setText(news_object.publishedAt.trim());

        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            HttpURLConnection connection=null;
            String result = null;
            ArrayList<News> newsArrayList=new ArrayList<News>();

            try {
                URL url=new URL(strings[0]);
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = null;
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //result = IOUtils.toString(connection.getInputStream(), "UTF-8");

                    reader =  new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    Log.d("str",stringBuilder.toString());
                    JSONObject root=new JSONObject(stringBuilder.toString());
                    JSONArray per=root.getJSONArray("articles");
                    for(int i=0;i<per.length();i++) {
                        JSONObject personJSON= per.getJSONObject(i);
                        News news_object=new News();
                        news_object.news_title =personJSON.getString("title");
                        news_object.news_description=personJSON.getString("description");
                        news_object.urlToImage=personJSON.getString("urlToImage");
                        news_object.publishedAt=personJSON.getString("publishedAt");
                        newsArrayList.add(news_object);
                    }
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return newsArrayList;


        }
    }


}
