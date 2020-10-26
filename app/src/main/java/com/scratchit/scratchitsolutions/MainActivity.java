package com.scratchit.scratchitsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    //create webview variable
    private WebView myWebView;
    //create progress bar variable
    private ProgressBar myProgressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //call webview by casting
        myWebView = findViewById(R.id.webview);
        //load progress bar
        myProgressBar = findViewById(R.id.progressBar);
        myProgressBar.setMax(100);
        //load website url
        // myWebView.loadUrl("https://scratchitsolutions.wixsite.com/scratchit");
        //get settings and allow app permission to access internet
        WebSettings webSettings = myWebView.getSettings();
        //exception handler in case website has javascript()
        webSettings.setJavaScriptEnabled(true);
        myWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);

        //website to open links in webview client

        //initialize connectivity manager

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //get active network info
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //check network status
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            //when internet is not available

            //initialize dialog
            Dialog dialog = new Dialog(this);
            //set content view
            dialog.setContentView(R.layout.connection_alert);
            //set outside touch
            dialog.setCanceledOnTouchOutside(false);
            //set dialog height and width
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            //initialize button variable

            Button btnTryAgain = dialog.findViewById(R.id.button_try_again);

            //perform onClick Listener
            btnTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //call recreate()
                    //recreate();
                    finish();
                    startActivity(getIntent());
                }
            });
            dialog.show();

        }else {
            myWebView.loadUrl("https://scratchitsolutions.wixsite.com/scratchit");
        }

        //myWebView.setWebViewClient(new WebViewClient());

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                myProgressBar.setVisibility(view.VISIBLE);
                //setTitle("Loading...");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                myProgressBar.setVisibility(view.GONE);
                //setTitle(view.getTitle());
                super.onPageFinished(view, url);
            }
        });

    }
    //predefined function called when press go back button on phone
    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack())
        {
            myWebView.goBack();
        }else
        {
            super.onBackPressed();
        }
    }
}