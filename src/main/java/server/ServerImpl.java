package server;

import shared.IServer;
import shared.Article;
import shared.Commande;
import shared.Facture;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du serveur.
 */
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
    public List<Article> consulterStock(int refMagasin) throws RemoteException {
        String query = "SELECT art.*, stock.qte_stock FROM stock stock " +
                       "JOIN articles art ON stock.article_reference = art.reference " +
                       "WHERE stock.qte_stock > 0 AND stock.magasin_reference = ?";
        List<Article> articles = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, refMagasin);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    articles.add(new Article(
                        rs.getString("reference"),
                        rs.getString("libelle"),
                        rs.getString("famille"),
                        rs.getBigDecimal("prix"),
                        rs.getInt("qte_stock")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Article> rechercherArticlesParFamille(String famille, int refMagasin) throws RemoteException {
        String query = "SELECT art.*, stock.qte_stock FROM stock stock " +
                       "JOIN articles art ON stock.article_reference = art.reference " +
                       "WHERE art.famille LIKE ? AND stock.qte_stock > 0 AND stock.magasin_reference = ?";
        List<Article> articles = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + famille + "%");
            stmt.setInt(2, refMagasin);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    articles.add(new Article(
                        rs.getString("reference"),
                        rs.getString("libelle"),
                        rs.getString("famille"),
                        rs.getBigDecimal("prix"),
                        rs.getInt("qte_stock")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Article> rechercherArticlesParId(int reference, int refMagasin) throws RemoteException {
        String query = "SELECT art.*, stock.qte_stock FROM stock stock " +
                       "JOIN articles art ON stock.article_reference = art.reference " +
                       "WHERE art.reference = ? AND stock.qte_stock > 0 AND stock.magasin_reference = ?";
        List<Article> articles = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reference);
            stmt.setInt(2, refMagasin);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    articles.add(new Article(
                        rs.getString("reference"),
                        rs.getString("libelle"),
                        rs.getString("famille"),
                        rs.getBigDecimal("prix"),
                        rs.getInt("qte_stock")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Facture> consulterFacture(String client) throws RemoteException {
        String query = "SELECT * FROM factures WHERE client LIKE ?";
        List<Facture> factures = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + client + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    factures.add(new Facture(
                        rs.getString("reference"),
                        rs.getString("client"),
                        rs.getString("mode_paiement"),
                        rs.getBigDecimal("montant"),
                        rs.getTimestamp("date_creation"),
                        rs.getTimestamp("date_enregistrement")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factures;
    }

    @Override
    public List<Facture> consulterFactureAll() throws RemoteException {
        String query = "SELECT * FROM factures";
        List<Facture> factures = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    factures.add(new Facture(
                        rs.getString("reference"),
                        rs.getString("client"),
                        rs.getString("mode_paiement"),
                        rs.getBigDecimal("montant"),
                        rs.getTimestamp("date_creation"),
                        rs.getTimestamp("date_enregistrement")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factures;
    }

    @Override
    public BigDecimal calculerChiffreAffaires(String date) throws RemoteException {
        String query = "SELECT SUM(montant) AS chiffre_affaires FROM factures WHERE DATE(date_creation) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("chiffre_affaires");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public boolean ajouterProduitStock(String reference, int quantite) throws RemoteException {
        String query = "UPDATE stock SET qte_stock = qte_stock + ? WHERE article_reference = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantite);
            stmt.setString(2, reference);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean acheterArticle(String client, String reference, int quantite, String modePaiement) throws RemoteException {
        String queryStock = "UPDATE stock SET qte_stock = qte_stock - ? WHERE article_reference = ? AND qte_stock >= ?";
        String queryFacture = "INSERT INTO factures (reference, client, mode_paiement, montant, date_creation, date_enregistrement) VALUES (?, ?, ?, ?, NOW(), NOW())";
        String queryArticle = "SELECT prix FROM articles WHERE reference = ?";

        try {
            conn.setAutoCommit(false);

            // Mettre à jour le stock
            try (PreparedStatement stmtStock = conn.prepareStatement(queryStock)) {
                stmtStock.setInt(1, quantite);
                stmtStock.setString(2, reference);
                stmtStock.setInt(3, quantite);
                int rowsAffected = stmtStock.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // Obtenir le prix de l'article
            BigDecimal prix;
            try (PreparedStatement stmtArticle = conn.prepareStatement(queryArticle)) {
                stmtArticle.setString(1, reference);
                try (ResultSet rs = stmtArticle.executeQuery()) {
                    if (rs.next()) {
                        prix = rs.getBigDecimal("prix");
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            // Créer la facture
            try (PreparedStatement stmtFacture = conn.prepareStatement(queryFacture)) {
                stmtFacture.setString(1, reference); // Assurez-vous que la référence de la facture est générée ou obtenue correctement
                stmtFacture.setString(2, client);
                stmtFacture.setString(3, modePaiement);
                stmtFacture.setBigDecimal(4, prix.multiply(BigDecimal.valueOf(quantite)));
                stmtFacture.executeUpdate();
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
    public boolean passerCommande(List<Commande> commandes, String client, int magasin, String modePaiement, BigDecimal total) throws RemoteException {
        String queryInsertFacture = "INSERT INTO factures (client, mode_paiement, montant) VALUES (?, ?, ?)";
        String queryInsertCommande = "INSERT INTO commandes (magasin_reference, article_reference, facture_reference, qte_fournie) VALUES (?, ?, ?, ?)";
        String queryUpdateStock = "UPDATE stock SET qte_stock = qte_stock - ? WHERE article_reference = ?";
    
        try {
            conn.setAutoCommit(false);
            int factureId;
    
            // Insérer la facture avec le montant total fourni
            try (PreparedStatement stmtFacture = conn.prepareStatement(queryInsertFacture, Statement.RETURN_GENERATED_KEYS)) {
                stmtFacture.setString(1, client);
                stmtFacture.setString(2, modePaiement);
                stmtFacture.setBigDecimal(3, total);
                stmtFacture.executeUpdate();
    
                // Obtenir l'ID de la facture insérée
                try (ResultSet generatedKeys = stmtFacture.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        factureId = generatedKeys.getInt(1);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }
    
            // Insérer les articles de la commande et mettre à jour le stock
            for (Commande cmd : commandes) {
                // Insérer l'article dans la commande
                try (PreparedStatement stmtCommande = conn.prepareStatement(queryInsertCommande)) {
                    stmtCommande.setInt(1, magasin);
                    stmtCommande.setInt(2, cmd.getReference());
                    stmtCommande.setInt(3, factureId);
                    stmtCommande.setInt(4, cmd.getQteFournie());
                    stmtCommande.executeUpdate();
                }
    
                // Mettre à jour le stock de l'article
                try (PreparedStatement stmtStock = conn.prepareStatement(queryUpdateStock)) {
                    stmtStock.setInt(1, cmd.getQteFournie());
                    stmtStock.setInt(2, cmd.getReference());
                    stmtStock.executeUpdate();
                }
            }
    
            // Valider la transaction
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
