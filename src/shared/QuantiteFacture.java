package shared;

import java.io.Serializable;

public class QuantiteFacture implements Serializable {
    private int factureReference;
    private int articleReference;
    private int quantiteFacture;

    public QuantiteFacture(int factureReference, int articleReference, int quantiteFacture) {
        this.factureReference = factureReference;
        this.articleReference = articleReference;
        this.quantiteFacture = quantiteFacture;
    }

    public int getFactureReference() {
        return factureReference;
    }

    public void setFactureReference(int factureReference) {
        this.factureReference = factureReference;
    }

    public int getArticleReference() {
        return articleReference;
    }

    public void setArticleReference(int articleReference) {
        this.articleReference = articleReference;
    }

    public int getQuantiteFacture() {
        return quantiteFacture;
    }

    public void setQuantiteFacture(int quantiteFacture) {
        this.quantiteFacture = quantiteFacture;
    }
}
