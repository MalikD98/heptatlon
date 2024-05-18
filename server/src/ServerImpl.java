package server.src;

import server.src.Article;
import server.src.Commande;
import server.src.Facture;
import shared.src.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class ServerImpl extends UnicastRemoteObject implements IServer {
    private Connection conn;

    protected ServerImpl() throws RemoteException {
        try {
            // Charger le driver MySQL explicitement
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/heptathlon", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Article consulterStock(int reference) throws RemoteException {
        String query = "SELECT * FROM articles WHERE reference = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reference);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Article(rs.getInt("reference"), rs.getString("famille"), rs.getBigDecimal("prix"), rs.getInt("quantite_stock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Commande consulterCommande(int reference) throws RemoteException {
        String query = "SELECT * FROM commandes WHERE reference = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reference);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Commande(rs.getInt("reference"), rs.getString("client"), rs.getString("statut"), rs.getTimestamp("date_creation"), rs.getTimestamp("date_modification"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Facture consulterFacture(int id) throws RemoteException {
        String query = "SELECT * FROM factures WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Facture(rs.getInt("id"), rs.getString("mode_paiement"), rs.getTimestamp("date_facturation"), rs.getInt("commande_reference"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
