package daconceicao.simon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Gui {

    private final JFrame frame = new JFrame("Location Ordinateurs");
    private final List<String> ordinateurs = new ArrayList<>();  // Liste des ordinateurs
    private final DefaultTableModel tableModel;  // Modèle de la table
    private final JTable table;  // JTable pour afficher les ordinateurs

    private final String filePath = "ordinateurs.txt";  // Fichier où les ordinateurs seront sauvegardés

    public Gui() {
        // Initialisation de la fenêtre
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        // Définir les colonnes du tableau
        String[] columnNames = {"Marque", "Numéro de série"};


        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true); 


        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Modification des ordinateurs directement dans la JTable
        
        table.getModel().addTableModelListener(e -> enregistrerModifications());

    }

    public void mainMenu() {
    	
    	// Créer le menu des actions
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(6, 1));
    	frame.add(panel, BorderLayout.WEST);
    	
    	// Boutons du menu
    	JButton ajoutez = new JButton("Ajoutez un Ordinateur");
    	ajoutez.addActionListener(e -> ajoutezOrdinateur());
    	panel.add(ajoutez);
    	
    	JButton modifier = new JButton("Modifer un Ordinateur");
    	modifier.addActionListener(e -> modifierOrdinateur());
    	panel.add(modifier);
    	
    	JButton supprimer = new JButton("Supprimer un Ordinateur");
    	supprimer.addActionListener(e -> supprimerOrdinateur());
    	panel.add(supprimer);
    	
    	JButton quitter = new JButton("Quitter");
    	quitter.addActionListener(e -> System.exit(0));
    	panel.add(quitter);
    	
    	chargerOrdinateurs();
    	
    	frame.setVisible(true);
    }
    
    // Méthode pour charger les ordinateurs depuis le fichier
    private void chargerOrdinateurs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ordinateurs.add(line);
                String[] parts = line.split(", ");
                String marque = parts[0].split(": ")[1];
                String numeroSerie = parts[1].split(": ")[1];
                tableModel.addRow(new Object[]{marque, numeroSerie});
            }
        } catch (IOException e) {
            // Si le fichier n'existe pas encore, ignorer l'erreur
            System.out.println("Le fichier de données n'existe pas encore.");
        }
    }

    // Méthode pour sauvegarder les ordinateurs dans le fichier
    private void sauvegarderOrdinateurs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String ordinateur : ordinateurs) {
                writer.write(ordinateur);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void enregistrerModifications() {
        ordinateurs.clear();  // Réinitialiser la liste des ordinateurs
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String marque = (String) tableModel.getValueAt(i, 0);
            String numeroSerie = (String) tableModel.getValueAt(i, 1);
            ordinateurs.add("Marque : " + marque + ", Numéro de série : " + numeroSerie);
        }
        sauvegarderOrdinateurs();  // Sauvegarder dans le fichier après modification
    }

    // Méthode pour louer un ordinateur (ajouter à la liste et mettre à jour la JTable)
    public void ajoutezOrdinateur() {
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
                // Ajouter l'ordinateur à la liste et au modèle de la table
                ordinateurs.add("Marque : " + marque + ", Numéro de série : " + numeroSerie);
                tableModel.addRow(new Object[]{marque, numeroSerie}); // Ajouter une ligne dans le tableau
                JOptionPane.showMessageDialog(frame, "Ordinateur ajouté avec succès !");
                // Sauvegarder les ordinateurs dans le fichier
                sauvegarderOrdinateurs();
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs !");
            }
        }
    }

    // Méthode pour afficher les ordinateurs (mise à jour de la JTable)
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

    // Méthode pour supprimer un ordinateur (supprimer la ligne dans la JTable et du fichier)
    public void supprimerOrdinateur() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un ordinateur à supprimer.");
            return;
        }

        int selectedRow = table.getSelectedRow();
        String numeroSerie = (String) tableModel.getValueAt(selectedRow, 1);  


        ordinateurs.removeIf(ordinateur -> ordinateur.contains("Numéro de série : " + numeroSerie));
        tableModel.removeRow(selectedRow); 
        JOptionPane.showMessageDialog(frame, "Ordinateur supprimé avec succès !");

        sauvegarderOrdinateurs();
    }

 // Méthode pour modifier un ordinateur
    public void modifierOrdinateur() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un ordinateur à modifier.");
            return;
        }

        String numeroSerie = (String) tableModel.getValueAt(selectedRow, 1);  
        String marque = (String) tableModel.getValueAt(selectedRow, 0);  

        JTextField marqueField = new JTextField(marque);
        JTextField numeroSerieField = new JTextField(numeroSerie);

        Object[] message = {
            "Numéro de série :", numeroSerieField,
            "Marque :", marqueField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Modifier l'ordinateur", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String newMarque = marqueField.getText().trim();
            String newNumeroSerie = numeroSerieField.getText().trim();

            if (!newMarque.isEmpty() && !newNumeroSerie.isEmpty()) {
            
                tableModel.setValueAt(newMarque, selectedRow, 0);
                tableModel.setValueAt(newNumeroSerie, selectedRow, 1);


                ordinateurs.set(selectedRow, "Marque : " + newMarque + ", Numéro de série : " + newNumeroSerie);

                JOptionPane.showMessageDialog(frame, "Ordinateur modifié avec succès !");
        
                sauvegarderOrdinateurs();
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs !");
            }
        }
    }
    
}
