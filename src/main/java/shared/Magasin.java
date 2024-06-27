package shared;

import java.io.Serializable;

/**
 * Magasin
 * 
 * Classe représentant un magasin.
 */
public class Magasin implements Serializable {
    private String reference;
    private String nom;
    private String ville;
    private String codePostal;

    // Constructeur par défaut
    public Magasin() {}

    // Constructeur avec paramètres
    public Magasin(String reference, String nom, String ville, String codePostal) {
        this.reference = reference;
        this.nom = nom;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    // Getters et setters
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public String toString() {
        return "Magasin [reference=" + reference + ", nom=" + nom + ", ville=" + ville + ", codePostal=" + codePostal + "]";
    }
}
