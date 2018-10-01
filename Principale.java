
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    final static double PLAFOND_VALEUR_SERVICE_COMMERCIAL = 5000;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, TerrainException, LotException {
        //Déclaration des variables et objets
        JSONObject mainObject;
        JSONArray lotArray;
        JSONObject lotObject;
        int typeTerrain;
        double prixMin;
        double prixMax;
        double valeurFonciereTotale = 0.0;
        double valeurParLot;
        String lotDescription;
        int nombreDroit;
        int nombreService;
        int superficie;
        String date_mesure;
        JSONArray lotSortieArray = new JSONArray();
        JSONObject lotSortieObject = new JSONObject();

        //lire le fichier d'entrée, en format JSON
        String json = FileReader.loadFileIntoString(FILEPATH_INPUT, CHARSET_ENCODING);
        mainObject = JSONObject.fromObject(json);
        typeTerrain = mainObject.getInt("type_terrain");
        prixMin = mainObject.getDouble("prix_m2_min");
        prixMax = mainObject.getDouble("prix_m2_max");
        lotArray = mainObject.getJSONArray("lotissements");
        Terrain t = new Terrain(typeTerrain, prixMin, prixMax, lotArray);
        for (int i = 0; i < lotArray.size(); ++i) {
            lotObject = lotArray.getJSONObject(i);
            lotDescription = lotObject.getString("description");
            nombreDroit = lotObject.getInt("nombre_droits_passage");
            nombreService = lotObject.getInt("nombre_services");
            superficie = lotObject.getInt("superficie");
            date_mesure = lotObject.getString("date_mesure");
            Lot lot = new Lot(lotDescription, nombreDroit, nombreService, superficie, date_mesure);
            double valeur_Lot = lot.ValeurLot(typeTerrain, superficie, prixMin, prixMax, t.getPrixMoyen());
            double valeur_DroitPassage = lot.ValeurDroitPassage(typeTerrain, nombreDroit, valeur_Lot);
            double valeur_services = lot.ValeurServices(typeTerrain, nombreService, superficie);
            valeurParLot = VALEUR_FONCIERE_FIXE + valeur_Lot + valeur_DroitPassage + valeur_services;
            valeurFonciereTotale += valeurParLot;
            lotSortieObject.clear();
            lotSortieObject.accumulate("description", lotDescription);
            lotSortieObject.accumulate("valeur_par_lot", valeurParLot);
            lotSortieArray.add(lotSortieObject);
        }

        Double taxeScolaire = valeurFonciereTotale * 0.012; //La taxe scolaire représente 1.2% de la valeur foncière totale
        Double taxeMunicipale = valeurFonciereTotale * 0.025; //la taxe municipale représente 2.5 % de la valeur foncière totale
        JSONObject terrainSortieJSON = new JSONObject();
        terrainSortieJSON.accumulate("valeur_fonciere_totale", valeurFonciereTotale);
        terrainSortieJSON.accumulate("taxe_scolaire", taxeScolaire);
        terrainSortieJSON.accumulate("taxe_ municipale", taxeMunicipale);
        terrainSortieJSON.accumulate("lotissements", lotSortieArray);

        //créer le fichier sortie, en format JSON
        FileWriter.saveStringIntoFile(FILEPATH_OUTPUT, terrainSortieJSON.toString());

        //créer le fichier sortie, en format JSON
        //FileWriter.saveStringIntoFile(FILEPATH_OUTPUT, terrainSortieJSON.toString());
    }
}
