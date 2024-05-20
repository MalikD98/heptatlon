package server.src;

import java.io.Serializable;
import java.math.BigDecimal;

public class Article implements Serializable {
    private int reference;
    private String famille;
    private BigDecimal prix;
    private int quantiteStock;

    public Article(int reference, String famille, BigDecimal prix, int quantiteStock) {
        this.reference = reference;
        this.famille = famille;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
    }

    public int getReference() {
        return reference;
    }

    public int getQuantite() {
        return quantiteStock;
    }
}