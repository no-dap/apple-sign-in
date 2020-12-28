package com.getcapacitor.community.applesignin;

import android.content.Intent;
import android.content.pm.SigningInfo;
import android.net.Uri;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@NativePlugin(requestCodes = { SignInWithApple.AUTH_CODE })
public class SignInWithApple extends Plugin {
  protected static final int AUTH_CODE = 0103;

  String authUrl = "https://appleid.apple.com/auth/authorize";

  @PluginMethod
  public void authorize(PluginCall call) {
    String clientId = call.getString("clientId", null);
    String redirectURI = call.getString("redirectURI", null);

    if (clientId == null || redirectURI == null) {
      call.reject("No options were provided.");
      return;
    }

    Uri authenticationUri = Uri
      .parse(authUrl)
      .buildUpon()
      .appendQueryParameter("client_id", clientId)
      .appendQueryParameter("redirect_uri", redirectURI)
      .appendQueryParameter("response_type", "code id_token")
      .build();

    if (call.hasOption("state")) {
      authenticationUri =
        authenticationUri
          .buildUpon()
          .appendQueryParameter("state", call.getString("state"))
          .build();
    }

    if (call.hasOption("nonce")) {
      authenticationUri =
        authenticationUri
          .buildUpon()
          .appendQueryParameter("nonce", call.getString("nonce"))
          .build();
    }

    String scope = call.getString("scope", "name email");

    authenticationUri =
      authenticationUri
        .buildUpon()
        .appendQueryParameter("scope", scope)
        .appendQueryParameter("response_mode", "form_post")
        .build();

    Intent intent = new Intent(getContext(), LoginActivity.class);
    intent.putExtra("authUrl", authenticationUri.toString());
    intent.putExtra("redirectURI", redirectURI);

    startActivityForResult(call, intent, AUTH_CODE);
  }
}
