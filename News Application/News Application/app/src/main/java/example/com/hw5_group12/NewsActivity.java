package example.com.hw5_group12;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class NewsActivity extends AppCompatActivity {

    public static final String URL_KEY="url";
    public static final String Title_key="title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            Source news_obj=(Source)getIntent().getExtras().getSerializable(MainActivity.NEWS_key);
            NewsActivity.this.setTitle(news_obj.name.toString().trim());
            if(isConnected()) {
                new GetDataAsync().execute("https://newsapi.org/v2/top-headlines?sources=" + news_obj.id.toString().trim() + "&apiKey=8f19938c3bfb43b785fdd8741555427f");
            }
            else
            {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }

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

        public final static String NEWS_key="news";
        @Override
        protected void onPostExecute(final ArrayList arrayList) {

            //Log.d("str",arrayList.toString());
            ListView listView=(ListView)findViewById(R.id.news_listView);
            NewsAdapter adapter=new NewsAdapter(NewsActivity.this,R.layout.news_layout,arrayList);
            listView.setAdapter(adapter);
            final TextView txtLoading=(TextView)findViewById(R.id.txtLoading_pb);
            final ProgressBar progressBar=(ProgressBar)findViewById(R.id.pb_loading);
            txtLoading.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // Log.d("demo"," clicked news "+position);
                    News news_obj=(News) arrayList.get(position);
                    Intent int_url=new Intent(NewsActivity.this,WebViewActivity.class);
                    int_url.putExtra(URL_KEY,news_obj.url.toString().trim());
                    int_url.putExtra(Title_key,NewsActivity.this.getTitle().toString().trim());
                    startActivity(int_url);

                }
            });

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

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    reader =  new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine())!=null)
                    {
                        stringBuilder.append(line);
                    }
                    Log.d("str",stringBuilder.toString());
                    JSONObject root=new JSONObject(stringBuilder.toString());
                    JSONArray per=root.getJSONArray("articles");
                    //SourceName=new String[per.length()];
                    for(int i=0;i<per.length();i++) {
                        JSONObject newsJSON= per.getJSONObject(i);
                        News news=new News();
                        news.author=newsJSON.getString("author");
                        news.title=newsJSON.getString("title");
                        news.url=newsJSON.getString("url");
                        news.urlToImage=newsJSON.getString("urlToImage");
                        news.publishedAt=newsJSON.getString("publishedAt");

                        newsArrayList.add(news);

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
