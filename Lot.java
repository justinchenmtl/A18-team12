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
    private int nbService;  // un minimum de 2 services (services de base)
    private double superficie;
    private String dateMesure;

    /**
     * Initialise les membres avec leur valeur par default
     */
    public Lot(){
        description = null;
        nbDroitPassage = 0;
        nbService = 2;
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
     * @throws LotException
     */
    public Lot(String description, int nbDroitPassage, int nbService, double superficie, String dateMesure)
        throws LotException{

        this.description =  description;
        this.nbDroitPassage = nbDroitPassage;
        this.nbService = nbService;
        this.superficie = superficie;
        this.dateMesure = dateMesure;
    }
}
