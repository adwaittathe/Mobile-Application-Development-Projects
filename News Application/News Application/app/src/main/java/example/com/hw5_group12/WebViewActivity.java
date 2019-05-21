package example.com.hw5_group12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView=(WebView)findViewById(R.id.webview_news);
        if(getIntent().getExtras()!=null && getIntent()!=null)
        {
            String title=(String) getIntent().getExtras().getString(NewsActivity.Title_key);
            WebViewActivity.this.setTitle(title.toString().trim());
            String url=(String) getIntent().getExtras().getString(NewsActivity.URL_KEY);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }
    }
}
