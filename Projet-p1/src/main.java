import java.io.IOException;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;


/**
 *
 * @author justi
 */
public class main {

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
        terrainJSON = FileReader.loadFileIntoString("json/fonciereEntree.json", "UTF-8");
        terrain = JSONObject.fromObject(terrainJSON);
        typeTerrain = terrain.getInt("type_terrain");
        prixMin = terrain.getDouble("prix_m2_min");
        prixMax = terrain.getDouble("prix_m2_max");
        
        //obtenir l'arraylist dans le fichier d'entrée
        lotArray = terrain.getJSONArray("lotissements");
        if(typeTerrain == 2){
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
                montantDroitPassage = 500-(3*(0.15*montantValeurLot));                
                if(superficie <=500){
                    montantService = 500.00;
                }else{
                    montantService = 1500.00*(nombreService + 2);
                    if(montantService > 5000.00){
                        montantService = 5000.00;
                    }
                }
                valeurParLot = 733.77 + montantValeurLot + montantDroitPassage + montantService;
                valeurFonciereTotale += valeurParLot; 
                
                //générer l'arraylist dans le fichier sortie, en format JSON
                lotSortieObject.clear();
                lotSortieObject.accumulate("description", lotDescription);
                lotSortieObject.accumulate("valeur_par_lot", valeurParLot);
                lotSortieArray.add(lotSortieObject);                
            }
            
            //calcul de la valeur foncière du terrain tatale et générer les objets dans le fichier sortie
            Double taxeScolaire = valeurFonciereTotale * 0.012;
            Double taxeMunicipale = valeurFonciereTotale * 0.025;
            JSONObject terrainSortieJSON = new JSONObject();
            terrainSortieJSON.accumulate("valeur_fonciere_totale", valeurFonciereTotale);
            terrainSortieJSON.accumulate("taxe_scolaire", taxeScolaire);
            terrainSortieJSON.accumulate("taxe_ municipale", taxeMunicipale);
            terrainSortieJSON.accumulate("lotissements", lotSortieArray);
            
            //créer le fichier sortie, en format JSON
            FileWriter.saveStringIntoFile("json/foncierSortie.json", terrainSortieJSON.toString());
            
        }
    }
    
}

