package server;

import server.ServerImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1100);
            ServerImpl server = new ServerImpl();
            Naming.rebind("rmi://localhost:1100/Central", server);
            System.out.println("Serveur prêt.");
            new CountDownLatch(1).await();
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
