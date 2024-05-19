package server.src;


import java.io.Serializable;
import java.sql.Timestamp;

public class Commande implements Serializable {
    private int reference;
    private String client;
    private String statut;
    private Timestamp dateCreation;
    private Timestamp dateModification;

    public Commande(int reference, String client, String statut, Timestamp dateCreation, Timestamp dateModification) {
        this.reference = reference;
        this.client = client;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    // Getters et setters...
}
