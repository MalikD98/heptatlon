package client.controller;

import java.util.List;

import client.service.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.Article;
import shared.Facture;
import shared.Commande;

public class ClientController {

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

    public ClientController() {
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
        List<Article> articles = articlefamille.length() > 0 ? 
            clientService.rechercherArticlesParFamille(articlefamille)
            :
            clientService.consulterStock();
        stockTable.getItems().clear();
        if (articles.size() != 0) {
            for (Article article : articles) {
                stockTable.getItems().add(article);
            }
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
        List<Facture> factures = clientId.length() > 0 ? 
            clientService.consulterFacture(clientId)
            :
            clientService.consulterFacture();
        facturesTable.getItems().clear();
        if (factures.size() != 0) {
            for (Facture facture : factures) {
                facturesTable.getItems().add(facture);
            }
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
        }
    }
}
