package daconceicao.simon;

import daconceicao.simon.writter.TxtWritter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gui {

    private final JFrame frame = new JFrame("Location Ordinateurs");
    private final List<String> ordinateurs = new ArrayList<>();

    public Gui() {
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        chargerOrdinateursDepuisFichier();
    }

    public void mainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));
        frame.add(panel);

        JButton louer = new JButton("Louer un ordinateur");
        louer.addActionListener(e -> louerOrdinateur());
        panel.add(louer);

        JButton affichage = new JButton("Afficher les ordinateurs");
        affichage.addActionListener(e -> afficherOrdinateur());
        panel.add(affichage);

        JButton supprimer = new JButton("Supprimez un ordinateur");
        supprimer.addActionListener(e -> supprimerOrdinateur());
        panel.add(supprimer);

        JButton modifier = new JButton("Modifiez un ordinateur");
        modifier.addActionListener(e -> modifierOrdinateur());
        panel.add(modifier);

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
                TxtWritter.ajouterOrdinateurDansFichier(ordinateur);
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
                TxtWritter.sauvegarderListeDansFichier(ordinateurs);
                JOptionPane.showMessageDialog(frame, "Ordinateur supprimé avec succès !");
            } else {
                JOptionPane.showMessageDialog(frame, "Aucun ordinateur trouvé avec ce numéro de série !");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Numéro de série invalide !");
        }
    }

    // Méthode pour modifier un ordinateur
    public void modifierOrdinateur() {
        if (ordinateurs.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Aucun ordinateur à modifier !");
            return;
        }

        String numeroSerie = JOptionPane.showInputDialog(frame, "Entrez le numéro de série de l'ordinateur à modifier :");

        if (numeroSerie != null && !numeroSerie.trim().isEmpty()) {
            for (int i = 0; i < ordinateurs.size(); i++) {
                if (ordinateurs.get(i).contains("Numéro de série : " + numeroSerie.trim())) {
                    JTextField nouvelleMarqueField = new JTextField();
                    JTextField nouveauNumeroSerieField = new JTextField();

                    Object[] message = {
                        "Nouvelle marque :", nouvelleMarqueField,
                        "Nouveau numéro de série :", nouveauNumeroSerieField
                    };

                    int option = JOptionPane.showConfirmDialog(frame, message, "Modifier l'ordinateur", JOptionPane.OK_CANCEL_OPTION);

                    if (option == JOptionPane.OK_OPTION) {
                        String nouvelleMarque = nouvelleMarqueField.getText().trim();
                        String nouveauNumeroSerie = nouveauNumeroSerieField.getText().trim();

                        if (!nouvelleMarque.isEmpty() && !nouveauNumeroSerie.isEmpty()) {
                            String nouvelOrdinateur = "Marque : " + nouvelleMarque + ", Numéro de série : " + nouveauNumeroSerie;
                            ordinateurs.set(i, nouvelOrdinateur); // Modifier dans la liste
                            TxtWritter.sauvegarderListeDansFichier(ordinateurs); // Mettre à jour le fichier
                            JOptionPane.showMessageDialog(frame, "Ordinateur modifié avec succès !");
                            return;
                        } else {
                            JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs !");
                            return;
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(frame, "Aucun ordinateur trouvé avec ce numéro de série !");
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
