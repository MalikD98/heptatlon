package shared;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Facture
 * 
 * Classe représentant une facture.
 */
public class Facture implements Serializable {
    private int reference;
    private String client;
    private String modePaiement;
    private BigDecimal montant;
    private Date dateCreation;
    private Date dateEnregistrement;

    // Constructeur par défaut
    public Facture() {}

    // Constructeur avec paramètres
    public Facture(int reference, String client, String modePaiement, BigDecimal montant, Date dateCreation, Date dateEnregistrement) {
        this.reference = reference;
        this.client = client;
        this.modePaiement = modePaiement;
        this.montant = montant;
        this.dateCreation = dateCreation;
        this.dateEnregistrement = dateEnregistrement;
    }

    // Getters et setters
    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    @Override
    public String toString() {
        return "Facture [reference=" + reference + ", client=" + client + ", modePaiement=" + modePaiement + ", montant=" + montant + ", dateCreation=" + dateCreation + ", dateEnregistrement=" + dateEnregistrement + "]";
    }
}
