/**
 * Utiliser pour decrire un terrain qui sera lu a partir d'un fichier
 * JSON.
 *
 * Un terrain a 4 attributs:
 *  - un type
 *  - un prix minimum par metre carre
 *  - un prix maximum par metre carre
 *  - un ou des lots
 *
 * @author josue
 * @version 1.0
 */
public class Terrain {
    private int type;
    private int prixMin;
    private int prixMax;
    private Lot[] lotissement; // Peut etre changer a ArrayList pour faciler l'ajout de lots

    /**
     * Contructeur par default. Initialise tout les attribut
     * a leur valeur par defaut. (0 pour int et null pour String)
     */
    public Terrain(){
        type = 0;
        prixMin = 0;
        prixMax = 0;
        lotissement = null;
    }

    public Terrain(int type, int prixMin, int prixMax, Lot[] lotissement){
        this.type = type;
        this.prixMin = prixMin;
        this.prixMax = prixMax;
        this.lotissement = lotissement;
    }

}
