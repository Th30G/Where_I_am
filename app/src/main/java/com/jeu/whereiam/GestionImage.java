package com.jeu.whereiam;

import android.content.Context;
import android.util.Pair;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Random;

public class GestionImage {

    //liste de pair contenant les coordonnees pour les mauvaises images (trefles a 3 feuilles)
    private ArrayList<Pair<Integer, Integer>> coordonneesMauvaisesImages;
    //pair contenant les coordonnes de l image a trouver (trefle 4 feuilles)
    private Pair<Integer, Integer> coordonneesImageATrouver;
    //pair contenant la taille du layout
    private Pair<Integer, Integer> layoutSize;


    /**
     * constructeur permettant d initialiser la liste de coordonnees d images et la taille du layout
     * @param width largeur du layout
     * @param height hauteur du layout
     */
    public GestionImage(int width, int height) {
        coordonneesMauvaisesImages = new ArrayList<Pair<Integer, Integer>>();
        layoutSize = new Pair<Integer, Integer>(width, height);
    }

    /**
     * fonction permettant d effacer les coordonnees d images deja creees et en creer
     * de nouvelles afin de placer les images sur l ecran
     * @param nbImages a placer
     */
    public void creerCoordonneesImage(int nbImages){
        if(nbImages <= 0){
            return;
        }

        //efface les coordonnees des images restantes (s il y a)
        coordonneesMauvaisesImages.clear();
        //donnes des coordonnees erronnees lors de l initialisation
        coordonneesImageATrouver = new Pair<Integer, Integer>(-1, -1);

        //random nous servant ensuite pour creer des coordonnees aleatoires
        Random rand = new Random(System.currentTimeMillis());

        //abscisse et ordonne des images
        int x, y;

        //on met les nouvelles coordonnees des images a ne pas selectionner
        //(trefles a 3 feuilles) dans la liste
        for (int i = 0; i < nbImages; i++){
            x = ((int) (rand.nextFloat() * (layoutSize.first - 200)));
            y = ((int) (rand.nextFloat() * (layoutSize.second - 200)));
            this.coordonneesMauvaisesImages.add(new Pair<Integer, Integer>(x, y));
        }

        //on donne de nouvelles coordonnees a l image a trouver
        x = ((int) (rand.nextFloat() * (layoutSize.first - 200)));
        y = ((int) (rand.nextFloat() * (layoutSize.second - 200)));
        coordonneesImageATrouver = new Pair<Integer, Integer>(x, y);
    }


    /**
     * donne le nombre de mauvaises images
     * faire +1 pour avoir le nombre total d images
     * @return
     */
    public int getNbCoordonnees(){
        return coordonneesMauvaisesImages.size();
    }


    /**
     * retourne les coordonnees de la ieme mauvaise image
     * @param i
     * @return
     */
    public Pair<Integer, Integer> getCoordonneesMauvaiseImage(int i){
        //dans le cas ou i n est pas compris dans la taille de la liste
        //on donne des coordonnees erreonnes/qui sortent de l ecran
        if (i <= 0 || i > coordonneesMauvaisesImages.size()-1)
            return new Pair<Integer, Integer>(-1,-1);
        return coordonneesMauvaisesImages.get(i);
    }


    /**
     * retourne les coordonnees de l image a trouver
     * @return
     */
    public Pair<Integer, Integer> getCoordonneesImageATrouver(){
        return coordonneesImageATrouver;
    }

}
