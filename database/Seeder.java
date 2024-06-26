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
            seedMagasins(conn);
            seedArticles(conn);
            seedStock(conn);
            seedCommandes(conn);
            seedFactures(conn);
            System.out.println("Seeding terminé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seedMagasins(Connection conn) throws SQLException {
        String sql = "INSERT INTO magasins (reference, nom, ville, code_postal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setString(2, "Magasin A");
            stmt.setString(3, "Paris");
            stmt.setString(4, "75001");
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setString(2, "Magasin B");
            stmt.setString(3, "Lyon");
            stmt.setString(4, "69001");
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    private static void seedArticles(Connection conn) throws SQLException {
        String sql = "INSERT INTO articles (reference, libelle, famille, prix) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setString(2, "Article A");
            stmt.setString(3, "Sports");
            stmt.setBigDecimal(4, new java.math.BigDecimal("20.99"));
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setString(2, "Article B");
            stmt.setString(3, "Loisirs");
            stmt.setBigDecimal(4, new java.math.BigDecimal("15.50"));
            stmt.addBatch();

            stmt.setInt(1, 3);
            stmt.setString(2, "Article C");
            stmt.setString(3, "Sports");
            stmt.setBigDecimal(4, new java.math.BigDecimal("9.99"));
            stmt.addBatch();

            stmt.setInt(1, 4);
            stmt.setString(2, "Article D");
            stmt.setString(3, "Fitness");
            stmt.setBigDecimal(4, new java.math.BigDecimal("45.00"));
            stmt.addBatch();

            stmt.setInt(1, 5);
            stmt.setString(2, "Article E");
            stmt.setString(3, "Loisirs");
            stmt.setBigDecimal(4, new java.math.BigDecimal("12.00"));
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    private static void seedStock(Connection conn) throws SQLException {
        String sql = "INSERT INTO stock (magasin_reference, article_reference, qte_stocke) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setInt(2, 1);
            stmt.setInt(3, 100);
            stmt.addBatch();

            stmt.setInt(1, 1);
            stmt.setInt(2, 2);
            stmt.setInt(3, 50);
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setInt(2, 3);
            stmt.setInt(3, 200);
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setInt(2, 4);
            stmt.setInt(3, 75);
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    private static void seedCommandes(Connection conn) throws SQLException {
        String sql = "INSERT INTO commandes (magasin_reference, article_reference, qte_fournie) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setInt(2, 1);
            stmt.setInt(3, 20);
            stmt.addBatch();

            stmt.setInt(1, 1);
            stmt.setInt(2, 2);
            stmt.setInt(3, 30);
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setInt(2, 3);
            stmt.setInt(3, 40);
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setInt(2, 4);
            stmt.setInt(3, 25);
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    private static void seedFactures(Connection conn) throws SQLException {
        String sql = "INSERT INTO factures (reference, client, mode_paiement, montant) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setString(2, "Client A");
            stmt.setString(3, "Credit Card");
            stmt.setBigDecimal(4, new java.math.BigDecimal("100.50"));
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setString(2, "Client B");
            stmt.setString(3, "Cash");
            stmt.setBigDecimal(4, new java.math.BigDecimal("200.75"));
            stmt.addBatch();

            stmt.executeBatch();
        }
    }
}
