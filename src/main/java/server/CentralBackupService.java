package server;

import shared.Facture;
import shared.IServer;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CentralBackupService {
    private Connection conn;

    public CentralBackupService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/heptathlon_central", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Échec de la connexion à la base de données centrale.");
        }
    }

    public void backupFacturesFromAllStores() {
        List<IServer> storeServers = getStoreServers();
        System.out.println("StoreServers: " + storeServers);
        for (IServer storeServer : storeServers) {
            List<Facture> factures = getFacturesFromStore(storeServer);
            System.out.println("Les factures ici : " + factures);
            for (Facture facture : factures) {
                insertFactureInCentralDB(facture);
            }
        }
        System.out.println("Sauvegarde des factures terminée.");
    }

    private List<IServer> getStoreServers() {
        List<IServer> storeServers = new ArrayList<>();
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            String[] storeNames = registry.list();
            System.out.println("storeNames : " + Arrays.toString(storeNames));
            for (String storeName : storeNames) {
                System.out.println("StoreName: " + storeName);
                IServer storeServer = (IServer) registry.lookup(storeName);
                storeServers.add(storeServer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des serveurs de magasins.");
        }
        return storeServers;
    }


    private List<Facture> getFacturesFromStore(IServer storeServer) {
        try {
            return storeServer.consulterFactureAll(1); // 0 pour récupérer toutes les factures
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des factures depuis le magasin.");
            return new ArrayList<>();
        }
    }

    private void insertFactureInCentralDB(Facture facture) {
        String insertFacture = "INSERT INTO factures (reference, client, mode_paiement, date_creation) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertFacture)) {
            stmt.setInt(1, facture.getReference());
            stmt.setString(2, facture.getClient());
            stmt.setString(3, facture.getModePaiement());
            stmt.setTimestamp(4, new Timestamp(facture.getDateCreation().getTime()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion de la facture dans la base de données centrale.");
        }
    }

    public static void main(String[] args) {
        CentralBackupService service = new CentralBackupService();
        service.backupFacturesFromAllStores();
    }
}
