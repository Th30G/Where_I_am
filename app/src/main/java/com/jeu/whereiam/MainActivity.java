/*package com.jeu.whereiam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private RelativeLayout fenetrePrincipale;
    private GestureDetectorCompat gestureDetector;
    private ImageButton imageButton;
    private List<Image> listeImages;
    private Point size;
    private TextView score;
    private int scoreValue = 0;
    private int niveau = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fenetrePrincipale = findViewById(R.id.fenetrePrincipale);
        listeImages = new ArrayList<Image>();
        gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);
        score = findViewById(R.id.score);
        Display display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        scoreValue = preferences.getInt("Score", 0);
        ajouterGroupeImage(niveau);
    }

    private void ajouterGroupeImage(int niveau) {
        int nombre = 10*niveau;
        for (int i = 0; i < nombre - 1; i++) {
            listeImages.add(new Image(false, fenetrePrincipale, size, score, this, this));
        }
        listeImages.add(new Image(true, fenetrePrincipale, size, score,this, this));
    }

    public void score(int n){
        scoreValue += n;
        score.setText("Score : "+scoreValue);
    }

    public void finPartie(View view) {
        Intent intent = new Intent(this, EndActivity.class);
        Log.e("valeur score : ", scoreValue+"");
        intent.putExtra("scoreValue", scoreValue);
        startActivity(intent);
    }

    public void niveauSupp(){
        ajouterGroupeImage(this.niveau++);
    }

    public void supprimerImage(View v){
        listeImages.remove(v);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}*/

package com.jeu.whereiam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    //layout qui va contenir les trefles
    private RelativeLayout fenetrePrincipale;
    // utilise pour obtenir la taille quand la View est dessinee (sinon taille = (0, 0))
    private ViewTreeObserver vto;
    //permet de detecter les interaction de l ecran
    private GestureDetectorCompat gestureDetector;
    //les images qu il faut eviter (trefle a 3 feuilles) et celle qu il faut trouver (trefle a 4 feuilles)
    private ImageButton imageButton, imageButtonToFind;
    //objet nous permettant de gerer les images
    private GestionImage gestionImages;
    //textView ou sera affiche le score final
    private TextView score;
    //valeur du score du joueur
    private int scoreValue = 0;
    //valeur du niveau ou est rendu le du joueur
    private int niveau = 1;

    /**
     * fonction permettant l affichage de la page de jeu
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisation
        fenetrePrincipale = findViewById(R.id.fenetrePrincipale);
        gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);
        score = findViewById(R.id.score);
        //Display display = getWindowManager().getDefaultDisplay();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        scoreValue = preferences.getInt("Score", 0);

        // Sert a obtenir la taille de la View une fois qu elle est creee
        vto = fenetrePrincipale.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                fenetrePrincipale.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                gestionImages = new GestionImage(fenetrePrincipale.getMeasuredWidth(), fenetrePrincipale.getMeasuredHeight());
                startGame();
            }
        });
    }

    /**
     * initialise le jeu a chaque nouveau niveau
     */
    public void startGame() {
        //efface les images a l ecran
        cleanScreen();

        //cree les coordoonees de x nouvelles images avec x = nbImages = 5 fois le niveau du joueur
        gestionImages.creerCoordonneesImage(niveau * 5);

        //pour le nombre de coordonnees creees on place une mauvaise image sur l ecran
        for (int i = 0; i < gestionImages.getNbCoordonnees(); i++) {
            placeImage(false, i);
        }

        //on place ensuite l image a trouver
        placeImage(true, -1);
    }


    /**
     * permet d afficher les images a l ecran
     * @param is4Leaf booleen servant a savoir si l image (le trefle) a placer est le bon ou le mauvais (4 feuilles ou non)
     * @param imgIdx id des coordonnees de l image a creer
     */
    public void placeImage(Boolean is4Leaf, final int imgIdx) {
        // pour les mauvaises images (trefles a 3 feuilles) on copie l image plusieurs fois au lieu de l instancier
        // plusieurs fois
        imageButton = new ImageButton(fenetrePrincipale.getContext());
        imageButtonToFind = new ImageButton(fenetrePrincipale.getContext());

        //s il s agit de la bonne image on lui donne les bons parametres
        if (is4Leaf) {
            imageButtonToFind.setBackgroundResource(R.drawable.trefle4);
            imageButtonToFind.setX(gestionImages.getCoordonneesImageATrouver().first);
            imageButtonToFind.setY(gestionImages.getCoordonneesImageATrouver().second);
            imageButtonToFind.setLayoutParams(new ViewGroup.LayoutParams(250, 250));

            //si on touche a touche on augmente le score le niveau puis on le supprime
            // de l affichage et on recommence
            imageButtonToFind.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    score(10);
                    niveau++;
                    fenetrePrincipale.removeView(v);
                    startGame();
                }
            });

            //on l affiche a l ecran
            fenetrePrincipale.addView(imageButtonToFind);

        }

        else {
            //on lui ajoute les bons parametres
            imageButton.setBackgroundResource(R.drawable.trefle);
            imageButton.setX(gestionImages.getCoordonneesMauvaiseImage(imgIdx).first);
            imageButton.setY(gestionImages.getCoordonneesMauvaiseImage(imgIdx).second);
            imageButton.setLayoutParams(new ViewGroup.LayoutParams(250, 250));
            //on l affiche a l ecran
            fenetrePrincipale.addView(imageButton);
            //lorsqu on clique sur le bouton on perd, ca nous renvoie sur
            //la fonction de la fin de partie
            imageButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finPartie(v);
                }
            });
        }
    }

    /**
     * efface toutes les images presentes sur l ecran
     */
    public void cleanScreen() {
        for (int i = 0; i < fenetrePrincipale.getChildCount(); i++) {
            View v = fenetrePrincipale.getChildAt(i);
            if (v instanceof ImageButton)
                fenetrePrincipale.removeView(v);
        }

        fenetrePrincipale.invalidate();
    }

    /**
     * augmente le score de n points
     */
    public void score(int n){
        scoreValue += n;
        score.setText("Score : " + scoreValue);
    }

    /**
     * fonction de fin de partie permettant d afficher la page des resultats contenant le score
     * @param view
     */
    public void finPartie(View view) {
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra("scoreValue", scoreValue);
        startActivity(intent);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}

