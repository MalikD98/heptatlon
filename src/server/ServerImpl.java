package server;

import shared.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import shared.Article;
import shared.Facture;

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
        String query = "SELECT * FROM `stock` WHERE article_reference = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reference);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println(rs.getInt("reference")+ rs.getString("famille")+ rs.getBigDecimal("prix")+ rs.getInt("stock"));
                    return new Article(rs.getInt("reference"), rs.getString("famille"), rs.getBigDecimal("prix"), rs.getInt("stock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Article> rechercherArticlesParFamille(String famille, int refmagasin) throws RemoteException {
        String query = "SELECT art.*, stock.qte_stock FROM `stock` stock join articles art on stock.article_reference = art.reference WHERE art.famille = 'Sports' and stock.qte_stock > 0 and stock.magasin_reference = 1";
        List<Article> references = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, famille);
            stmt.setInt(2, refmagasin);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    references.add(new Article(rs.getInt("reference"), rs.getString("famille"), rs.getBigDecimal("prix"), rs.getInt("stock")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return references;
    }

    @Override
    public boolean acheterArticle(String client, int reference, int quantite, String modePaiement) throws RemoteException {
        String checkStockQuery = "SELECT * FROM `stock` WHERE article_reference = ?";
        String updateStockQuery = "UPDATE stock SET qte_stock = qte_stock - ? WHERE article_reference = ?";
        String insertFactureQuery = "INSERT INTO factures (client, mode_paiement, montant) VALUES (?, ?, ?)";
        String insertQuantiteFactureQuery = "INSERT INTO commandes (magasin_reference, article_reference, qte_fournie) VALUES (?, ?, ?)";
        BigDecimal prix = new BigDecimal("0");
        BigDecimal bDQuantite = new BigDecimal("0");
        
        try (PreparedStatement checkStockStmt = conn.prepareStatement(checkStockQuery);
             PreparedStatement updateStockStmt = conn.prepareStatement(updateStockQuery);
             PreparedStatement insertFactureStmt = conn.prepareStatement(insertFactureQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertQuantiteFactureStmt = conn.prepareStatement(insertQuantiteFactureQuery)) {
            
            conn.setAutoCommit(false);

            checkStockStmt.setInt(1, reference);
            try (ResultSet rs = checkStockStmt.executeQuery()) {
                    if (rs.next()) {
                    int stock = rs.getInt("stock");
                    if (stock < quantite) {
                        conn.rollback();
                        return false;
                    }
                } else {
                    conn.rollback();
                    return false;
                }
            }

            // Update stock
            updateStockStmt.setInt(1, quantite);
            updateStockStmt.setInt(2, reference);
            int rowsAffected = updateStockStmt.executeUpdate();
            if (rowsAffected == 0) {
                conn.rollback();
                return false;
            }

            // Insert facture
            insertFactureStmt.setString(1, client);
            insertFactureStmt.setString(2, modePaiement);
            insertFactureStmt.setBigDecimal(3, bDQuantite.multiply(prix));
            insertFactureStmt.executeUpdate();

            try (ResultSet generatedKeys = insertFactureStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int factureId = generatedKeys.getInt(1);
                    
                    // Insert quantite_factures
                    insertQuantiteFactureStmt.setInt(1, factureId);
                    insertQuantiteFactureStmt.setInt(2, reference);
                    insertQuantiteFactureStmt.setInt(3, quantite);
                    insertQuantiteFactureStmt.executeUpdate();
                } else {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Facture consulterFacture(int reference) throws RemoteException {
        String query = "SELECT * FROM factures WHERE reference = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reference);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Facture(rs.getInt("reference"), rs.getString("client"), rs.getString("mode_paiement"), rs.getBigDecimal("montant"), rs.getTimestamp("date_creation"), rs.getTimestamp("date_enregistrement"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double calculerChiffreAffaires(String date) throws RemoteException {
        String query = "SELECT SUM(f.montant) AS chiffre_affaires FROM factures f WHERE DATE(f.date_creation) = date(?)";
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
        String query = "UPDATE stock SET qte_stock = qte_stock + ? WHERE article_reference = ?";
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
