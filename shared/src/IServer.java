package shared.src;


import java.rmi.Remote;
import java.rmi.RemoteException;

import server.src.Article;
import server.src.Commande;
import server.src.Facture;

public interface IServer extends Remote {
    Article consulterStock(int reference) throws RemoteException;
    Commande consulterCommande(int reference) throws RemoteException;
    Facture consulterFacture(int id) throws RemoteException;
    // Autres méthodes...
}
