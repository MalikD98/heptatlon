package server.src;


import java.io.Serializable;
import java.sql.Timestamp;

public class Commande implements Serializable {
    private int reference;
    private String client;
    private String modePaiement;
    private Timestamp dateCreation;
    private Timestamp dateEnregistrement;
    private Timestamp dateModification;

    public Commande(int reference, String client, String modePaiement, Timestamp dateCreation, Timestamp dateModification, Timestamp dateEnregistrement) {
        this.reference = reference;
        this.client = client;
        this.modePaiement = modePaiement;
        this.dateCreation = dateCreation;
        this.dateEnregistrement = dateEnregistrement;
        this.dateModification = dateModification;
    }
    // Getters et setters...
}
