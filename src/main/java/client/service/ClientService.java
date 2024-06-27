package client.service;

import shared.Article;
import shared.Commande;
import shared.Facture;
import shared.IServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * ClientService
 * 
 * Service client pour interagir avec le serveur.
 * Cette classe contient des méthodes pour consulter le stock, passer des commandes, et récupérer des factures.
 */
public class ClientService {
    private IServer server;

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
        }
    }

    /**
     * Consulter le stock d'un article.
     * @param reference Référence de l'article.
     * @return Article correspondant à la référence.
     */
    public Article consulterStock(String reference) {
        try {
            return server.consulterStock(reference);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Passer une commande.
     * @param commande Commande à passer.
     * @return True si la commande a été passée avec succès, false sinon.
     */
    public boolean passerCommande(Commande commande) {
        try {
            return server.passerCommande(commande);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupérer les factures pour un client donné.
     * @param clientId ID du client.
     * @return Liste des factures du client.
     */
    public Facture consulterFacture(String clientId) {
        try {
            return server.consulterFacture(clientId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthodes supplémentaires selon les besoins
}
