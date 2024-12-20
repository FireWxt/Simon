package daconceicao.simon.model;

public class Ordinateur {
    private String numeroSerie; 
    private String marque;

    
    
    public Ordinateur(String numeroSerie, String marque) {
    	// Constructeur 
        this.numeroSerie = numeroSerie;
        this.marque = marque;
    }
    
    // Getters
    
    public String getNumeroSerie() {
        return numeroSerie;
    }


    public String getMarque() {
        return marque;
    }
    
    
    // Setters
    
    public void setNumeroSerie(String numeroSerie) {
    	this.numeroSerie = numeroSerie;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    @Override
    public String toString() {
        return "Ordinateur [Marque=" + marque + ", Numéro de série=" + numeroSerie + "]" + "\n";
    }
}
