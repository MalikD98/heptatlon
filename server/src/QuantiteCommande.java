package server.src;

import java.io.Serializable;

public class QuantiteCommande implements Serializable {
    private int commandeReference;
    private int articleReference;
    private int quantiteCommande;

    public QuantiteCommande(int commandeReference, int articleReference, int quantiteCommande) {
        this.commandeReference = commandeReference;
        this.articleReference = articleReference;
        this.quantiteCommande = quantiteCommande;
    }

    public int getCommandeReference() {
        return commandeReference;
    }

    public void setCommandeReference(int commandeReference) {
        this.commandeReference = commandeReference;
    }

    public int getArticleReference() {
        return articleReference;
    }

    public void setArticleReference(int articleReference) {
        this.articleReference = articleReference;
    }

    public int getQuantiteCommande() {
        return quantiteCommande;
    }

    public void setQuantiteCommande(int quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }
}
