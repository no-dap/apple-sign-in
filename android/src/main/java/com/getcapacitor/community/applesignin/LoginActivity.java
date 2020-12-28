package com.getcapacitor.community.applesignin;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.community.applesignin.applesignin.R;

public class LoginActivity extends AppCompatActivity {
  Dialog appleDialog;
  String authUrl;
  String redirectURI;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    authUrl = getIntent().getStringExtra("authUrl");
    redirectURI = getIntent().getStringExtra("redirectURI");

    Log.i("url", authUrl);
    setupAppleWebViewDialog();
  }

  private void setupAppleWebViewDialog() {
    appleDialog = new Dialog(this);
    WebView webView = new WebView(this);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    webView.setVerticalScrollBarEnabled(false);
    webView.setHorizontalScrollBarEnabled(false);
    webView.setWebViewClient(new AppleWebViewClient());
    webView.loadUrl(authUrl);
    appleDialog.setContentView(webView);
    appleDialog.show();
  }

  private class AppleWebViewClient extends WebViewClient {

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);

      // retrieve display dimensions
      final Rect displayRectangle = new Rect();
      final Window window = LoginActivity.this.getWindow();
      window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

      // Set height of the Dialog to 90% of the screen
      final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
      layoutParams.width = Math.round((displayRectangle.width() * 0.9f));
      layoutParams.height = Math.round((displayRectangle.height() * 0.9f));
      view.setLayoutParams(layoutParams);
    }
  }
}
