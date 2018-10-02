
/**
 * Un lot contient 5 attributs:
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

    // On peut peut-etre les rendre public pour ne pas ecrire les setter et getter
    private String description;
    private int nbDroitPassage;
    private int nbService;
    private double superficie;
    // dateMesure n'est pas utile
    private String dateMesure;

    // Un minimum de 2 services (services de base)
    private final int NOMBRE_SERIVICE_MINIMUM_BASE = 2;
    private final int NOMBRE_DROIT_PASSAGE_MIN = 0;
    private final int NOMBRE_SERVICE_MINIMUM = 0;
    private final double SUPERFICIE_MINIMUM = 0;

    /**
     * Initialise les membres avec leur valeur par default
     */
    public Lot() {

        description = null;
        nbDroitPassage = 0;
        nbService = NOMBRE_SERIVICE_MINIMUM_BASE;
        superficie = 0.0;
        dateMesure = null;

    }

    /**
     *
     * @param description bref description du lot, ex: "lot 1", "lot 2"
     * @param nbDroitPassage le nombre de droit de passage
     * @param nbService le nombre de services offerts
     * @param superficie la superficie en metre carre
     * @param dateMesure la date dans laquelle le lot a ete mesure
     *
     * @throws LotException lorsque les donnees n'ont pas le bon format: -
     * description et dateMesure sont null - nbDroitPassage et nbService sont
     * negatifs - Superficie est inferieure ou egale a 0
     */
    public Lot(String description, int nbDroitPassage, int nbService, double superficie, String dateMesure)
            throws LotException {
        // Verifier que les donnees aient le bon format
        if (null != description && null != dateMesure && nbDroitPassage >= NOMBRE_DROIT_PASSAGE_MIN
                && nbService >= NOMBRE_SERVICE_MINIMUM && superficie > SUPERFICIE_MINIMUM) {

            this.description = description;
            this.nbDroitPassage = nbDroitPassage;
            this.nbService = NOMBRE_SERIVICE_MINIMUM_BASE + nbService;
            this.superficie = superficie;
            this.dateMesure = dateMesure;

        } else {

            // On peut changer le message d'erreure plus tard. Peut-etre le mettre comme FINAL
            throw new LotException("lot non conforme");

        }
    }

    public double ValeurLot(int typeTerrain, int superficie, double prixMin, double prixMax, double prixMoyen) {
        double valeurLot = 0.0;
        switch (typeTerrain) {
            case 0:
                valeurLot = superficie * prixMin;
                break;
            case 1:
                valeurLot = superficie * prixMoyen;
                break;
            default:
                valeurLot = superficie * prixMax;
                break;
        }
        return valeurLot;
    }

    public double ValeurDroitPassage(int typeTerrain, int nbrDroitPassage, double ValeurLot) {
        double valeurDroitPassage = 0.0;
        switch (typeTerrain) {
            case 0:
                valeurDroitPassage = 500 - (nbrDroitPassage * (ValeurLot * 0.05));
                break;
            case 1:
                valeurDroitPassage = 500 - (nbrDroitPassage * (ValeurLot * 0.1));
                break;
            default:
                valeurDroitPassage = 500 - (nbrDroitPassage * (ValeurLot * 0.15));
                break;
        }

        return valeurDroitPassage;
    }

    public double ValeurServices(int typeTerrain, int nbService, double superficie) {
        double valeurServices = 0.0;
        switch (typeTerrain) {
            case 0:
                valeurServices = 0.0;
                break;
            case 1:
                if (superficie <= 500) {
                    valeurServices = 0.0;
                } else if (superficie > 500 || superficie <= 10000) {
                    valeurServices = 500 * (nbService + 2);
                } else {
                    valeurServices = 1500 * (nbService + 2);
                }
                break;
            default:
                if (superficie <= 500) {
                    valeurServices = 500 * (nbService + 2);
                } else if (superficie > 500) {
                    valeurServices = 1500 * (nbService + 2);
                }
                break;
        }

        if (valeurServices > 5000) {
            valeurServices = 5000;
        }

        return valeurServices;
    }

}
