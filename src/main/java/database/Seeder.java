package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Seeder {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/heptathlon";
    private static final String USER = "root"; // Utilisateur par défaut de XAMPP
    private static final String PASS = "";    // Mot de passe par défaut de XAMPP (vide)

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            seedMagasins(conn);
            seedArticles(conn);
            seedStock(conn);
            seedFactures(conn);
            seedCommandes(conn);
            System.out.println("Seeding terminé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seedMagasins(Connection conn) throws SQLException {
        String sql = "INSERT INTO magasins (reference, nom, ville, code_postal, identifiant, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String[] magasins = {
                "Paris Centre,Paris,75001,H001,PC@75001",
                "Lyon Part-Dieu,Lyon,69001,H002,LPD@69001",
                "Marseille Vieux-Port,Marseille,13001,H003,MVP@13001",
                "Lille Centre,Lille,59000,H004,LC@59000",
                "Bordeaux Centre,Bordeaux,33000,H005,BC@33000",
                "Nice Etoile,Nice,06000,H006,NE@06000",
                "Nantes Atlantis,Nantes,44000,H007,NA@44000",
                "Toulouse Capitole,Toulouse,31000,H008,TC@31000",
                "Strasbourg Rivetoile,Strasbourg,67000,H009,SR@67000",
                "Montpellier Odysseum,Montpellier,34000,H010,MO@34000"
            };

            for (int i = 0; i < magasins.length; i++) {
                String[] parts = magasins[i].split(",");
                String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(parts[4]);
                stmt.setInt(1, i + 1);
                stmt.setString(2, parts[0]);
                stmt.setString(3, parts[1]);
                stmt.setString(4, parts[2]);
                stmt.setString(5, parts[3]);
                stmt.setString(6, sha256hex);
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    private static void seedArticles(Connection conn) throws SQLException {
        String sql = "INSERT INTO articles (reference, libelle, famille, prix) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String[] articles = {
                "Ballon de football,Sports,29.99",
                "Raquette de tennis,Sports,89.99",
                "Chaussures de course,Sportswear,49.99",
                "T-shirt de sport,Vêtements,19.99",
                "Vélo de montagne,Sports,299.99",
                "Gants de boxe,Sports,34.99",
                "Sac de couchage,Camping,59.99",
                "Lampe de poche,Camping,14.99",
                "Skis alpins,Sports d'hiver,199.99",
                "Casque de vélo,Accessoires,39.99",
                "Short de sport,Vêtements,24.99",
                "Ballon de basket,Sports,24.99",
                "Maillot de bain,Vêtements,29.99",
                "Tente de camping,Camping,89.99",
                "Gourde de randonnée,Camping,9.99"
            };

            for (int i = 0; i < articles.length; i++) {
                String[] parts = articles[i].split(",");
                stmt.setInt(1, i + 1);
                stmt.setString(2, parts[0]);
                stmt.setString(3, parts[1]);
                stmt.setBigDecimal(4, new java.math.BigDecimal(parts[2]));
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    private static void seedStock(Connection conn) throws SQLException {
        String sql = "INSERT INTO stock (magasin_reference, article_reference, qte_stock) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 10; i++) {
                for (int j = 1; j <= 15; j++) {
                    stmt.setInt(1, i);
                    stmt.setInt(2, j);
                    stmt.setInt(3, (int) (Math.random() * 100 + 1));
                    stmt.addBatch();
                }
            }

            stmt.executeBatch();
        }
    }

    private static void seedCommandes(Connection conn) throws SQLException {
        String getPricesSql = "SELECT reference, prix FROM articles";
        String insertCommandSql = "INSERT INTO commandes (magasin_reference, article_reference, facture_reference, qte_fournie, montant) VALUES (?, ?, ?, ?, ?)";

        // Récupérer tous les prix des articles et les stocker dans une Map
        Map<Integer, BigDecimal> articlePrices = new HashMap<>();
        try (PreparedStatement getPricesStmt = conn.prepareStatement(getPricesSql);
             ResultSet rs = getPricesStmt.executeQuery()) {
            while (rs.next()) {
                int reference = rs.getInt("reference");
                BigDecimal prix = rs.getBigDecimal("prix");
                articlePrices.put(reference, prix);
            }
        }

        // Insérer les commandes avec les montants calculés
        try (PreparedStatement insertCommandStmt = conn.prepareStatement(insertCommandSql)) {
            for (int i = 1; i <= 10; i++) {
                for (int j = 1; j <= 15; j++) {
                    int qte_fournie = (int) (Math.random() * 20 + 1);
                    BigDecimal prix = articlePrices.get(j);
                    BigDecimal montant = prix.multiply(BigDecimal.valueOf(qte_fournie));

                    insertCommandStmt.setInt(1, i);
                    insertCommandStmt.setInt(2, j);
                    insertCommandStmt.setInt(3, i);  // Facture référence égale au magasin référence pour simplifier
                    insertCommandStmt.setInt(4, qte_fournie);
                    insertCommandStmt.setBigDecimal(5, montant);
                    insertCommandStmt.addBatch();
                }
            }

            insertCommandStmt.executeBatch();
        }
    }



    private static void seedFactures(Connection conn) throws SQLException {
        String sql = "INSERT INTO factures (reference, client, mode_paiement) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String[] emails = {
                "jean.dupont@example.com", "marie.martin@example.com", "pierre.durand@example.com", 
                "lucie.bernard@example.com", "sophie.petit@example.com", "michel.robert@example.com", 
                "julie.richard@example.com", "thomas.dubois@example.com", "emma.moreau@example.com", 
                "chloe.laurent@example.com"
            };

            String[] modes = { "Carte de crédit", "PayPal", "Virement bancaire", "Espèces" };

            for (int i = 0; i < emails.length; i++) {
                stmt.setInt(1, i + 1);
                stmt.setString(2, emails[i]);
                stmt.setString(3, modes[(int) (Math.random() * modes.length)]);
                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }
}
