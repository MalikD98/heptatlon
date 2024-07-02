package client.service;

import shared.Article;
import shared.Commande;
import shared.Facture;
import shared.IServer;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import client.controller.ConnexionController;


/**
 * ClientService
 *
 * Service client pour interagir avec le serveur.
 * Cette classe contient des méthodes pour consulter le stock, passer des commandes, et récupérer des factures.
 */
public class ClientService {
    private IServer server;
    private int magasinReference = ConnexionController.magasinReference;


    /**
     * Constructeur du service client.
     * Initialise la connexion au serveur.
     */
    public ClientService() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            server = (IServer) registry.lookup("Server");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Échec de la connexion au serveur.");
        }
    }

    /**
     * Authentifie un utilisateur en vérifiant si l'identifiant et le mot de passe existent en base de données.
     *
     * @param identifiant L'identifiant de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @return True si l'utilisateur est authentifié avec succès, False sinon.
     */
    public boolean authenticate(String identifiant, String password) {
        try {
            boolean isAuthenticated = server.authenticate(identifiant, password);
            if (isAuthenticated) {
                magasinReference = server.getMagasinReference(identifiant);
            }
            return isAuthenticated;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Consulter le stock d'un article dans le magasin par défaut.
     *
     * @return Liste des articles en stock.
     */
    public List<Article> consulterStock() {
        try {
            if (magasinReference == -1) {
                System.out.println("Erreur : magasinReference n'est pas initialisé.");
                return null;
            }
            return server.consulterStock(magasinReference);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Rechercher des articles par famille dans le magasin par défaut.
     *
     * @param famille Famille des articles à rechercher.
     * @return Liste des articles correspondant à la famille.
     */
    public List<Article> rechercherArticlesParFamille(String famille) {
        try {
            return server.rechercherArticlesParFamille(famille, magasinReference);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Rechercher des articles par identifiant dans le magasin par défaut.
     *
     * @param reference Référence de l'article à rechercher.
     * @return Liste des articles correspondant à la référence.
     */
    public List<Article> rechercherArticlesParId(int reference) {
        try {
            return server.rechercherArticlesParId(reference, magasinReference);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Passer une commande.
     *
     * @param commandes Liste des articles à commander.
     * @param client ID du client.
     * @param modePaiement Mode de paiement utilisé.
     * @return True si la commande a été passée avec succès, false sinon.
     */
    public boolean passerCommande(List<Commande> commandes, String client, String modePaiement) {
        try {
            return server.passerCommande(commandes, client, magasinReference, modePaiement);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupérer les factures pour un client donné.
     *
     * @param clientId ID du client.
     * @return Liste des factures du client.
     */
    public List<Facture> consulterFacture(String clientId) {
        try {
            return server.consulterFacture(clientId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Récupérer toutes les factures.
     *
     * @return Liste de toutes les factures.
     */
    public List<Facture> consulterFacture() {
        try {
            return server.consulterFactureAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculer le chiffre d'affaires à une date donnée.
     *
     * @param date Date pour laquelle calculer le chiffre d'affaires.
     * @return Chiffre d'affaires à la date spécifiée.
     */
    public BigDecimal calculerChiffreAffaires(String date) {
        try {
            return server.calculerChiffreAffaires(date, magasinReference);
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    /**
     * Ajoute une quantité spécifique d'un produit au stock du magasin.
     *
     * @param reference La référence du produit.
     * @param quantite La quantité à ajouter au stock.
     * @return True si l'ajout est effectué avec succès, False sinon.
     */
    public boolean ajouterProduitStock(String reference, int quantite) {
        try {
            return server.ajouterProduitStock(reference, quantite, magasinReference);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère la liste des articles associés à une facture spécifique.
     *
     * @param factureId L'identifiant de la facture.
     * @return Une liste d'articles associés à la facture, ou null en cas d'échec.
     */
    public List<Article> getArticlesByFacture(int factureId) {
        try {
            return server.getArticlesByFacture(factureId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getMagasinReference() {
        return magasinReference;
    }
}
