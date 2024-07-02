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

    public Magasin() {}

    public Magasin(String reference, String nom, String ville, String codePostal) {
        this.reference = reference;
        this.nom = nom;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    @Override
    public String toString() {
        return "Magasin [reference=" + reference + ", nom=" + nom + ", ville=" + ville + ", codePostal=" + codePostal + "]";
    }
}
