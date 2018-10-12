import java.io.IOException;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;


/**
 *
 * @author justi
 */
// On devrait changer le nom de cette classe pour un nom plus significatif
public class ValeurFonciere {


    // **********
    // CONSTANTES
    // **********
    final static String FILEPATH_INPUT = "json/fonciereEntree.json";
    final static String FILEPATH_OUTPUT = "json/foncierSortie.json";
    final static String CHARSET_ENCODING = "UTF-8";
    final static double VALEUR_FONCIERE_FIXE = 733.77;
    final static double TAXE_SCOLAIRE = 0.012;
    final static double TAXE_MUNICIPALE = 0.025;
    final static double DROIT_PASSAGE_MONTANT_BASE = 500;
    final static double DROIT_PASSAGE_MONTANT_SUPERIEURE_500_COMMERCIAL = 1500;
    final static double POURCENTAGE_DROIT_PASSAGE_AGRICOLE = 0.05;
    final static double POURCENTAGE_DROIT_PASSAGE_RESIDENTIEL = 0.1;
    final static double POURCENTAGE_DROIT_PASSAGE_COMMERCIAL = 0.15;
    final static double PLAFOND_VALEUR_SERVICE_COMMERCIAL = 5000;

    static double valeurLot(){
        return 0;
    }

    static double valeurLotAgricole(){
        return 0;
    }

    static double valeurLotResidentiel(){
        return 0;
    }

    static double valeurLotCommercial(){
        double valeurLotCommercial;

        valeurLotCommercial = Lot.superficie * Terrain.prixMax;

        return valeurLotCommercial ;
    }

    static double valeurDroitPass(){
        return 0;
    }

    static double valeurDroitPassAgricole(){
        return 0;
    }

    static double valeurDroitPassResidentiel(){
        return 0;
    }

    static double valeurDroitPassCommercial(){
        double valeurDroitPassCommercial;

        valeurDroitPassCommercial = DROIT_PASSAGE_MONTANT_BASE -
                (Lot.nbDroitPassage*(POURCENTAGE_DROIT_PASSAGE_COMMERCIAL*valeurLotCommercial()));

        return valeurDroitPassCommercial;
    }

    static double valeurService(){
        return 0;
    }

    static double valeurServiceAgricole(){
        return 0;
    }

    static double valeurServiceResidentiel(){
        return 0;
    }

    static double valeurServiceCommercial(){
        double valeurServiceCommercial;

        if(Lot.superficie <= DROIT_PASSAGE_MONTANT_BASE){
            valeurServiceCommercial = DROIT_PASSAGE_MONTANT_BASE;
        }else{
            valeurServiceCommercial = DROIT_PASSAGE_MONTANT_SUPERIEURE_500_COMMERCIAL*(Lot.nbService + 2);
            if(valeurServiceCommercial > PLAFOND_VALEUR_SERVICE_COMMERCIAL ){
                valeurServiceCommercial = PLAFOND_VALEUR_SERVICE_COMMERCIAL ;
            }
        }

        return valeurServiceCommercial;
    }

    /**
     * Les taxes vont etre a l'interieur de la methode(dans le calcul du total)
     */

    static double valeurFonciereTotale(){
        return 0;
    }


    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //Déclaration des variables et objets
        String terrainJSON;
        JSONObject terrain;
        JSONArray lotArray;
        JSONObject lotObject;
        int typeTerrain;
        double prixMin;
        double prixMax;
        String lotDescription;
        int nombreDroit;
        int nombreService;
        int superficie;
        double valeurFonciereTotale = 0.0;
        JSONArray lotSortieArray = new JSONArray();
        JSONObject lotSortieObject = new JSONObject();
        
        //lire le fichier d'entrée, en format JSON
        terrainJSON = FileReader.loadFileIntoString(FILEPATH_INPUT, CHARSET_ENCODING);
        terrain = JSONObject.fromObject(terrainJSON);
        typeTerrain = terrain.getInt("type_terrain");
        prixMin = terrain.getDouble("prix_m2_min");
        prixMax = terrain.getDouble("prix_m2_max");
        
        //obtenir l'arraylist dans le fichier d'entrée
        lotArray = terrain.getJSONArray("lotissements");
        if(typeTerrain == 2){//2 représente le type de terrain commercial
            for(int i = 0; i < lotArray.size(); ++i){
                lotObject = lotArray.getJSONObject(i);
                lotDescription = lotObject.getString("description");
                nombreDroit = lotObject.getInt("nombre_droits_passage");
                nombreService = lotObject.getInt("nombre_services");
                superficie = lotObject.getInt("superficie");
                
                //calcul de la valeur foncière du lot
                double montantValeurLot = 0.0;
                double montantDroitPassage = 0.0;
                double montantService = 0.0;
                double valeurParLot = 0.0;
                montantValeurLot = valeurLotCommercial();//ici la fonction est remplacée par la méthode en haut
                montantDroitPassage = valeurDroitPassCommercial();//ici la fonction est remplacée par la méthode en haut
                montantService = valeurServiceCommercial();//ici la fonction est remplacée par la méthode en haut
                //Un montant fixe de 733.77 $ est ajouté à la valeur foncière pour couvrir la valeur de base
                valeurParLot = VALEUR_FONCIERE_FIXE + montantValeurLot + montantDroitPassage + montantService;
                valeurFonciereTotale += valeurParLot; 
                
                //générer l'arraylist dans le fichier sortie, en format JSON
                lotSortieObject.clear();
                lotSortieObject.accumulate("description", lotDescription);
                lotSortieObject.accumulate("valeur_par_lot", valeurParLot);
                lotSortieArray.add(lotSortieObject);                
            }
            
            //calcul de la valeur foncière du terrain tatale et générer les objets dans le fichier sortie
            double taxeScolaire = valeurFonciereTotale * 0.012; //La taxe scolaire représente 1.2% de la valeur foncière totale
            double taxeMunicipale = valeurFonciereTotale * 0.025; //la taxe municipale représente 2.5 % de la valeur foncière totale
            JSONObject terrainSortieJSON = new JSONObject();
            terrainSortieJSON.accumulate("valeur_fonciere_totale", valeurFonciereTotale);
            terrainSortieJSON.accumulate("taxe_scolaire", taxeScolaire);
            terrainSortieJSON.accumulate("taxe_ municipale", taxeMunicipale);
            terrainSortieJSON.accumulate("lotissements", lotSortieArray);
            
            //créer le fichier sortie, en format JSON
            FileWriter.saveStringIntoFile(FILEPATH_OUTPUT, terrainSortieJSON.toString());
            
        }
    }
    
}

