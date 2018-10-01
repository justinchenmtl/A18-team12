
import net.sf.json.JSONArray;

/**
 * Utiliser pour decrire un terrain qui sera lu a partir d'un fichier JSON.
 *
 * Un terrain a 4 attributs: - un type (en general, le type peut etre n'importe
 * quel entier, depend de la situation externe) - un prix minimum par metre
 * carre - un prix maximum par metre carre - un ou plusieurs lots
 *
 * @author josue
 * @version 1.0
 */
public class Terrain {

    // **********
    // CONSTANTES
    // **********
    public static final int AGRICOLE = 0;
    public static final int RESIDENTIEL = 1;
    public static final int COMMERCIAL = 2;

    // On peut peut-etre les rendre public pour ne pas ecrire les setter et getter
    public int type;
    public double prixMin;
    public double prixMoyen;
    public double prixMax;
    public JSONArray lotissement; // Peut etre changer a ArrayList pour faciler l'ajout de lots

    /*
     * Contructeur par default. Initialise tout les attribut a leur valeur par
     * defaut. (0 pour int et null pour String)
     */
    public Terrain() {

        type = 0;
        prixMin = 0;
        prixMoyen = 0;
        prixMax = 0;
        lotissement = null;

    }

    /**
     *
     * @param type le type de terrain
     * @param prixMin le prix minimum par metre carre
     * @param prixMax le prix maximum par metre carre
     * @param lotissement les lots dans ce terrain
     *
     * @throws TerrainException si le prix minimum et le prix maximum sont
     * inferieur ou egale a 0
     */
    public Terrain(int type, double prixMin, double prixMax, JSONArray lotissement)
            throws TerrainException {
        // Ce n'est pas necessairement la meilleur maniere de valider les donnes.
        // Possiblement a changer
        if (prixMin > 0 && prixMax > 0 && null != lotissement) {
            this.type = type;
            this.prixMin = prixMin;
            this.prixMoyen = (prixMin + prixMax) / 2;
            this.prixMax = prixMax;
            this.lotissement = lotissement;
        } else {
            // A modifier pour plus de precision
            throw new TerrainException("Terrain non conforme.");
        }

    }

    public double getPrixMoyen() {
        return prixMoyen;
    }


}
