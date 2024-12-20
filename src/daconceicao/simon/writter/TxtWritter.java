package daconceicao.simon.writter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxtWritter {

    private static final String FICHIER_ORDINATEURS = "ordinateurs.txt";
    public static void ajouterOrdinateurDansFichier(String ordinateur) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_ORDINATEURS, true))) {
            writer.write(ordinateur);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

    public static List<String> chargerOrdinateursDepuisFichier() {
        List<String> ordinateurs = new ArrayList<>();
        File fichier = new File(FICHIER_ORDINATEURS);

        if (!fichier.exists()) {
            // Si le fichier n'existe pas, on le crée vide
            try {
                fichier.createNewFile();
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
            }
            return ordinateurs;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                ordinateurs.add(ligne);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        return ordinateurs;
    }
    
    public static void sauvegarderListeDansFichier(List<String> ordinateurs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_ORDINATEURS))) {
            for (String ordinateur : ordinateurs) {
                writer.write(ordinateur);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des données : " + e.getMessage());
        }
    }

}
