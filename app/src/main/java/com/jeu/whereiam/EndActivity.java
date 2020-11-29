package com.jeu.whereiam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    //textView permettant d afficher les scores
    private TextView tvEnd;
    //valeur du score du joueur
    private int scoreValue;
    //valeur du meilleur score des parties jouees sur le telephone
    private int bestScore;

    /**
     * fonction permettant d afficher l interface de la page de fin de partie
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        //initialisation du textView d id tv_end
        tvEnd = findViewById(R.id.tv_end);

        //recuperation du score du joueur
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        scoreValue = extras.getInt("scoreValue");

        //on compare les score pour savoir si le nouveau score est meilleur que l ancien meilleur score
        comparaisonMeilleurScore(scoreValue);

        //on affiche le resultat que le textView doit presenter
        tvEnd.setText(getString(R.string.text_fin_1) + scoreValue + getString(R.string.text_fin_2) + bestScore);
    }

    /**
     * fonction se declenchant lorsqu on appuie sur le bouton recommencer
     * elle permet de relancer le jeu
     * @param view
     */
    public void recommencer(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * fonction permettant de comparer le score obtenu par le joueur
     * remplace le meilleur score dans le cas ou le joueur le bat
     * @param scoreValue score du joueur
     * @return le meilleur score
     */
    public int comparaisonMeilleurScore(int scoreValue){
        //demmande quel est le meilleur score enregistre pour l instant
        getBestScore();
        //dans le cas ou le score obtenu  par le joueur est supperieur au meilleur score enregistre
        if (scoreValue > bestScore) {
            //le meilleur score devient celui du joueur
            bestScore = scoreValue;
            //on enregistre ce nouveau meilleur score en tant que bestScore
            SharedPreferences settings = getSharedPreferences("SharedPreferencesScore", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("bestScore", scoreValue);
            editor.apply();
        }
        //on retourne le meilleur score
        return bestScore;
    }

    /**
     * onction permettant d avoir le meilleur score enregistre
     * @return le meilleur score enregistre
     */
    public int getBestScore(){
        //prend les parametres du SharedPreferences
        SharedPreferences settings = getSharedPreferences("SharedPreferencesScore", MODE_PRIVATE);
        //donne la valeur stockee dans le SharedPreference en tant que bestScore au bestScore de l activity
        //dans le cas ou il n y a pas de valeur enregistree on a une valeur par defaut de 0
        this.bestScore = settings.getInt("bestScore", 0);
        return bestScore;
    }

    /**
     * fonction enclenchee lorsque l on appuie sur le bouton accueil
     * elle permet de revenir a la page d accueil
     * @param view
     */
    public void retourAccueil(View view) {
        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
    }
}