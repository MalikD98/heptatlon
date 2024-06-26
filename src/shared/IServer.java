package shared.src;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface IServer extends Remote {
    Article consulterStock(int reference) throws RemoteException;
    List<Article> rechercherArticlesParFamille(String famille, int refmagasin) throws RemoteException;
    boolean acheterArticle(String client, int reference, int quantite, String modePaiement) throws RemoteException;
    Facture consulterFacture(int reference) throws RemoteException;
    double calculerChiffreAffaires(String date) throws RemoteException;
    boolean ajouterProduitStock(int reference, int quantite) throws RemoteException;
}
