package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interface du serveur pour les opérations de gestion de stock et facturation.
 */
public interface IServer extends Remote {
    
    /**
     * Consulter le stock d'un article.
     * 
     * @param reference Référence de l'article.
     * @return Article correspondant à la référence.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    Article consulterStock(String reference) throws RemoteException;

    /**
     * Rechercher des articles par famille.
     * 
     * @param famille Famille des articles.
     * @param refMagasin Référence du magasin.
     * @return Liste des articles correspondants.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    List<Article> rechercherArticlesParFamille(String famille, String refMagasin) throws RemoteException;

    /**
     * Acheter un article.
     * 
     * @param client ID du client.
     * @param reference Référence de l'article.
     * @param quantite Quantité à acheter.
     * @param modePaiement Mode de paiement.
     * @return True si l'achat est réussi, sinon false.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    boolean acheterArticle(String client, String reference, int quantite, String modePaiement) throws RemoteException;

    /**
     * Consulter une facture.
     * 
     * @param reference Référence de la facture.
     * @return Facture correspondant à la référence.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    Facture consulterFacture(String reference) throws RemoteException;

    /**
     * Calculer le chiffre d'affaires pour une date donnée.
     * 
     * @param date Date pour laquelle calculer le chiffre d'affaires.
     * @return Chiffre d'affaires de la date spécifiée.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    BigDecimal calculerChiffreAffaires(String date) throws RemoteException;

    /**
     * Ajouter des produits au stock.
     * 
     * @param reference Référence de l'article.
     * @param quantite Quantité à ajouter.
     * @return True si l'ajout est réussi, sinon false.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    boolean ajouterProduitStock(String reference, int quantite) throws RemoteException;

    boolean passerCommande(Commande commande) throws RemoteException; // Ajout de la méthode passerCommande
}
