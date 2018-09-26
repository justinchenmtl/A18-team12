/**
 * Un lot contient 4 attributs:
 *  - une description
 *  - un nombre de droits de passage
 *  - un nombre de service (minimum de 2 servicess de base)
 *  - une superficie en metre carre
 *  - une date de mesure
 *
 * @author josue
 * @version 1.0
 */
public class Lot {
    private String description;
    private int nbDroitPassage;
    private int nbService;
    private double superficie;
    private String dateMesure;

    // Un minimum de 2 services (services de base)
    private final int NOMBRE_SERIVICE_MINIMUM = 2;

    /**
     * Initialise les membres avec leur valeur par default
     */
    public Lot(){
        description = null;
        nbDroitPassage = 0;
        nbService = NOMBRE_SERIVICE_MINIMUM;
        superficie = 0.0;
        dateMesure = null;
    }

    /**
     *
     * @param description
     * @param nbDroitPassage
     * @param nbService
     * @param superficie
     * @param dateMesure
     *
     * @throws LotException lorsque les donnees n'ont pas le bon format:
     *                      - description et dateMesure sont null
     *                      - nbDroitPassage et nbService sont negatifs
     *                      - Superficie est inferieure ou egale a 0
     */
    public Lot(String description, int nbDroitPassage, int nbService, double superficie, String dateMesure)
        throws LotException{
        // Verifier que les donnees aient le bon format
        if(null != description || null != dateMesure || nbDroitPassage >= 0 || nbService >= 0 || superficie > 0) {
            this.description = description;
            this.nbDroitPassage = nbDroitPassage;
            this.nbService = NOMBRE_SERIVICE_MINIMUM + nbService;
            this.superficie = superficie;
            this.dateMesure = dateMesure;
        } else {
            // On peut changer le message d'erreure plus tard. Peut-etre le mettre comme FINAL
            throw new LotException("Il y a une erreure dans le fichier JSON");
        }
    }
}
