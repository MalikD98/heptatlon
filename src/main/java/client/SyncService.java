package client;

import server.LocalServerImpl;

public class SyncService {
    public static void main(String[] args) {
        try {
            LocalServerImpl localServer = new LocalServerImpl();
            localServer.syncWithCentralServer();
            System.out.println("Synchronisation avec le serveur central terminée.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
