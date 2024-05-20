package server.src;

import shared.src.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Article> rechercherArticlesParFamille(String famille) throws RemoteException {
        String query = "SELECT * FROM articles WHERE famille = ? AND quantite_stock > 0";
        List<Article> references = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, famille);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    references.add(new Article(rs.getInt("reference"), rs.getString("famille"), rs.getBigDecimal("prix"), rs.getInt("quantite_stock")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return references;
    }

    @Override
    public boolean acheterArticles(String client, List<Article> articles, String modePaiement) throws RemoteException {
        String insertCommandeQuery = "INSERT INTO commandes (client, mode_paiement, date_creation) VALUES (?, ?, NOW())";
        String insertQuantiteCommandeQuery = "INSERT INTO quantite_commandes (commande_reference, article_reference, quantite_commande) VALUES (?, ?, ?)";
        String updateStockQuery = "UPDATE articles SET quantite_stock = quantite_stock - ? WHERE reference = ? AND quantite_stock >= ?";

        try {
            conn.setAutoCommit(false);

            // Insérer la commande
            int commandeId;
            try (PreparedStatement stmt = conn.prepareStatement(insertCommandeQuery, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, client);
                stmt.setString(2, modePaiement);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        commandeId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            // Insérer chaque article de la commande et mettre à jour le stock
            try (PreparedStatement stmtQuantite = conn.prepareStatement(insertQuantiteCommandeQuery);
                 PreparedStatement stmtStock = conn.prepareStatement(updateStockQuery)) {
                for (Article article : articles) {
                    // Insérer la quantité commandée
                    stmtQuantite.setInt(1, commandeId);
                    stmtQuantite.setInt(2, article.getReference());
                    stmtQuantite.setInt(3, article.getQuantite());
                    stmtQuantite.addBatch();

                    // Mettre à jour le stock
                    stmtStock.setInt(1, article.getQuantite());
                    stmtStock.setInt(2, article.getReference());
                    stmtStock.setInt(3, article.getQuantite());
                    stmtStock.addBatch();
                }
                stmtQuantite.executeBatch();
                stmtStock.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Commande consulterCommande(int reference) throws RemoteException {
        String query = "SELECT * FROM commandes WHERE reference = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reference);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Commande(rs.getInt("reference"), rs.getString("client"), rs.getString("mode_paiement"), rs.getTimestamp("date_creation"), rs.getTimestamp("date_enregistrement"), rs.getTimestamp("date_modification"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double calculerChiffreAffaires(String date) throws RemoteException {
        String query = "SELECT SUM(a.prix * qc.quantite_commande) AS chiffre_affaires " +
                       "FROM commande c " +
                       "JOIN quantite_commandes qc ON c.reference = qc.commande_reference " +
                       "JOIN articles a ON qc.article_reference = a.reference " +
                       "WHERE DATE(c.date_creation) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("chiffre_affaires");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public boolean ajouterProduitStock(int reference, int quantite) throws RemoteException {
        String query = "UPDATE articles SET quantite_stock = quantite_stock + ? WHERE reference = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantite);
            stmt.setInt(2, reference);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
