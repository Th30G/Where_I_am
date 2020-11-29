package com.jeu.whereiam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccueilActivity extends AppCompatActivity {

    /**
     * onCreate permettant d afficher l interface de la page d accueil
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    /**
     * fonction enclenchee lorsqu on clique sur le bouton jouer
     * elle permet d aller sur l activity de la partie
     * @param view
     */
    public void startGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * fonction enclenchee lorsu on clique sur le bouton auteur
     * elle permet d aller sur l activity auteur et y voir les informations
     * concernant l auteur du jeu
     * @param view
     */
    public void pageAuteur(View view) {
        Intent intent = new Intent(this, AuteursActivity.class);
        startActivity(intent);
    }
}