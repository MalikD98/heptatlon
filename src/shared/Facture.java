package shared.src;


import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class Facture implements Serializable {
    private int reference;
    private String client;
    private String modePaiement;
    private BigDecimal montant;
    private Timestamp dateCreation;
    private Timestamp dateEnregistrement;
    private Timestamp dateModification;

    public Facture(int reference, String client, String modePaiement, BigDecimal montant, Timestamp dateCreation, Timestamp dateEnregistrement) {
        this.reference = reference;
        this.client = client;
        this.modePaiement = modePaiement;
        this.montant = montant;
        this.dateCreation = dateCreation;
        this.dateEnregistrement = dateEnregistrement;
        this.dateModification = null;
    }
}
