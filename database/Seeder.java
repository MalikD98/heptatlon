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
            seedFactures(conn);
            seedQuantiteFactures(conn);
            System.out.println("Seeding terminé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insère des données dans la table articles.
     * @param conn la connexion à la base de données
     * @throws SQLException si une erreur survient pendant l'insertion
     */
    private static void seedArticles(Connection conn) throws SQLException {
        String sql = "INSERT INTO articles (reference, famille, prix, stock) VALUES (?, ?, ?, ?)";
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

    /**
     * Insère des données dans la table factures.
     * @param conn la connexion à la base de données
     * @throws SQLException si une erreur survient pendant l'insertion
     */
    private static void seedFactures(Connection conn) throws SQLException {
        String sql = "INSERT INTO factures (reference, client, mode_paiement, montant, date_creation, date_modification) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setString(2, "johndoe@mail.com");
            stmt.setString(3, "Credit Card");
            stmt.setBigDecimal(4, new java.math.BigDecimal("51.97"));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf("2024-05-10 10:00:00"));
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf("2024-05-10 10:00:00"));
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setString(2, "ebda3a99-c8ed-49c0-98ac-2c08e9f0f1b2");
            stmt.setString(3, "Cash");
            stmt.setBigDecimal(4, new java.math.BigDecimal("31"));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf("2024-05-11 11:00:00"));
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf("2024-05-11 11:00:00"));
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    /**
     * Insère des données dans la table quantite_factures.
     * @param conn la connexion à la base de données
     * @throws SQLException si une erreur survient pendant l'insertion
     */
    private static void seedQuantiteFactures(Connection conn) throws SQLException {
        String sql = "INSERT INTO quantite_factures (facture_reference, article_reference, quantite_facture) VALUES (?, ?, ?)";
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
