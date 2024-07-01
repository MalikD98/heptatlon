package client.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import client.service.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import shared.Article;
import shared.Facture;
import shared.Commande;

public class ConsultationController {

    public DatePicker datePickerChiffreAffaire;

    public Label chiffreAffaireTexte;
    @FXML
    private TextField familleField;
    @FXML
    private TextField clientIdField;
    @FXML
    private TextField articleRefField;
    @FXML
    private TextField quantiteField;
    @FXML
    private TextField factureClientIdField;
    @FXML
    private TableView<Article> stockTable;
    @FXML
    private TableView<Facture> facturesTable;
    @FXML
    private TableColumn<Article, String> refColumn;
    @FXML
    private TableColumn<Article, String> libelleColumn;
    @FXML
    private TableColumn<Article, String> familleColumn;
    @FXML
    private TableColumn<Article, Double> prixColumn;
    @FXML
    private TableColumn<Article, Integer> quantiteColumn;
    @FXML
    private TableColumn<Facture, String> clientColumn;
    @FXML
    private TableColumn<Facture, String> idFactureColumn;
    @FXML
    private TableColumn<Facture, Double> montantColumn;
    @FXML
    private TableColumn<Facture, Double> modePaiementColumn;
    @FXML
    private TableColumn<Facture, String> dateColumn;

    private ClientService clientService;

    public ConsultationController() {
        clientService = new ClientService();
    }

    @FXML
    private void initialize() {
        // Initialize table columns if necessary
        refColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        familleColumn.setCellValueFactory(new PropertyValueFactory<>("famille"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        idFactureColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        modePaiementColumn.setCellValueFactory(new PropertyValueFactory<>("modePaiement"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));

        initStockTable();
        initFacturesTable();
    }

    @FXML
    private void handleConsulterStock() {
        String articlefamille = familleField.getText();

        if(articlefamille == null || articlefamille.isEmpty()) {
            showError("Erreur de saisie", "Veuillez renseigner une famille");
        }

        List<Article> articles = articlefamille.length() > 0 ?
                clientService.rechercherArticlesParFamille(articlefamille)
                :
                clientService.consulterStock();
        stockTable.getItems().clear();
        if (articles.size() != 0) {
            for (Article article : articles) {
                stockTable.getItems().add(article);
            }
        } else {
        showError("Aucun article trouvé", "Aucun article trouvé pour la famille spécifiée.");
    }
    }

    @FXML
    private void initStockTable() {
        List<Article> articles = clientService.consulterStock();
        stockTable.getItems().clear();
        if (articles.size() != 0) {
            for (Article article : articles) {
                stockTable.getItems().add(article);
            }
        } else {
            showError("Erreur de chargement", "Impossible de charger le stock.");
        }
    }

    @FXML
    private void handlePasserCommande(ActionEvent event) {
        try {
            // Charge la nouvelle vue
            Parent commandesPage = FXMLLoader.load(getClass().getResource("/Commande.fxml"));
            Scene commandesScene = new Scene(commandesPage);

            // Récupère la scène actuelle et le stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Change la scène du stage
            stage.setScene(commandesScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de navigation", "Impossible d'ouvrir la page de commande.");
        }
    }

    @FXML
    private void handleGetFactures() {
        String clientEmail = factureClientIdField.getText();
        if(clientEmail == null || clientEmail.isEmpty()) {
            showError("Erreur de saisie", "Veuillez renseigner une email");
        }
        List<Facture> factures = clientEmail.length() > 0 ?
                clientService.consulterFacture(clientEmail)
                :
                clientService.consulterFacture();
        facturesTable.getItems().clear();
        if (factures.size() != 0) {
            for (Facture facture : factures) {
                facturesTable.getItems().add(facture);
            }
        } else {
            showError("Aucune facture trouvée", "Aucune facture trouvée pour le client spécifié.");
        }
    }

    @FXML
    private void initFacturesTable() {
        List<Facture> factures = clientService.consulterFacture();
        facturesTable.getItems().clear();
        if (factures.size() != 0) {
            for (Facture facture : factures) {
                facturesTable.getItems().add(facture);
            }
        } else {
            showError("Erreur de chargement", "Impossible de charger les factures.");
        }
    }


    @FXML
    private void handleCalculerChiffreAffaire(ActionEvent event) {
        // Récupérer la date du DatePicker
        LocalDate selectedDate = datePickerChiffreAffaire.getValue();
        if (selectedDate != null) {
            // Formatter la date en 'YYYY-MM-DD'
            String formattedDate = selectedDate.toString();

            // Appeler la fonction calculerChiffreAffaires de clientService
            BigDecimal chiffreAffaire = clientService.calculerChiffreAffaires(formattedDate);

            // Afficher le chiffre d'affaire
            if(chiffreAffaire != null) {
                chiffreAffaireTexte.setText("Voici le chiffre d'affaire du " + formattedDate + " : " + chiffreAffaire + "€");
            }else {
                chiffreAffaireTexte.setText("Pas de chiffre d'affaire pour ce jour");
            }
        } else {
            showError("Date manquante", "Veuillez sélectionner une date.");
        }
    }

    @FXML
    private void handleStock(ActionEvent event) {
        try {
            // Charge la nouvelle vue
            Parent commandesPage = FXMLLoader.load(getClass().getResource("/Stock.fxml"));
            Scene commandesScene = new Scene(commandesPage);

            // Récupère la scène actuelle et le stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Change la scène du stage
            stage.setScene(commandesScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de navigation", "Impossible d'ouvrir la page de stock.");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
