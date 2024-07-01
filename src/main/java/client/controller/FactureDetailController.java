package client.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import shared.Article;
import shared.Facture;

import java.math.BigDecimal;
import java.util.List;

import client.service.ClientService;

public class FactureDetailController {

    @FXML
    private Label factureIdLabel;
    @FXML
    private Label clientLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label montantLabel;
    @FXML
    private Label modePaiementLabel;
    @FXML
    private TableView<Article> articlesTable;
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

    private ClientService clientService;

    public FactureDetailController() {
        clientService = new ClientService();
    }

    @FXML
    private void initialize() {
        refColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        familleColumn.setCellValueFactory(new PropertyValueFactory<>("famille"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
    }

    public void setFacture(Facture facture) {
        factureIdLabel.setText(String.valueOf(facture.getReference()));
        clientLabel.setText(facture.getClient());
        dateLabel.setText(facture.getDateCreation().toString());
        montantLabel.setText(facture.getMontant().toString());
        modePaiementLabel.setText(facture.getModePaiement());

        List<Article> articles = clientService.getArticlesByFacture(facture.getReference());
        articlesTable.getItems().setAll(articles);
    }

    @FXML
    private void handleClose(ActionEvent event) {
        // Get the current stage from the event source and close it
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
