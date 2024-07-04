package shared;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface du serveur pour les opérations de gestion de stock et facturation.
 */
public interface IServer extends Remote {
    
    /**
     * Consulter le stock d'un article.
     * 
     * @param refMagasin Référence du magasin.
     * @return Liste des articles correspondants.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    List<Article> consulterStock(int refMagasin) throws RemoteException;

    /**
     * Rechercher des articles par famille.
     * 
     * @param famille Famille des articles.
     * @param refMagasin Référence du magasin.
     * @return Liste des articles correspondants.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    List<Article> rechercherArticlesParFamille(String famille, int refMagasin) throws RemoteException;

    /**
     * Rechercher des articles par famille.
     * 
     * @param reference Référence de l'article.
     * @param refMagasin Référence du magasin.
     * @return Liste des articles correspondants.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    List<Article> rechercherArticlesParId(int reference, int refMagasin) throws RemoteException;

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
     * Consulter les articles d'un client facture.
     * 
     * @param client ID du client.
     * @return Factures correspondant au client.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    List<Facture> consulterFacture(String client, int refMagasin) throws RemoteException;

    /**
     * Consulter les articles d'un client facture.
     * 
     * @return Factures correspondant au client.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    List<Facture> consulterFactureAll(int refMagasin) throws RemoteException;

    /**
     * Calculer le chiffre d'affaires pour une date donnée.
     * 
     * @param date Date pour laquelle calculer le chiffre d'affaires.
     * @return Chiffre d'affaires de la date spécifiée.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    BigDecimal calculerChiffreAffaires(String date, int refMagasin) throws RemoteException;

    /**
     * Ajouter des produits au stock.
     * 
     * @param reference Référence de l'article.
     * @param quantite Quantité à ajouter.
     * @return True si l'ajout est réussi, sinon false.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    boolean ajouterProduitStock(String reference, int quantite, int refMagasin) throws RemoteException;
    
    /**
     * Passe une commande pour un client, enregistre une facture, met à jour le stock
     * et insère chaque article de la commande dans une transaction.
     * 
     * @param commandes Liste des articles commandés.
     * @param client ID du client.
     * @param refMagasin Référence du magasin.
     * @param modePaiement Mode de paiement utilisé.
     * @return True si la commande a été passée avec succès, sinon false.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    boolean passerCommande(List<Commande> commandes, String client, int refMagasin, String modePaiement) throws RemoteException;

    /**
     * Authentifier un utilisateur.
     *
     * @param identifiant Identifiant de l'utilisateur.
     * @param password Mot de passe de l'utilisateur.
     * @return True si l'authentification réussit, sinon false.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    boolean authenticate(String identifiant, String password) throws RemoteException;

    /**
     * Récupère l'id du magasin connecté.
     *
     * @param identifiant Identifiant de l'utilisateur.
     * @return L'id du magasin.
     * @throws RemoteException Si une erreur de communication RMI se produit.
     */
    int getMagasinReference(String identifiant) throws RemoteException;

    List<Article> getArticlesByFacture(int factureId) throws RemoteException;

    public void syncWithCentralServer() throws RemoteException ;

}
