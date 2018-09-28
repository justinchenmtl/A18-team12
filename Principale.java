import java.io.IOException;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;


/**
 *
 * @author justi
 */
// On devrait changer le nom de cette classe pour un nom plus significatif
public class Principale {


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
    final static double POURCENTAGE_DROIT_PASSAGE_AGRICOLE = 0.05;
    final static double POURCENTAGE_DROIT_PASSAGE_RESIDENTIEL = 0.1;
    final static double POURCENTAGE_DROIT_PASSAGE_COMMERCIAL = 0.15;
    final static double PLAFOND_VALEUR_SERVICE = 5000;

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
        return 0;
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
        return 0;
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
        return 0;
    }

    static double valeurFonciere(){
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
        Double prixMin;
        Double prixMax;
        String lotDescription;
        int nombreDroit;
        int nombreService;
        int superficie;
        Double valeurFonciereTotale = 0.0;
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
                Double montantValeurLot = 0.0;
                Double montantDroitPassage = 0.0;
                Double montantService = 0.0;
                Double valeurParLot = 0.0; 
                montantValeurLot = superficie * prixMax;
                montantDroitPassage = 500-(nombreDroit*(0.15*montantValeurLot));//500 représent un montant de base                
                if(superficie <=500){
                    montantService = 500.00;
                }else{
                    montantService = 1500.00*(nombreService + 2);//1500.00 $ du service, si la superficie est supérieure à 500 m2
                    if(montantService > 5000.00){
                        montantService = 5000.00;//Le montant pour les services ne peut pas dépasser les 5000.00 $
                    }
                }
                //Un montant fixe de 733.77 $ est ajouté à la valeur foncière pour couvrir la valeur de base
                valeurParLot = 733.77 + montantValeurLot + montantDroitPassage + montantService;
                valeurFonciereTotale += valeurParLot; 
                
                //générer l'arraylist dans le fichier sortie, en format JSON
                lotSortieObject.clear();
                lotSortieObject.accumulate("description", lotDescription);
                lotSortieObject.accumulate("valeur_par_lot", valeurParLot);
                lotSortieArray.add(lotSortieObject);                
            }
            
            //calcul de la valeur foncière du terrain tatale et générer les objets dans le fichier sortie
            Double taxeScolaire = valeurFonciereTotale * 0.012; //La taxe scolaire représente 1.2% de la valeur foncière totale
            Double taxeMunicipale = valeurFonciereTotale * 0.025; //la taxe municipale représente 2.5 % de la valeur foncière totale
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

