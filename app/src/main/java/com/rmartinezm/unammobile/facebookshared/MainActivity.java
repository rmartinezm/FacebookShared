package com.rmartinezm.unammobile.facebookshared;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnPublishListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LoginButton loginButton;
    CallbackManager callbackManager;
    EditText publicacion;
    Button publicar;
    View layout;
    SimpleFacebook fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout);
        publicacion = (EditText) findViewById(R.id.et_publicacion);
        publicar = (Button) findViewById(R.id.btn_publicar);
        publicar.setOnClickListener(this);
        loginButton = (LoginButton) findViewById(R.id.login_btn);
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Snackbar.make(layout, "Inicio de sesión exitoso", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Snackbar.make(layout, "Inicio de sesión cancelado", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Snackbar.make(layout, "Error al iniciar sesión", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if (AccessToken.getCurrentAccessToken() == null){
            Snackbar.make(layout, "Tienes que iniciar sesión primero", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Feed feed = new Feed.Builder()
                .setDescription(publicacion.getText().toString())
                .build();
        SimpleFacebook.setConfiguration(new MiConfiguracion().getMyConfigs());
        fb = SimpleFacebook.getInstance(this);
        fb.publish(feed, new OnPublishListener() {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                Snackbar.make(layout, "Publicado exitosamente", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable throwable) {
                super.onException(throwable);
                Snackbar.make(layout, "Error al publicar", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String reason) {
                super.onFail(reason);
                Snackbar.make(layout, "Error al publicar", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
