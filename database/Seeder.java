package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Seeder {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/heptathlon";
    private static final String USER = "root"; // Utilisateur par défaut de XAMPP
    private static final String PASS = "";    // Mot de passe par défaut de XAMPP (vide)

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            seedArticles(conn);
            seedCommandes(conn);
            seedQuantiteCommandes(conn);
            System.out.println("Seeding terminé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seedArticles(Connection conn) throws SQLException {
        String sql = "INSERT INTO articles (reference, famille, prix, quantite_stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setString(2, "Sports");
            stmt.setBigDecimal(3, new java.math.BigDecimal("20.99"));
            stmt.setInt(4, 100);
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setString(2, "Loisirs");
            stmt.setBigDecimal(3, new java.math.BigDecimal("15.50"));
            stmt.setInt(4, 200);
            stmt.addBatch();

            stmt.setInt(1, 3);
            stmt.setString(2, "Sports");
            stmt.setBigDecimal(3, new java.math.BigDecimal("9.99"));
            stmt.setInt(4, 150);
            stmt.addBatch();

            stmt.setInt(1, 4);
            stmt.setString(2, "Fitness");
            stmt.setBigDecimal(3, new java.math.BigDecimal("45.00"));
            stmt.setInt(4, 75);
            stmt.addBatch();

            stmt.setInt(1, 5);
            stmt.setString(2, "Loisirs");
            stmt.setBigDecimal(3, new java.math.BigDecimal("12.00"));
            stmt.setInt(4, 120);
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    private static void seedCommandes(Connection conn) throws SQLException {
        String sql = "INSERT INTO commandes (reference, client, mode_paiement, date_creation) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setString(2, "John Doe");
            stmt.setString(3, "Credit Card");
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf("2024-05-10 10:00:00"));
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setString(2, "Jane Smith");
            stmt.setString(3, "Cash");
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf("2024-05-11 11:00:00"));
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    private static void seedQuantiteCommandes(Connection conn) throws SQLException {
        String sql = "INSERT INTO quantite_commandes (commande_reference, article_reference, quantite_commande) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setInt(2, 1);
            stmt.setInt(3, 2);
            stmt.addBatch();

            stmt.setInt(1, 1);
            stmt.setInt(2, 3);
            stmt.setInt(3, 1);
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setInt(2, 2);
            stmt.setInt(3, 2);
            stmt.addBatch();

            stmt.executeBatch();
        }
    }
}
