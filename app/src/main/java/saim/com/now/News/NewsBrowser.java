package saim.com.now.News;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import saim.com.now.R;

public class NewsBrowser extends AppCompatActivity {

    WebView mWebView;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imgExit;
    TextView txtNewstitle;
    ProgressDialog progressDialog;

    public String mainUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_browser);

        mainUrl = getIntent().getExtras().getString("MAIN_URL");

        init();
    }

    private void init() {

        imgExit = (ImageView) findViewById(R.id.imgExit);
        mWebView = (WebView) findViewById(R.id.webView);
        txtNewstitle = (TextView) findViewById(R.id.txtNewstitle);
        txtNewstitle.setText(getIntent().getExtras().getString("TITLE").trim());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wiat...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        renderWebPage(mainUrl);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                renderWebPage(mainUrl);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ExitDialog();
                finish();
            }
        });
    }


    protected void renderWebPage(String urlToRender){
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebViewClient(new MyBrowser());
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                mWebView.setEnabled(false);
                if (newProgress == 100) {
                    mWebView.setEnabled(true);
                    progressDialog.dismiss();
                }
            }
        });

        mWebView.loadUrl(urlToRender);
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("SAIM ANDROID M", url);
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.d("SAIM ANDROID N", request.getUrl().toString());
            view.loadUrl(String.valueOf(request.getUrl()));
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressDialog.show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                        //ExitDialog();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    public void ExitDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewsBrowser.this);
        alertDialogBuilder.setTitle("Exit kooxda!");

        alertDialogBuilder.setMessage("Are you sure want to close kooxda?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
