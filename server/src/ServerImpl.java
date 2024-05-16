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
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/heptathlon", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Article consulterStock(int reference) throws RemoteException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM articles WHERE reference = ?");
            stmt.setInt(1, reference);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Article(rs.getInt("reference"), rs.getString("famille"), rs.getBigDecimal("prix"), rs.getInt("quantite_stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Commande consulterCommande(int reference) throws RemoteException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM commandes WHERE reference = ?");
            stmt.setInt(1, reference);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Commande(rs.getInt("reference"), rs.getString("client"), rs.getString("statut"), rs.getTimestamp("date_creation"), rs.getTimestamp("date_modification"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Facture consulterFacture(int id) throws RemoteException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM factures WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Facture(rs.getInt("id"), rs.getString("mode_paiement"), rs.getTimestamp("date_facturation"), rs.getInt("commande_reference"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Autres méthodes...
}
