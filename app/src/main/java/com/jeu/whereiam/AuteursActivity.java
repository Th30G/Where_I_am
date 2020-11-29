package com.jeu.whereiam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AuteursActivity extends AppCompatActivity {

    /**
     * onCreate permettant d afficher l interface de la page auteur
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auteurs);
    }

    /**
     * fonction enclenchee lorsqu on appuie sur le bouton accueil
     * elle permet de retourner a la page d accueil
     * @param view
     */
    public void retourAccueil(View view) {
        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
    }

}