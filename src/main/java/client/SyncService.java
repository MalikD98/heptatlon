package client;

import shared.IServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SyncService {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            IServer server = (IServer) registry.lookup("Server");

            server.syncWithCentralServer();
            System.out.println("Synchronisation avec le serveur central terminée.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
