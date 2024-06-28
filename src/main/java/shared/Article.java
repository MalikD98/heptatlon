package shared;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Article
 * 
 * Classe représentant un article.
 */
public class Article implements Serializable {
    private String reference;
    private String libelle;
    private String famille;
    private BigDecimal prix;
    private int quantite;

    // Constructeur par défaut
    public Article() {}

    // Constructeur avec paramètres
    public Article(String reference, String libelle, String famille, BigDecimal prix, int quantite) {
        this.reference = reference;
        this.libelle = libelle;
        this.famille = famille;
        this.prix = prix;
        this.quantite = quantite;
    }

    // Getters et setters
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Article [reference=" + reference + ", libelle=" + libelle + ", famille=" + famille + ", prix=" + prix + ", quantite=" + quantite + "]";
    }
}
