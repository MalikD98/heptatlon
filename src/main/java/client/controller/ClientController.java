package client.controller;

import client.service.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import shared.Article;
import shared.Facture;
import shared.Commande;

public class ClientController {

    @FXML
    private TextField referenceField;
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
    private TableColumn<Article, String> familleColumn;
    @FXML
    private TableColumn<Article, Double> prixColumn;
    @FXML
    private TableColumn<Article, Integer> quantiteColumn;
    @FXML
    private TableColumn<Facture, String> idFactureColumn;
    @FXML
    private TableColumn<Facture, Double> montantColumn;
    @FXML
    private TableColumn<Facture, String> dateColumn;

    private ClientService clientService;

    public ClientController() {
        clientService = new ClientService();
    }

    @FXML
    private void initialize() {
        // Initialize table columns if necessary
    }

    @FXML
    private void handleConsulterStock() {
        String reference = referenceField.getText();
        Article article = clientService.consulterStock(reference);
        stockTable.getItems().clear();
        if (article != null) {
            stockTable.getItems().add(article);
        }
    }

    @FXML
    private void handlePasserCommande() {
        String clientId = clientIdField.getText();
        String articleRef = articleRefField.getText();
        int quantite = Integer.parseInt(quantiteField.getText());
        Commande commande = new Commande(clientId, articleRef, quantite);
        clientService.passerCommande(commande);
        // Add additional logic if needed
    }

    @FXML
    private void handleGetFactures() {
        String clientId = factureClientIdField.getText();
        facturesTable.getItems().clear();
        facturesTable.getItems().addAll(clientService.consulterFacture(clientId));
    }
}
