package server.src;

import java.io.Serializable;
import java.sql.Timestamp;

public class Facture implements Serializable {
    private int id;
    private String modePaiement;
    private Timestamp dateFacturation;
    private int commandeReference;

    public Facture(int id, String modePaiement, Timestamp dateFacturation, int commandeReference) {
        this.id = id;
        this.modePaiement = modePaiement;
        this.dateFacturation = dateFacturation;
        this.commandeReference = commandeReference;
    }

    // Getters et setters...
}
