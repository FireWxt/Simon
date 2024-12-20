package daconceicao.simon;

import daconceicao.simon.writter.TxtWritter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gui {

    private final JFrame frame = new JFrame("Location Ordinateurs");
    // Liste pour stocker les ordinateurs (numéro de série + marque)
    private final List<String> ordinateurs = new ArrayList<>();

    public Gui() {
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre
        chargerOrdinateursDepuisFichier(); // Charger les ordinateurs depuis le fichier au démarrage
    }

    public void mainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        frame.add(panel);

        JButton louer = new JButton("Louer un ordinateur");
        louer.addActionListener(e -> louerOrdinateur());
        panel.add(louer);

        JButton affichage = new JButton("Afficher les ordinateurs");
        affichage.addActionListener(e -> afficherOrdinateur());
        panel.add(affichage);

        JButton supprimer = new JButton("Supprimez Ordinateur");
        supprimer.addActionListener(e -> supprimerOrdinateur());
        panel.add(supprimer);

        JButton quitter = new JButton("Quitter");
        quitter.addActionListener(e -> System.exit(0));
        panel.add(quitter);

        frame.setVisible(true);
    }

    // Méthode pour louer un ordinateur
    public void louerOrdinateur() {
        JTextField numeroSerieField = new JTextField();
        JTextField marqueField = new JTextField();

        Object[] message = {
            "Numéro de série :", numeroSerieField,
            "Marque :", marqueField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Ajouter un ordinateur", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String marque = marqueField.getText().trim();
            String numeroSerie = numeroSerieField.getText().trim();

            if (!numeroSerie.isEmpty() && !marque.isEmpty()) {
                String ordinateur = "Marque : " + marque + ", Numéro de série : " + numeroSerie;
                ordinateurs.add(ordinateur);
                TxtWritter.ajouterOrdinateurDansFichier(ordinateur); // Écrire dans le fichier
                JOptionPane.showMessageDialog(frame, "Ordinateur ajouté avec succès !");
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs !");
            }
        }
    }

    // Méthode pour afficher les ordinateurs
    public void afficherOrdinateur() {
        if (ordinateurs.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Aucun ordinateur disponible !");
        } else {
            StringBuilder liste = new StringBuilder("Ordinateurs disponibles :\n");
            for (String ordinateur : ordinateurs) {
                liste.append(ordinateur).append("\n");
            }
            JOptionPane.showMessageDialog(frame, liste.toString());
        }
    }

    // Méthode pour supprimer un ordinateur
    public void supprimerOrdinateur() {
        if (ordinateurs.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Aucun ordinateur à supprimer !");
            return;
        }

        String numeroSerie = JOptionPane.showInputDialog(frame, "Entrez le numéro de série de l'ordinateur à supprimer :");

        if (numeroSerie != null && !numeroSerie.trim().isEmpty()) {
            boolean removed = ordinateurs.removeIf(ordinateur -> ordinateur.contains("Numéro de série : " + numeroSerie.trim()));

            if (removed) {
                TxtWritter.sauvegarderListeDansFichier(ordinateurs); // Sauvegarder la liste mise à jour
                JOptionPane.showMessageDialog(frame, "Ordinateur supprimé avec succès !");
            } else {
                JOptionPane.showMessageDialog(frame, "Aucun ordinateur trouvé avec ce numéro de série !");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Numéro de série invalide !");
        }
    }

    // Charger les ordinateurs depuis le fichier
    private void chargerOrdinateursDepuisFichier() {
        List<String> ordinateursCharges = TxtWritter.chargerOrdinateursDepuisFichier();
        ordinateurs.addAll(ordinateursCharges);
    }
}
