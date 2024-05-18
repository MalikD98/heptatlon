package server.src;

import server.src.ServerImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ServerImpl server = new ServerImpl();
            Naming.rebind("rmi://localhost/Server", server);
            System.out.println("Serveur prêt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
