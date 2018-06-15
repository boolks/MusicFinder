package musicfinder.com.musicfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by sky64 on 2016-12-04.
 */

public class webView extends AppCompatActivity{
    WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://211.219.34.162/list.php");
        WebSettings websettings = webView.getSettings();
        websettings.setUseWideViewPort(true);
        websettings.setSupportZoom(true);
        websettings.setBuiltInZoomControls(true);
        websettings.setJavaScriptEnabled(true);
    }
}
