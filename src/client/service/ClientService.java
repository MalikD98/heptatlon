package client.service;

import shared.Article;
import shared.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Service pour communiquer avec le serveur.
 */
public class ClientService {
    private IServer server;

    public ClientService() {
        try {
            server = (IServer) Naming.lookup("//localhost/Server");
        } catch (NotBoundException | RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public List<Article> rechercherArticlesParFamille(String family, int refmagasin) throws RemoteException {
        return server.rechercherArticlesParFamille(family, 1);
    }
}
