package shared;

import java.io.Serializable;
import java.math.BigDecimal;

public class Article implements Serializable {
    private int reference;
    private String famille;
    private String libelle;
    private BigDecimal prix;
    private int quantiteStock;

    public Article(int reference, String famille, String libelle, BigDecimal prix, int quantiteStock) {
        this.reference = reference;
        this.famille = famille;
        this.libelle = libelle;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
    }

    public int getReference() {
        return reference;
    }

    public int getQuantite() {
        return quantiteStock;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getPrix() {
        return prix.toString();
    }
}