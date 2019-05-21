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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ProgressBar pb;
    public static TextView txtLoading;
    public final static String NEWS_key="news";
    private String[] SourceName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isConnected()) {
            new GetDataAsync().execute("https://newsapi.org/v2/sources?apiKey=8f19938c3bfb43b785fdd8741555427f");
        }
        else
        {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
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


        @Override
        protected void onPostExecute(final ArrayList arrayList) {

            if(arrayList!=null) {

                txtLoading=(TextView)findViewById(R.id.txt_Loading);
                pb=(ProgressBar)findViewById(R.id.pb);

                ListView listView = (ListView) findViewById(R.id.source_listView);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, SourceName);
                listView.setAdapter(arrayAdapter);
                pb.setVisibility(View.INVISIBLE);
                txtLoading.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Source selected_news = (Source) arrayList.get(position);
                        String news_id = selected_news.id;
                        String news_name = selected_news.name;
                        Intent int_news = new Intent(MainActivity.this, NewsActivity.class);
                        int_news.putExtra(NEWS_key, selected_news);
                        startActivity(int_news);
                    }
                });

            }

        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            HttpURLConnection connection=null;
            String result = null;
            ArrayList<Source> sourceArrayList=new ArrayList<Source>();

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
                    JSONArray per=root.getJSONArray("sources");
                    SourceName=new String[per.length()];
                    for(int i=0;i<per.length();i++) {
                        JSONObject sourceJSON= per.getJSONObject(i);
                        Source source=new Source();
                        source.id=sourceJSON.getString("id");
                        source.name=sourceJSON.getString("name");

                       SourceName[i]=sourceJSON.getString("name");
                       sourceArrayList.add(source);

                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return sourceArrayList;
        }
    }
}
