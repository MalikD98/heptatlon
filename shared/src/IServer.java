package shared.src;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import server.src.Article;
import server.src.Commande;

public interface IServer extends Remote {
    Article consulterStock(int reference) throws RemoteException;
    List<Article> rechercherArticlesParFamille(String famille) throws RemoteException;
    boolean acheterArticles(String client, List<Article> articles, String modePaiement) throws RemoteException;
    Commande consulterCommande(int reference) throws RemoteException;
    double calculerChiffreAffaires(String date) throws RemoteException;
    boolean ajouterProduitStock(int reference, int quantite) throws RemoteException;

    // Autres méthodes...
}
