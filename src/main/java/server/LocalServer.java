package server;

import server.LocalServerImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

public class LocalServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            LocalServerImpl server = new LocalServerImpl();
            Naming.rebind("rmi://localhost/Server", server);
            System.out.println("Serveur prêt.");
            new CountDownLatch(1).await();
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
