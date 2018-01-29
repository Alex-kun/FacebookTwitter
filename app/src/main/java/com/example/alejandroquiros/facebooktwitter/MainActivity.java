package com.example.alejandroquiros.facebooktwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginButton loginButton;
    TwitterLoginButton twLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        twLoginButton = (TwitterLoginButton) findViewById(R.id.twitter);
        twLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Toast.makeText(MainActivity.this, "LOGIN TWITTER CORRECTO", Toast.LENGTH_SHORT).show();

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(MainActivity.this, "LOGIN TWITTER INCORRECTO", Toast.LENGTH_SHORT).show();

            }
        });




        /*TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(R.string.com_twitter_sdk_android_CONSUMER_KEY, R.string.com_twitter_sdk_android_CONSUMER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);*/



        //FACEBOOK
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        //loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(MainActivity.this, "LOGIN CORRECTO", Toast.LENGTH_SHORT).show();
                //handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this, "LOGIN CANCEL", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(MainActivity.this, "LOGIN ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //FACEBOOK
        callbackManager.onActivityResult(requestCode, resultCode, data);

        //TWITTER
        twLoginButton.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }
}
