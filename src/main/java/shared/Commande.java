package shared;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Commande
 * 
 * Classe représentant une commande.
 */
public class Commande implements Serializable {
    private String clientId;
    private int reference;
    private int qteFournie;
    private BigDecimal montant;

    // Constructeur par défaut
    public Commande() {}

    // Constructeur avec paramètres
    public Commande(String clientId, int reference, int qteFournie, BigDecimal montant) {
        this.clientId = clientId;
        this.reference = reference;
        this.qteFournie = qteFournie;
        this.montant = montant;
    }

    // Getters et setters
    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public int getQteFournie() {
        return qteFournie;
    }

    public void setQteFournie(int qteFournie) {
        this.qteFournie = qteFournie;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    @Override
    public String toString() {
        return "Commande [reference=" + reference + ", qteFournie=" + qteFournie + "]";
    }
}
